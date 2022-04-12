package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.CanceledClassesService;
import pl.coderslab.service.GroupService;
import pl.coderslab.service.UserService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
//@Secured("ROLE_USER")
@RequestMapping("/user/groups")
public class GroupModelController {
    private final GroupService groupService;
    private final CanceledClassesService canceledClassesService;
    private final UserService userService;

    public GroupModelController(GroupService groupService, CanceledClassesService canceledClassesService, UserService userService) {
        this.groupService = groupService;
        this.canceledClassesService = canceledClassesService;
        this.userService = userService;
    }

    @GetMapping("")
    @ResponseBody
    private String findAll(){
        return groupService.findAll().stream()
                .map(GroupModel::toString)
                .collect(Collectors.joining(" | "));
    }

    @GetMapping("/add")
//    @Secured("ROLE_USER")
    @ResponseBody
    private String add(){
        GroupModel group = new GroupModel();
        group.setName("nameTest");
        group.setDayOfWeek(DayOfWeek.FRIDAY);
        group.setLocalTime(LocalTime.parse("18:00"));
        group.setSize(6);
        User user = userService.findByIdWithGroups(1l);

//        Customer customer = customerService.findByIdWithGroups(1l);

        group.getUsers().add(user);
//        group.getCustomers().add(customer);

        user.getGroups().add(group);

//        customer.getGroups().add(group);

        groupService.save(group);
        userService.save(user);

//        customerService.save(customer);
        return group.toString();
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
