package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.service.CalendarCellService;
import pl.coderslab.service.DayOfWeekService;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class CalendarCellController {

    private final CalendarCellService calendarCellService;

    public CalendarCellController(CalendarCellService calendarCellService, DayOfWeekService dayOfWeekService) {
        this.calendarCellService = calendarCellService;
    }

    // Testowe @ResponseBody:
    @GetMapping("/admin/cells/user/{userId}/{monthInt}/{yearInt}")
    @ResponseBody
    private String cellsForUser(Model model, @PathVariable Long userId, @PathVariable int monthInt, @PathVariable int yearInt){
        LocalDate localDate = LocalDate.now();
        List<CalendarCell> cells = calendarCellService.calendarCardForUser(userId, Month.of(monthInt), Year.of(yearInt));
        return cells.stream()
                .map(CalendarCell::toString)
                .collect(Collectors.joining("<br>"));
    }

}
