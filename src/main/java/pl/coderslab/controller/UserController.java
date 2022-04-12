package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.User;
import pl.coderslab.service.DBGroupService;
import pl.coderslab.service.UserService;

import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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
//        user.getGroups()
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

    @GetMapping("/admin/users")
    @ResponseBody
    private String findAll(){
        String result = userService.findAll().stream()
                .map(user ->
                        String.join(" ; ", user.getUsername(), user.getEmail(), user.getName(), user.getLastName(), user.getRoles().toString()))
                .collect(Collectors.joining(" | "));
        return result;
    }

//    @GetMapping("/admin/user/add")
//    @ResponseBody
//    private String add(){
//        Customer customer = new Customer();
//        customer.setEmail("email@domain.pl");
//        customer.setName("Name");
//        customer.setLastName("Lastname");
////        GroupModel groupModel = dbGroupService.findById(2l).get();
////        customer.getGroups().add(groupModel);
//        customerService.save(customer);
//        return customer.toString();
//    }
}
