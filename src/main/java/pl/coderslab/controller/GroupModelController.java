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
//@Secured("ROLE_USER")
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

    @GetMapping("/add")
    private String showAddForm(Model model){
        GroupModel groupModel = new GroupModel();
        model.addAttribute("groupModel" , groupModel);
        return "/admin/groups/add";
    }

    @PostMapping("/add")
    @ResponseBody
    private String proceedAddForm(GroupModel groupModel){
        groupService.save(groupModel);
        return groupModel.toString();
    }

    @GetMapping("/update/{id}")
    private String showUpdateForm(@PathVariable Long id, Model model){
        model.addAttribute("groupModel" , groupService.findJoiningUsers(id));
        return "/admin/groups/update";
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    private String proceedUpdateForm(GroupModel groupModel){
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
        return groupModel.toString();
    }

    @GetMapping("/addUser/{id}")
    private String addUserShowForm(@PathVariable Long id, Model model){
//        Optional<GroupModel> byId = groupService.findById(id);
//        if(byId.isEmpty()){
//            return "redirect:/error";
////            return "redirect:/form/books";
//        }
        GroupModel joiningUsers = groupService.findJoiningUsers(id);
        model.addAttribute("groupForUser", joiningUsers);
        return "admin/groups/adduser";
    }

    @PostMapping("/addUser/{id}")
    @ResponseBody
    private String addUserProceedForm(GroupModel groupModel){

        groupModel.getUsers().stream()
                .forEach(user -> {
                    user.getGroups().add(groupModel);
                    userService.save(user);
                });
        groupService.save(groupModel);
        return groupModel.toString() + " users: "  +  groupModel.getUsers().toString();
    }

    @GetMapping("/month/{groupId}")
    private String showSelectMonthForm(Model model, @PathVariable Long groupId){
        model.addAttribute("groupId", groupId);
        return "admin/groups/selectMonth";
    }

    @GetMapping("/monthtest")
    @ResponseBody
    private String proceedSelectMonthForm(Model model, @RequestParam String date){
        model.addAttribute("date", date);
        return date;
    }

    @GetMapping("/{groupId}")
    private String groupDetails(@PathVariable Long groupId, Model model){

        Optional<GroupModel> optionalGroupModel = groupService.findById(groupId);

        if(optionalGroupModel.isPresent()){
            model.addAttribute("group", groupService.findJoiningUsers(groupId));

            Month month = LocalDate.now().getMonth();
            Year year = Year.of(LocalDate.now().getYear());
            model.addAttribute("callendarCard", calendarCellService.calendarCardForGroup(groupId, month, year));
            List<CalendarCell> cells = calendarCellService.calendarCardForGroup(groupId, month, year);
            Map<Integer, List<CalendarCell>> cellsMap =
                    cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
            List<List<CalendarCell>> weeks = new ArrayList<List<CalendarCell>>(cellsMap.values());
            model.addAttribute("weeks", weeks);

            return "admin/groups/details";
        }
        return "redirect:/admin/groups/notfound";
    }
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

    @GetMapping("/notfound")
    private String notFound(){
        return "/notfound";
    }

                        // TEST LISTY GRUP Z WOLNYMI MIEJSCAMI!!!!



    @GetMapping("freeplaces")
    @ResponseBody
    private String findGroupsWithFreePlaces(){
        return groupService.findAllJoiningUsers().stream()
                .map(groupModel -> groupModel.getName() + " size: " + groupModel.getSize() + " members: " + String.valueOf(groupModel.getUsers().size()))
                .collect(Collectors.joining(" | ")) + "<br>  groupModelsWithFreePlaces: " + groupService.findGroupsWithFreePlaces().toString();
    }

    @ModelAttribute("daysOfWeek")
    Collection<String> findAllGroups(){
        return dayOfWeekService.findAll();
    }

    @ModelAttribute("users")
    Collection<User> findAllUsers(){
        return userService.findAll();
    }


    // PONIŻEJ AKCJE TESTOWE, BEZ FORMULARZY!!!

    @GetMapping("")
    @ResponseBody
    private String findAll(){
        return groupService.findAll().stream()
                .map(GroupModel::toString)
                .collect(Collectors.joining(" | "));
    }

    @GetMapping("/fwu/{id}")
    @ResponseBody
    private String findWithUsers(@PathVariable Long id){
        return groupService.findJoiningUsers(id).getUsers().stream()
                .map(user -> user.getUsername())
                .collect(Collectors.joining(" | "));
    }

    @GetMapping("/add2")
    @ResponseBody
    private String add(){
        GroupModel group = new GroupModel();
        group.setName("nameTest");
        group.setDayOfWeek(DayOfWeek.FRIDAY);
        group.setLocalTime(LocalTime.parse("18:00"));
        group.setSize(6);
        User user = userService.findByIdWithGroups(2l);
        group.getUsers().add(user);

        user.getGroups().add(group);

        groupService.save(group);
        userService.save(user);

        return group.toString() + "DAYSOFWEEK: ";
    }

    @GetMapping("/fcd/{id}")
    @ResponseBody
    private String findClassDates(@PathVariable Long id){
        GroupModel groupModel = groupService.findById(id).get();
        Year actualYear = Year.of(LocalDate.now().getYear());
        int length = actualYear.length();

        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 1; i <= length; i++) {
            if(actualYear.atDay(i).getDayOfWeek().equals(groupModel.getDayOfWeek())){
                dateList.add(actualYear.atDay(i));
            }
        }
        List<LocalDate> dateListToCompare = new ArrayList<>(dateList);

        canceledClassesService.findAll().stream()
                .map(canceledClasses -> canceledClasses.getLocalDate())
                .forEach(localDate -> {if(dateList.contains(localDate)){
                    dateList.remove(localDate);
                }
                });

        return "<h3>" + "lista dat zajęć dla grupy: " + dateListToCompare.toString() + "</h3>"
                + "<br>" + "lista bez odwołanych zajęć: " + dateList.toString();
    }

}
