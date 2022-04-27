package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;
import pl.coderslab.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PaymentsController {
    private final UserService userService;

    public PaymentsController(UserService userService) {
        this.userService = userService;
    }

//    find all for user

    @GetMapping("/admin/payments/{userId}")
    private String paymentsForUser(Model model, @PathVariable Long userId){
        // porównanie metod pierwsza z JPQL może zwracać nulla!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        return userService.findWithPayments(userId).getPayments().toString();
//        return userService.findByIdWithGroups(userId).getPayments().toString();

        User user = userService.findWithPayments(userId);
        List<Payment> payments = new ArrayList<>();
        if(user!=null) {
            payments = userService.findWithPayments(userId).getPayments();
        }
        model.addAttribute("paymentsForUser", payments);
        model.addAttribute("userId", userId);
        return "/admin/payments/payments";
    }

//    add new for user

    @GetMapping("/admin/addPayment/{userId}")
    private String showAddPaymentToUserForm(Model model, @PathVariable Long userId){
        Payment payment = new Payment();
        model.addAttribute("userId", userId);
        model.addAttribute("payment", payment);
        return "admin/payments/addPayment";
    }

    @PostMapping("/admin/addPayment/{userId}")
    private String showAddPaymentToUserForm(Payment payment, @RequestParam Long userId){
        User user = userService.findByIdWithGroups(userId);
//        User user = userService.findWithPayments(userId);
        userService.addPaymentToUser(user, payment);
        return "redirect:/admin/payments/"+userId;
    }
}
