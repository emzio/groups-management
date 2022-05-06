package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.Utils.MonthUtil;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/groups")
public class GroupModelController {
    private final GroupService groupService;
    private final CanceledClassesService canceledClassesService;
    private final UserService userService;
    private final DayOfWeekService dayOfWeekService;
    private final CalendarCellServiceInterface calendarCellService;

    private final PaymentService paymentService;
    public GroupModelController(GroupService groupService, CanceledClassesService canceledClassesService, UserService userService, DayOfWeekService dayOfWeekService, CalendarCellServiceInterface calendarCellService, PaymentService paymentService) {
        this.groupService = groupService;
        this.canceledClassesService = canceledClassesService;
        this.userService = userService;
        this.dayOfWeekService = dayOfWeekService;
        this.calendarCellService = calendarCellService;
        this.paymentService = paymentService;
    }

    //add
    @GetMapping("/add")
    private String showAddForm(Model model){
        GroupModel groupModel = new GroupModel();
        model.addAttribute("groupModel" , groupModel);
        return "/admin/groups/add";
    }

    @PostMapping("/add")
    private String proceedAddForm(@Valid GroupModel groupModel, BindingResult result){
        if(result.hasErrors()){
            return "/admin/groups/add";
        }
        groupService.save(groupModel);
        return "redirect:/admin/groups/"+groupModel.getId();
    }

    //add user
    @GetMapping({"/addUser/{id}", "/addUser/{id}?oversize=true"})
    private String addUserShowForm(@PathVariable Long id, Model model,@RequestParam(required = false) boolean oversize){
        if(oversize){
            model.addAttribute("oversize", true);
        }
        GroupModel joiningUsers = groupService.findJoiningUsers(id);
        List<User> usersOutsideGroup = userService.findUsersOutOfGroup(joiningUsers);

        model.addAttribute("usersOutsideGroup", usersOutsideGroup);
        model.addAttribute("groupForUser", joiningUsers);
        return "admin/groups/adduser";
    }

    @PostMapping("/addUser/{id}")
    private String addUserProceedForm(GroupModel groupModel){

        int preUpdatedUsersNumber = groupService.findJoiningUsers(groupModel.getId()).getUsers().size();

        if (groupModel.getSize() < preUpdatedUsersNumber + groupModel.getUsers().size()){
            return "redirect:/admin/groups/addUser/{id}?oversize=true";
        }
        groupService.addUserToGroup(groupModel);
        return "redirect:/admin/groups/"+groupModel.getId();
    }


    // delete
    @GetMapping("/delete/{id}")
    private String deleteGroup(@PathVariable Long id, Model model){
        Optional<GroupModel> groupModelOptional = groupService.findById(id);
        if(groupModelOptional.isPresent()){
            model.addAttribute("toDelete", groupService.findById(id).get());
            return "/admin/groups/delete";
        }
        return "redirect:/admin/groups/notfound";
    }

    @PostMapping("/delete/{id}")
    private String proceedDeleteGroup(GroupModel groupModel){
        groupService.deleteGroupModel(groupModel);
        return "redirect:/user/start";
    }

    // update
    @GetMapping({"/update/{id}","/update/{id}?oversize=true"})
    private String showUpdateForm(@PathVariable Long id, Model model, @RequestParam(required = false) boolean oversize){
        if(oversize){
            model.addAttribute("oversize", true);
        }
        model.addAttribute("groupModel" , groupService.findJoiningUsers(id));
        return "/admin/groups/update";
    }

    @PostMapping("/update/{id}")
    public String proceedUpdateForm(@Valid GroupModel groupModel, BindingResult result){
        if(result.hasErrors()){
            return "/admin/groups/update";
        }
        if (!groupService.verificationOfOversize(groupModel, groupModel.getUsers())){
            return "redirect:/admin/groups/update/{id}?oversize=true";
        }
        groupService.editGroupModel(groupModel);
        return "redirect:/admin/groups/"+groupModel.getId();
    }

    // select month for CalendarCard
    @GetMapping("/month/{id}")
    private String showSelectMonthForm(Model model, @PathVariable Long id){
        model.addAttribute("id", id);

        model.addAttribute("months", MonthUtil.allMonths());
        model.addAttribute("actualYear", LocalDate.now().getYear());
        return "admin/groups/selectMonth";
    }

    @PostMapping("/month/{groupId}")
    public String proceedSelectMonthForm(@RequestParam String id, @RequestParam Integer year, @RequestParam Month month){
        String date = String.valueOf(LocalDate.of(year, month, 1));
        return "redirect:/admin/groups/"+ id+"?date="+date.toString();
    }

//    GroupDetails
    @GetMapping({"/{groupId}", "/{groupId}/{date}"})
    private String groupDetails(@RequestParam(required = false) String date, @PathVariable Long groupId, Model model){

        Optional<GroupModel> optionalGroupModel = groupService.findById(groupId);

        if(optionalGroupModel.isPresent()){
            model.addAttribute("group", groupService.findJoiningUsers(groupId));
            Month month = LocalDate.now().getMonth();
            Year year = Year.of(LocalDate.now().getYear());

            if(date!=null){
                LocalDate localDate = LocalDate.parse(date);
                month = localDate.getMonth();
                year = Year.of(localDate.getYear());
            }
            model.addAttribute("month", month.toString());
            model.addAttribute("year", year.toString());

            List<CalendarCell> cells = calendarCellService.calendarCardForGroup(groupId, month, year);
            List<List<CalendarCell>> weeks = calendarCellService.divideCalendarCardIntoWeeks(cells);

            model.addAttribute("weeks", weeks);

            // Informacje o płatnościach:
            Map<String, BigDecimal> paymentsInfo = paymentService.paymentAndClasses(cells);
            model.addAttribute("numberOfClasses",paymentsInfo.get("numberOfClasses"));
            model.addAttribute("paymentAmount", paymentsInfo.get("paymentAmount"));

            return "admin/groups/details";
        }
        return "redirect:/admin/groups/notfound";
    }



    @GetMapping("/notfound")
    private String notFound(){
        return "/notfound";
    }

    @ModelAttribute("daysOfWeek")
    Collection<String> findAllGroups(){
        return dayOfWeekService.findAll();
    }

    @ModelAttribute("users")
    Collection<User> findAllUsers(){
        return userService.findAllActive();
    }

}
