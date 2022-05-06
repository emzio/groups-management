package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.Utils.MonthUtil;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;
import pl.coderslab.service.CalendarCellServiceInterface;
import pl.coderslab.service.DayOfWeekService;
import pl.coderslab.service.PaymentService;
import pl.coderslab.service.UserService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

@Controller
public class PaymentsController {
    private final UserService userService;
    private final PaymentService paymentService;
    private final CalendarCellServiceInterface calendarCellService;
    private final DayOfWeekService dayOfWeekService;

    public PaymentsController(UserService userService, PaymentService paymentService, CalendarCellServiceInterface calendarCellService, DayOfWeekService dayOfWeekService) {
        this.userService = userService;
        this.paymentService = paymentService;
        this.calendarCellService = calendarCellService;
        this.dayOfWeekService = dayOfWeekService;
    }

//    find all for user

    @GetMapping("/admin/payments/{userId}")
    private String paymentsForUser(Model model, @PathVariable Long userId, @RequestParam (required = false) String date){
        User user = userService.findByIdWithGroupsAndPayments(userId);
        List<Payment> payments = new ArrayList<>();
        if(user!=null) {
            payments = user.getPayments();
            Collections.sort(payments, (s,t)-> s.getPaymentCode().compareTo(t.getPaymentCode()));
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

        List<List<CalendarCell>> weeks = calendarCellService.divideCalendarCardIntoWeeks(cells);

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

    //Select month:

    @GetMapping("/admin/user/month/{userId}")
    private String showSelectMonthForm(Model model, @PathVariable Long userId){
        model.addAttribute("id", userId);

        model.addAttribute("months", MonthUtil.allMonths());
        model.addAttribute("actualYear", LocalDate.now().getYear());
        return "/admin/groups/selectMonth";
    }

    //    add new for user

    @PostMapping("/admin/user/month/{userId}")
    private String proceedSelectMonthForm(@PathVariable Long userId, @RequestParam Long id, @RequestParam Integer year, @RequestParam Month month) {
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
    private String showAddPaymentToUserForm(@Valid Payment payment, BindingResult result, @RequestParam Long userId){
        if (result.hasErrors()){
            return "admin/payments/addPayment";
        }
        User user = userService.findByIdWithGroupsAndPayments(userId);
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
