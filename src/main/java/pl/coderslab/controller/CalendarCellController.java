package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.service.CalendarCellService;
import pl.coderslab.service.DayOfWeekService;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
public class CalendarCellController {

    private final CalendarCellService calendarCellService;
    private final DayOfWeekService dayOfWeekService;

    public CalendarCellController(CalendarCellService calendarCellService, DayOfWeekService dayOfWeekService) {
        this.calendarCellService = calendarCellService;
        this.dayOfWeekService = dayOfWeekService;
    }

    @GetMapping("/admin/cells/{groupId}/{monthInt}/{yearInt}")
    private String cellsForGroupById(Model model, @PathVariable Long groupId, @PathVariable int monthInt, @PathVariable int yearInt){
        Month month = Month.of(monthInt);
        Year year = Year.of(yearInt);
        model.addAttribute("callendarCard", calendarCellService.calendarCardForGroup(groupId, month, year));
        List<CalendarCell> cells = calendarCellService.calendarCardForGroup(groupId, month, year);
        Map<Integer, List<CalendarCell>> cellsMap =
                cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
        List<List<CalendarCell>> weeks = new ArrayList<List<CalendarCell>>(cellsMap.values());
        model.addAttribute("weeks", weeks);
        return "admin/groups/calendarCard";
    }

    // Testowe @ResponseBody:


    @GetMapping("/admin/cells/user/{userId}/{monthInt}/{yearInt}")
    @ResponseBody
    private String groupForUser(Model model, @PathVariable Long userId, @PathVariable int monthInt, @PathVariable int yearInt){
        List<CalendarCell> cells = calendarCellService.calendarCardForUser(userId, Month.of(monthInt), Year.of(yearInt));
        return cells.stream()
                .map(CalendarCell::toString)
                .collect(Collectors.joining("<br>"));
    }

    @GetMapping("/admin/dates/{groupId}/{month}/{year}")
    @ResponseBody
    private String classesForGroupById(@PathVariable Long groupId, @PathVariable int month, @PathVariable int year){
        return calendarCellService.datesForGroupById(groupId, month, year).toString();
    }

    @GetMapping("/admin/cells3/{groupId}/{month}/{year}")
    @ResponseBody
    private String cellsForGroupById3(@PathVariable Long groupId, @PathVariable int month, @PathVariable int year){
        return calendarCellService.cellsForGroupById(groupId, month, year).toString();
    }

    @GetMapping("/admin/cells2/{groupId}/{monthInt}/{yearInt}")
    @ResponseBody
    private String cellsForGroupById2(@PathVariable Long groupId, @PathVariable int monthInt, @PathVariable int yearInt){
        Month month = Month.of(monthInt);
        Year year = Year.of(yearInt);

        List<CalendarCell> cells = calendarCellService.calendarCardForGroup(groupId, month, year);
//        List<List<CalendarCell>> weeks = new ArrayList<>();
//        List<CalendarCell> week = new ArrayList<>();
//        for (int i = 0; i < cells.size(); i=i+7) {
//            for (int j = i%7 ; j < i%7+7; j++) {
//                week.add(cells.get(i));
//            }
//            weeks.add(week);
//        }

        Map<Integer, List<CalendarCell>> cellsMap =
                cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
        List<List<CalendarCell>> subSets = new ArrayList<List<CalendarCell>>(cellsMap.values());
        return subSets.toString();
    }


    @ModelAttribute("daysOfWeek")
    Collection<String> findAllGroups(){
        return dayOfWeekService.findAll();
    }
}
