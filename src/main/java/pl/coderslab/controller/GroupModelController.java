package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.service.GroupService;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/groups")
public class GroupModelController {
    private final GroupService groupService;

    public GroupModelController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    @ResponseBody
    private String findAll(){
        return groupService.findAll().stream()
                .map(GroupModel::toString)
                .collect(Collectors.joining(" | "));
    }

    @GetMapping("/add")
    @ResponseBody
    private String add(){
        GroupModel group = new GroupModel();
        group.setName("nameTest");
        group.setDayOfWeek(DayOfWeek.FRIDAY);
        group.setLocalTime(LocalTime.parse("18:00"));
        groupService.save(group);
        return group.toString();
    }
}
