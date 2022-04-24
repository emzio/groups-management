package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.*;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/groups")
public class GroupModelController {
    private final GroupService groupService;
    private final CanceledClassesService canceledClassesService;
    private final UserService userService;
    private final DayOfWeekService dayOfWeekService;
    private final CalendarCellService calendarCellService;

    public GroupModelController(GroupService groupService, CanceledClassesService canceledClassesService, UserService userService, DayOfWeekService dayOfWeekService, CalendarCellService calendarCellService) {
        this.groupService = groupService;
        this.canceledClassesService = canceledClassesService;
        this.userService = userService;
        this.dayOfWeekService = dayOfWeekService;
        this.calendarCellService = calendarCellService;
    }

    //add
    @GetMapping("/add")
    private String showAddForm(Model model){
        GroupModel groupModel = new GroupModel();
        model.addAttribute("groupModel" , groupModel);
        return "/admin/groups/add";
    }

    @PostMapping("/add")
    private String proceedAddForm(GroupModel groupModel){
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
        model.addAttribute("groupForUser", joiningUsers);
        return "admin/groups/adduser";
    }

    @PostMapping("/addUser/{id}")
    private String addUserProceedForm(GroupModel groupModel){

        int preUpdatedUsersNumber = groupService.findJoiningUsers(groupModel.getId()).getUsers().size();

        if (groupModel.getSize() < preUpdatedUsersNumber + groupModel.getUsers().size()){
            return "redirect:/admin/groups/addUser/{id}?oversize=true";
        }

        groupModel.getUsers().stream()
                .forEach(user -> {
                    user.getGroups().add(groupModel);
                    userService.save(user);
                });
        groupService.save(groupModel);
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
        GroupModel joiningUsers = groupService.findJoiningUsers(groupModel.getId());
        List<User> users = joiningUsers.getUsers();
        for (User user : users) {
            user.getGroups().remove(joiningUsers);
            userService.save(user);
        }
//        groupService.findJoiningUsers(groupModel.getId()).getUsers().stream()
//                        .forEach(user -> {user.getGroups().remove(groupModel);
//                        userService.save(user);});

        groupService.deleteById(groupModel.getId());
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
    private String proceedUpdateForm(GroupModel groupModel){
        if (!groupService.verificationOfOversize(groupModel.getId(), groupModel.getUsers())){
            return "redirect:/admin/groups/update/{id}?oversize=true";
        }

        GroupModel groupToUpdate = groupService.findJoiningUsers(groupModel.getId());
        List<User> formerUsers = groupToUpdate.getUsers();
        formerUsers.stream()
                        .forEach(user -> {
                            user.getGroups().remove(groupToUpdate);
                            userService.save(user);
                        });
        groupModel.getUsers().stream()
                        .forEach(user -> {
                            user.getGroups().add(groupModel);
                            userService.save(user);
                        });
        groupService.save(groupModel);
        return "redirect:/admin/groups/"+groupModel.getId();
    }

    // select month for CalendarCard
    @GetMapping("/month/{groupId}")
    private String showSelectMonthForm(Model model, @PathVariable Long groupId){
        model.addAttribute("groupId", groupId);
        return "admin/groups/selectMonth";
    }

//    @GetMapping("/monthtest")
    @PostMapping("/monthtest")
    public String proceedSelectMonthForm(@RequestParam String groupId, @RequestParam Integer year, @RequestParam Integer month){
        String date = String.valueOf(LocalDate.of(year, month, 1));
        return "redirect:/admin/groups/"+ groupId+"?date="+date.toString();
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

//            model.addAttribute("callendarCard", calendarCellService.calendarCardForGroup(groupId, month, year));
            List<CalendarCell> cells = calendarCellService.calendarCardForGroup(groupId, month, year);
            Map<Integer, List<CalendarCell>> cellsMap =
                    cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
            List<List<CalendarCell>> weeks = new ArrayList<List<CalendarCell>>(cellsMap.values());
            model.addAttribute("weeks", weeks);

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
        return userService.findAll();
    }



                        // TEST LISTY GRUP Z WOLNYMI MIEJSCAMI!!!!
    @GetMapping("freeplaces")
    @ResponseBody
    private String findGroupsWithFreePlaces(){
        return groupService.findAllJoiningUsers().stream()
                .map(groupModel -> groupModel.getName() + " size: " + groupModel.getSize() + " members: " + String.valueOf(groupModel.getUsers().size()))
                .collect(Collectors.joining(" | ")) + "<br>  groupModelsWithFreePlaces: " + groupService.findGroupsWithFreePlaces().toString();
    }

}
