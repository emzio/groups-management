package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;
import pl.coderslab.service.PaymentService;
import pl.coderslab.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class PaymentsController {
    private final UserService userService;
    private final PaymentService paymentService;

    public PaymentsController(UserService userService, PaymentService paymentService) {
        this.userService = userService;
        this.paymentService = paymentService;
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

    @GetMapping("/admin/deletePayment/{userId}/{paymentId}")
    private String showDeletePaymentForm(Model model, @PathVariable Long userId, @PathVariable Long paymentId){
        Optional<Payment> optionalPayment = paymentService.findById(paymentId);
        if (optionalPayment.isEmpty()){
            return "redirect:/admin/groups/notfound";
        }
        model.addAttribute("payment", optionalPayment.get());
        model.addAttribute("userId", userId);
        return "admin/payments/delete";
    }

    @PostMapping("/admin/deletePayment/{userId}/{paymentId}")
    private String proceedDeleteForm(@RequestParam Long userId, @RequestParam Long paymentId){
        userService.deletePaymentForUser(userId, paymentId);
        return "redirect:/admin/payments/"+userId;
    }
}
