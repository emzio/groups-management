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

    //findAll
    @GetMapping("")
    private String findAll(Model model){
        model.addAttribute("allCanceled", canceledClassesService.findAll());
        return "admin/CanceledClasses/canceled";
    }

    //add
    @GetMapping("/add")
    private String showAddForm(Model model){
        CanceledClasses canceledClasses = new CanceledClasses();
        model.addAttribute("canceled", canceledClasses);
        return "admin/canceledAdd";
    }

    @PostMapping("/add")
    private String proceedAddForm(CanceledClasses canceledClasses){
        canceledClassesService.save(canceledClasses);
        return "redirect:/admin/canceled";
    }

    //delete
    @GetMapping("/delete/{id}")
    private String deleteCanceledClass(@PathVariable Long id){
        canceledClassesService.deleteById(id);
        return "redirect:/admin/canceled";
    }

}
