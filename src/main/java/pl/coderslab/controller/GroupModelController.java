package pl.coderslab.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.Customer;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.service.CustomerService;
import pl.coderslab.service.GroupService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Collectors;

@Controller
//@Secured("ROLE_USER")
@RequestMapping("/user/groups")
public class GroupModelController {
    private final GroupService groupService;
    private final CustomerService customerService;

    public GroupModelController(GroupService groupService, CustomerService customerService) {
        this.groupService = groupService;
        this.customerService = customerService;
    }

    @GetMapping("")
    @ResponseBody
    private String findAll(){
        return groupService.findAll().stream()
                .map(GroupModel::toString)
                .collect(Collectors.joining(" | "));
//        return "Groups";
    }

    @GetMapping("/add")
//    @Secured("ROLE_USER")
    @ResponseBody
    private String add(){
        GroupModel group = new GroupModel();
        group.setName("nameTest");
        group.setDayOfWeek(DayOfWeek.FRIDAY);
        group.setLocalTime(LocalTime.parse("18:00"));
        Customer customer = customerService.findByIdWithGroups(1l);

        group.getCustomers().add(customer);
        customer.getGroups().add(group);

        groupService.save(group);
        customerService.save(customer);
        return group.toString();
    }
}
