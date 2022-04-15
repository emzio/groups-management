package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.service.GroupService;

import java.util.Collection;

@Controller
public class IndexController {
    private final GroupService groupService;

    public IndexController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("")
    private String showAllGroups(Model model){
        model.addAttribute("groups", groupService.findAll());
        return "/index";
    }

}
