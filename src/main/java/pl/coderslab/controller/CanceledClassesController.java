package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.entity.CanceledClasses;
import pl.coderslab.service.CanceledClassesService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class CanceledClassesController {
    private final CanceledClassesService canceledClassesService;

    public CanceledClassesController(CanceledClassesService canceledClassesService) {
        this.canceledClassesService = canceledClassesService;
    }

    @GetMapping("canceled/{date}")
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
