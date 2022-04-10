package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.CanceledClasses;
import pl.coderslab.entity.Customer;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.service.CanceledClassesService;
import pl.coderslab.service.CustomerService;
import pl.coderslab.service.GroupService;

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
    private final CustomerService customerService;
    private final CanceledClassesService canceledClassesService;

    public GroupModelController(GroupService groupService, CustomerService customerService, CanceledClassesService canceledClassesService) {
        this.groupService = groupService;
        this.customerService = customerService;
        this.canceledClassesService = canceledClassesService;
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
        Customer customer = customerService.findByIdWithGroups(1l);

        CanceledClasses canceledClass = canceledClassesService.findById(1L).get();

        group.getCanceledClasses().add(canceledClass);


        group.getCustomers().add(customer);
        customer.getGroups().add(group);

        groupService.save(group);
        customerService.save(customer);
        return group.toString();
    }

    @GetMapping("/canceled/{canceledId}")
    @ResponseBody
    private String addCanceledClass(@PathVariable Long canceledId){
        GroupModel groupModel = groupService.findByIdWithCanceledClasses(1l).get();
        CanceledClasses canceledClasses = canceledClassesService.findById(canceledId).get();
        groupModel.getCanceledClasses().add(canceledClasses);
        groupService.save(groupModel);
        return groupModel.toString();
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
