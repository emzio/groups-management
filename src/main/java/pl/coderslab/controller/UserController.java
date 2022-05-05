package pl.coderslab.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserService userService;
    private final GroupService groupService;
    private final CalendarCellService calendarCellService;
    private final DayOfWeekService dayOfWeekService;

    private final PaymentService paymentService;
    public UserController(UserService userService, GroupService groupService, CalendarCellService calendarCellService, DayOfWeekService dayOfWeekService, PaymentService paymentService) {
        this.userService = userService;
        this.groupService = groupService;
        this.calendarCellService = calendarCellService;
        this.dayOfWeekService = dayOfWeekService;
        this.paymentService = paymentService;
    }

    //Pierwszy widok po zalogowaniu:

    @GetMapping("/user/start")
    private String adminOrUserView(@AuthenticationPrincipal UserDetails customUser,@RequestParam(required = false) String date, Model model){

        if (customUser != null && customUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            return "/admin/adminstart";
        }

        Month month = LocalDate.now().getMonth();
        Year year = Year.of(LocalDate.now().getYear());
        if(date!=null){
            LocalDate localDate = LocalDate.parse(date);
            month = localDate.getMonth();
            year = Year.of(localDate.getYear());
        }
        model.addAttribute("month", month.toString());
        model.addAttribute("year", year.toString());
        User user = userService.findByUserNameWithGroupsAndPayments(customUser.getUsername());
        return userStartView(model, user, month, year);
    }



    private String userStartView(Model model, User user, Month month, Year year){
//        List<CalendarCell> cells = calendarCellService.calendarCardForUser(user.getId(), LocalDate.now().getMonth(), Year.of(LocalDate.now().getYear()));
        List<CalendarCell> cells = calendarCellService.calendarCardForUser(user.getId(), month, year);

        Map<Integer, List<CalendarCell>> cellsMap =
                cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
        List<List<CalendarCell>> weeks = new ArrayList<List<CalendarCell>>(cellsMap.values());

        model.addAttribute("groupsForUser", user.getGroups());
        model.addAttribute("userId", user.getId());
        model.addAttribute("daysOfWeek" ,dayOfWeekService.findAll());
        model.addAttribute("weeksForUser", weeks);

        // Informacje o płatnościach:
        Map<String, BigDecimal> paymentsInfo = paymentService.paymentAndClasses(cells);
        model.addAttribute("numberOfClasses",paymentsInfo.get("numberOfClasses"));
        model.addAttribute("paymentAmount", paymentsInfo.get("paymentAmount"));
        return "/user/userstart";
    }

    // findAll
    @GetMapping("/admin/users")
    private String findAllUsers(Model model){
        model.addAttribute("users", userService.findAllActiveWithGroupsAndPayments());
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
    private String proceedUserAddForm(@Valid User user, BindingResult result){
        if(result.hasErrors()){
            return "registry";
        }
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
        User user = userService.findByIdWithGroupsAndPayments(id);
        if(user!=null){
            model.addAttribute("userToUpdate",user);
            return "admin/users/update";
        }
        return "redirect:/admin/groups/notfound";
    }

    @PostMapping("/admin/users/update/{id}")
    private String proceedUserUpdateForm(@ModelAttribute("userToUpdate")@Valid User user, BindingResult result){
        if (result.hasErrors()){
            return "admin/users/update";
        }
        userService.update(user);
        return "redirect:/admin/users";
    }

    // Select Month:
    @GetMapping("/user/month/{userId}")
    private String showSelectMonthForm(Model model, @PathVariable Long userId){
        model.addAttribute("id", userId);

        List<Month> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Month.of(i));
        }
        model.addAttribute("months",months);
        model.addAttribute("actualYear", LocalDate.now().getYear());
        return "admin/groups/selectMonth";
    }

    @PostMapping("/user/month/{userId}")
    private String proceedSelectMonthForm(@RequestParam Long id, @RequestParam Integer year, @RequestParam Month month){
        String date = String.valueOf(LocalDate.of(year, month, 1));
        return "redirect:/user/start/?date="+date.toString();
    }


    @ModelAttribute("groups")
    Collection<GroupModel> findAllGroups(){
        return groupService.findAll();
    }

    @ModelAttribute("freeGroups")
    Collection<GroupModel> findAllGroupsWithFreePlaces(){
        return groupService.findGroupsWithFreePlaces();
    }

    // PONIŻEJ AKCJE TESTOWE !!!



    @GetMapping("/create-admin")
    @ResponseBody
    private String createAdmin(){
        User user = new User();
        user.setUsername("admin3");
        user.setPassword("admin3");
        userService.saveAdmin(user);
        return "admin2";
    }

    @GetMapping("test/{id}")
    @ResponseBody
    private String testUserNotEager(@PathVariable Long id){


        return userService.findUsersOutOfGroup(groupService.findJoiningUsers(id)).stream()
                .map(user -> String.join("|||",user.getName(), user.getId().toString(), user.getUsername()))
                .collect(Collectors.joining("<br>"));

//        List<User> allActive = userService.findAllActiveWithGroupsAndPayments();
//        return allActive.stream()
//                .map(user -> user.getPayments()+ "|||||||||||" + user.getGroups() )
//                .collect(Collectors.joining("<br>"));

//        User byIdWithGroupsAndPayments = userService.findByIdWithGroupsAndPayments(id);
//        List<GroupModel> groupsForUserId = userService.findGroupsForUserId(id);
//        return "groupModels : " + groupsForUserId + "<br>" + "payments: "+ byIdWithGroupsAndPayments.getPayments() + "<br>" + " groups " + byIdWithGroupsAndPayments.getGroups();
    }
}
