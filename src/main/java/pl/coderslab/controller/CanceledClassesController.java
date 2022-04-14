package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.entity.CanceledClasses;
import pl.coderslab.service.CanceledClassesService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/canceled")
public class CanceledClassesController {
    private final CanceledClassesService canceledClassesService;

    public CanceledClassesController(CanceledClassesService canceledClassesService) {
        this.canceledClassesService = canceledClassesService;
    }

    @GetMapping("/add")
    private String showAddForm(Model model){
        CanceledClasses canceledClasses = new CanceledClasses();
        model.addAttribute("canceled", canceledClasses);
        return "admin/canceledAdd";
    }

    @GetMapping("")
    @ResponseBody
    private String findAll(){
        return canceledClassesService.findAll().stream()
                .map(CanceledClasses::toString)
                .collect(Collectors.joining(" | "));
    }

    // BAJZEL Z METODĄ POST:

////    @PostMapping("/add")
//    @GetMapping("add2")
//    @ResponseBody
//    private String proceedAddForm(@RequestParam String dateTest){
////        private String proceedAddForm(){
//        CanceledClasses canceledClasses = new CanceledClasses();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        LocalDate localDate = (LocalDate.parse(dateTest,formatter));
//        canceledClasses.setLocalDate(localDate);
//        canceledClassesService.save(canceledClasses);
//        return "saved: " + canceledClasses;
//    }
//
//    @PostMapping("/add2")
//    @ResponseBody
//    private String proceedAddFormPost(@RequestParam String dateTest){
////        private String proceedAddForm(){
//        CanceledClasses canceledClasses = new CanceledClasses();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//        LocalDate localDate = (LocalDate.parse(dateTest,formatter));
//        canceledClasses.setLocalDate(localDate);
//        canceledClassesService.save(canceledClasses);
//        return "saved: " + canceledClasses;
//    }

    // BAJZEL Z METODĄ POST (KONIEC)

    @PostMapping("/add")
    @ResponseBody
    private String proceedAddForm(CanceledClasses canceledClasses){
        canceledClassesService.save(canceledClasses);
        return "saved: " + canceledClasses;
    }

    @GetMapping("/canceled/{date}")
    @ResponseBody
    private String addCanceledClass(@PathVariable String date){
        CanceledClasses canceledClasses = new CanceledClasses();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = (LocalDate.parse(date,formatter));
        canceledClasses.setLocalDate(localDate);
        canceledClassesService.save(canceledClasses);
        return canceledClasses.toString();
    }

}
