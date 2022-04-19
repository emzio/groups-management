package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.service.CurrentUser;
import pl.coderslab.service.GroupService;
import pl.coderslab.service.UserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/admin/users")
    private String findAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return "/admin/users/users";
    }

    @GetMapping("admin/users/delete/{id}")
    private String deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

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
//    @ResponseBody
    private String proceedUserUpdateForm(User user){
//        String formerPassword = userService.findById(user.getId()).get().getPassword();
//        String result = "Zmieniono hasło!!!";
//            if(user.getPassword().equals(formerPassword)){
//                user.setPassword(formerPassword);
//                result = "Nie zmieniono hasła";
//
//        }
//            userService.save(user);
//        return result;
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

    @GetMapping("admin/userwg/{id}")
    @ResponseBody
    private String userWithGroupList(@PathVariable Long id){
        return userService.findByIdWithGroups(id).getGroups().toString();
    }

    @GetMapping("/create-user/{userName}")
    @ResponseBody
    public String createUser(@PathVariable String userName) {
        User user = new User();
        user.setUsername(userName);
        user.setPassword("admin");
        user.setEmail("email@domena.pl");
        user.setName("Michal");
        user.setLastName("Ziółkowski");
        user.getGroups();
        userService.saveUser(user);
        return "admin";
    }

    @GetMapping("/create-admin")
    @ResponseBody
    private String createAdmin(){
        User user = new User();
        user.setUsername("admin3");
        user.setPassword("admin3");
        userService.saveAdmin(user);
        return "admin2";
    }

    @GetMapping("/admin/infoadmin")
    @ResponseBody
    public String userInfo(@AuthenticationPrincipal UserDetails customUser) {
//        log.info("customUser class {} " , customUser.getClass());
        return "You are logged as " + customUser;
    }

    @GetMapping("/admin/users2")
    @ResponseBody
    private String findAll(){
        String result = userService.findAll().stream()
                .map(user ->
                        String.join(" ; ", user.getUsername(), user.getEmail(), user.getName(), user.getLastName(), user.getRoles().toString()))
//        String.join(" ; ", user.getUsername(), user.getRoles().toString()))
                .collect(Collectors.joining(" | "));
        return result.toString();
    }

    @GetMapping("/admin/info")
    @ResponseBody
    public String admin(@AuthenticationPrincipal CurrentUser customUser) {
        User entityUser = customUser.getUser();
        return "Hello " + entityUser.getUsername() + entityUser.getRoles().toString();
    }

//    @GetMapping("/admin/user/add")
//    @ResponseBody
//    private String add(){
//        User user = new User();
//        User.setEmail("email@domain.pl");
//        User.setName("Name");
//        User.setLastName("Lastname");
////        GroupModel groupModel = dbGroupService.findById(2l).get();
////        customer.getGroups().add(groupModel);
//        userService.save(user);
//        return User.toString();
//    }
}
