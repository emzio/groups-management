package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.repository.CanceledClassesRepository;
import pl.coderslab.repository.GroupModelRepository;
import pl.coderslab.repository.UserRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalendarCellService {

    private final UserService userService;
    private final GroupModelRepository groupModelRepository;
    private final CanceledClassesRepository canceledClassesRepository;

    public CalendarCellService(UserService userService, GroupModelRepository groupModelRepository, CanceledClassesRepository canceledClassesRepository) {
        this.userService = userService;
        this.groupModelRepository = groupModelRepository;
        this.canceledClassesRepository = canceledClassesRepository;
    }


    public List<LocalDate> classesDatesForGroup(GroupModel groupModel, Month month, Year year){
        List<LocalDate> monthDateList = new ArrayList<>();
            int length = month.length(Year.isLeap(year.getValue()));

            for (int i = 1; i <= length; i++) {
                if(LocalDate.of(year.getValue(), month, i).getDayOfWeek().equals(groupModel.getDayOfWeek())){
                    monthDateList.add(LocalDate.of(year.getValue(), month, i));
                }
            }
        return monthDateList;
    }

    public List<CalendarCell> emptyCalendarCard(Month month, Year year){
        List<CalendarCell> cells = new ArrayList<>();
        int length = month.length(Year.isLeap(year.getValue()));

        LocalDate firstDayOfMonth = LocalDate.of(year.getValue(), month, 1);
        int periodInt = firstDayOfMonth.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();

        for (int i = periodInt; i > 0 ; i--) {
            cells.add(new CalendarCell(LocalDate.of(year.getValue(), month, 1).minus(Period.ofDays(i))));
        }

        for (int i = 1; i <= length; i++) {
            cells.add(new CalendarCell(LocalDate.of(year.getValue(), month, i)));
        }
        return cells;
    }

    public List<CalendarCell> calendarCardForGroup(Long id, Month month, Year year){
        List<CalendarCell> cells = emptyCalendarCard(month, year);
        Optional<GroupModel> optionalGroupModel = groupModelRepository.findById(id);
        if(optionalGroupModel.isPresent()){
            GroupModel groupModel = optionalGroupModel.get();
            List<LocalDate> classesDates = classesDatesForGroup(groupModel, month, year);
            List<LocalDate> canceledClassesDates = canceledClassesRepository.findAll().stream()
                    .filter(canceledClasses -> classesDates.contains(canceledClasses.getLocalDate()))
                    .map(canceledClasses -> canceledClasses.getLocalDate())
                    .collect(Collectors.toList());
            cells.stream()
                    .forEach(calendarCell -> {
                        if (classesDates.contains(calendarCell.getDate())) {
                            calendarCell.setAddToFee(true);
                            calendarCell.setDescription(groupModel.getName() + " " + groupModel.getDayOfWeek().toString());
                        }
                        if(canceledClassesDates.contains(calendarCell.getDate())){
                            calendarCell.setAddToFee(false);
                            calendarCell.setDescription("Free Day!");
                        }
                    });
        }
        return cells;
    }

    public List<CalendarCell> calendarCardForUser(Long userId, Month month, Year year){
        User user = userService.findByIdWithGroupsAndPayments(userId);
        List<CalendarCell> cells = emptyCalendarCard(month, year);
        if(user!=null){
            List<GroupModel> groups = user.getGroups();

            for (GroupModel group : groups) {
                List<CalendarCell> cellsForGroup = calendarCardForGroup(group.getId(), month, year);
                List<LocalDate> dates = cellsForGroup.stream()
                        .filter(calendarCell -> calendarCell.getDescription() != null)
                        .map(calendarCell -> calendarCell.getDate())
                        .collect(Collectors.toList());
                cells.stream()
                        .forEach(calendarCell -> {
                            if(dates.contains(calendarCell.getDate())){
                                int index = IntStream.range(0, cellsForGroup.size())
                                        .filter(i -> cellsForGroup.get(i).getDate().equals(calendarCell.getDate()))
                                        .findFirst()
                                        .orElse(-1);

                                calendarCell.setDescription(calendarCell.getDescription() + cellsForGroup.get(index).getDescription()+calendarCell.getDate());
                                calendarCell.setAddToFee(cellsForGroup.get(index).getAddToFee());
                            }
                        });
            }
        }
        return cells;
    }


            // PONIŻEJ TESTOWE DO USUNIĘCIA?

//    public List<LocalDate> datesForGroupById(Long id, int monthNumber, int year){
//
//        List<LocalDate> monthDateList = new ArrayList<>();
//
//        Optional<GroupModel> optionalGroupModel = groupModelRepository.findById(id);
//        if(optionalGroupModel.isPresent()){
//            GroupModel groupModel = optionalGroupModel.get();
//            Year actualYear = Year.of(year);
//            Month actualMonth = Month.of(monthNumber);
//            int length = actualMonth.length(Year.isLeap(actualYear.getValue()));
//
//            for (int i = 1; i <= length; i++) {
//                if(LocalDate.of(actualYear.getValue(), actualMonth, i).getDayOfWeek().equals(groupModel.getDayOfWeek())){
//                    monthDateList.add(LocalDate.of(actualYear.getValue(), actualMonth, i));
//                }
//            }
//
//            canceledClassesRepository.findAll().stream()
//                    .map(canceledClasses -> canceledClasses.getLocalDate())
//                    .forEach(localDate -> {if(monthDateList.contains(localDate)){
//                        monthDateList.remove(localDate);
//                    }
//                    });
//        }
//
//        return monthDateList;
//    }
//
//    public List<CalendarCell> cellsForGroupById(Long id, int monthNumber, int yearNumber) {
//
//        Optional<GroupModel> optionalGroupModel = groupModelRepository.findById(id);
//
//        List<LocalDate> classDatesForGroup = datesForGroupById(id, monthNumber, yearNumber);
//        List<CalendarCell> cells = new ArrayList<>();
//        if(optionalGroupModel.isPresent()){
//            GroupModel groupModel = optionalGroupModel.get();
//            Month month = Month.of(monthNumber);
//            int length = month.length(Year.isLeap(yearNumber));
//
//            LocalDate firstDayOfMonth = LocalDate.of(yearNumber, monthNumber, 1);
//            int periodInt = firstDayOfMonth.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();
//
//            for (int i = periodInt; i > 0 ; i--) {
//                cells.add(new CalendarCell(LocalDate.of(yearNumber, monthNumber, 1).minus(Period.ofDays(i))));
//            }
//
//            for (int i = 1; i <= length; i++) {
//                cells.add(new CalendarCell(LocalDate.of(yearNumber, monthNumber, i)));
//            }
//
//            cells.stream()
//                    .forEach(calendarCell -> {
//                        if (classDatesForGroup.contains(calendarCell.getDate())) {
//                            calendarCell.setDay(groupModel.getDayOfWeek().toString());
//                            calendarCell.setDescription(groupModel.getName() + " " + groupModel.getLocalTime().toString());
//                        }
//                    });
//
//        }
//        return cells;
//    }
}
