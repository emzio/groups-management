package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@Secured("ROLE_USER")
@RequestMapping("/admin/groups")
public class GroupModelController {
    private final GroupService groupService;
    private final CanceledClassesService canceledClassesService;
    private final UserService userService;
    private final DayOfWeekService dayOfWeekService;

    public GroupModelController(GroupService groupService, CanceledClassesService canceledClassesService, UserService userService, DayOfWeekService dayOfWeekService) {
        this.groupService = groupService;
        this.canceledClassesService = canceledClassesService;
        this.userService = userService;
        this.dayOfWeekService = dayOfWeekService;
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

    @ModelAttribute("daysOfWeek")
    Collection<String> findAllGroups(){
        return dayOfWeekService.findAll();
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
