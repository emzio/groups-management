package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.GroupService;
import pl.coderslab.service.UserService;

import java.util.Collection;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;
    private final GroupService groupService;

    public UserController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @GetMapping("/user/start")
    private String adminOrUserView(@AuthenticationPrincipal UserDetails customUser, Model model){

        if (customUser != null && customUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            model.addAttribute("users", userService.findAll());
            return "/admin/adminstart";
        }
        return "/user/userstart";
    }

    // findAll
    @GetMapping("/admin/users")
    private String findAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "/admin/users/users";
    }
    // add admin
    @GetMapping("/admin/users/addadmin")
    private String showAdminAddForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registry";
    }

    @PostMapping("/admin/users/addadmin")
    private String proceedAdminAddForm(User user) {
        userService.saveAdmin(user);
        return "redirect:/admin/users";
    }

    //add user

    @GetMapping("/registry")
    private String showUserAddForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "registry";
    }

    @PostMapping("/registry")
    private String proceedUserAddForm(User user){
        userService.saveUser(user);
        return "redirect:login";
    }

    //delete

    @GetMapping("admin/users/delete/{id}")
    private String deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    //update
    @GetMapping("/admin/users/update/{id}")
    private String showUserUpdateForm(@PathVariable Long id, Model model){
        Optional<User> optionalUser = userService.findById(id);
        if(optionalUser.isPresent()){
            model.addAttribute("userToUpdate",optionalUser.get());
            return "admin/users/update";
        }
        return "redirect:/admin/groups/notfound";
    }

    @PostMapping("/admin/users/update/{id}")
    private String proceedUserUpdateForm(User user){
        userService.update(user);
        return "redirect:/admin/users";
    }

    @ModelAttribute("groups")
    Collection<GroupModel> findAllGroups(){
        return groupService.findAll();
    }

    @ModelAttribute("freeGroups")
    Collection<GroupModel> findAllGroupsWithFreePlaces(){
        return groupService.findGroupsWithFreePlaces();
    }


    // PONIŻEJ AKCJE TESTOWE - BEZ FORMULARZY !!!
    @GetMapping("/create-admin")
    @ResponseBody
    private String createAdmin(){
        User user = new User();
        user.setUsername("admin3");
        user.setPassword("admin3");
        userService.saveAdmin(user);
        return "admin2";
    }
}
