package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;
import pl.coderslab.service.CalendarCellService;
import pl.coderslab.service.DayOfWeekService;
import pl.coderslab.service.PaymentService;
import pl.coderslab.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PaymentsController {
    private final UserService userService;
    private final PaymentService paymentService;
    private final CalendarCellService calendarCellService;
    private final DayOfWeekService dayOfWeekService;

    public PaymentsController(UserService userService, PaymentService paymentService, CalendarCellService calendarCellService, DayOfWeekService dayOfWeekService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.calendarCellService = calendarCellService;
        this.dayOfWeekService = dayOfWeekService;
    }

//    find all for user

    @GetMapping("/admin/payments/{userId}")
    private String paymentsForUser(Model model, @PathVariable Long userId, @RequestParam (required = false) String date){
        // porównanie metod pierwsza z JPQL może zwracać nulla!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        return userService.findWithPayments(userId).getPayments().toString();
//        return userService.findByIdWithGroups(userId).getPayments().toString();


//        User user = userService.findWithPayments(userId);
        User user = userService.findByIdWithGroups(userId);
        List<Payment> payments = new ArrayList<>();
        if(user!=null) {
            payments = userService.findByIdWithGroups(userId).getPayments();
        }
        model.addAttribute("paymentsForUser", payments);
        model.addAttribute("userId", userId);

//        CalendarCell:

        Month month = LocalDate.now().getMonth();
        Year year = Year.of(LocalDate.now().getYear());
        if(date!=null){
            LocalDate localDate = LocalDate.parse(date);
            month = localDate.getMonth();
            year = Year.of(localDate.getYear());
        }
        model.addAttribute("month", month.toString());
        model.addAttribute("year", year.toString());

        addUserCalendarCells(model, user, month, year);

        return "/admin/payments/payments";
    }

    private String addUserCalendarCells(Model model, User user, Month month, Year year){
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

//    add new for user

    //Select month:

    @GetMapping("/admin/user/month/{userId}")
    private String showSelectMonthForm(Model model, @PathVariable Long userId){
        model.addAttribute("id", userId);
        return "/admin/groups/selectMonth";
    }

    @PostMapping("/admin/user/month/{userId}")
    private String proceedSelectMonthForm(@PathVariable Long userId, @RequestParam Long id, @RequestParam Integer year, @RequestParam Integer month) {
        String date = String.valueOf(LocalDate.of(year, month, 1));
        return "redirect:/admin/payments/"+userId+"?date=" + date;
    }

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
