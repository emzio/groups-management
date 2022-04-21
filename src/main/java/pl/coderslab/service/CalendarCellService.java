package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.repository.CanceledClassesRepository;
import pl.coderslab.repository.GroupModelRepository;
import pl.coderslab.repository.UserRepository;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalendarCellService {

    private final UserRepository userRepository;
    private final GroupModelRepository groupModelRepository;
    private final CanceledClassesRepository canceledClassesRepository;


    public CalendarCellService(UserRepository userRepository, GroupModelRepository groupModelRepository, CanceledClassesRepository canceledClassesRepository) {
        this.userRepository = userRepository;
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
                            calendarCell.setDay(groupModel.getDayOfWeek().toString());
                            calendarCell.setDescription(groupModel.getName() + " " + groupModel.getLocalTime().toString());
                        }
                        if(canceledClassesDates.contains(calendarCell.getDate())){
                            calendarCell.setDescription("Free Day!");
                        }
                    });
        }
        return cells;
    }

    public List<LocalDate> datesForGroupById(Long id, int monthNumber, int year){

        List<LocalDate> monthDateList = new ArrayList<>();

        Optional<GroupModel> optionalGroupModel = groupModelRepository.findById(id);
        if(optionalGroupModel.isPresent()){
            GroupModel groupModel = optionalGroupModel.get();
            Year actualYear = Year.of(year);
            Month actualMonth = Month.of(monthNumber);
            int length = actualMonth.length(Year.isLeap(actualYear.getValue()));

            for (int i = 1; i <= length; i++) {
                if(LocalDate.of(actualYear.getValue(), actualMonth, i).getDayOfWeek().equals(groupModel.getDayOfWeek())){
                    monthDateList.add(LocalDate.of(actualYear.getValue(), actualMonth, i));
                }
            }

            canceledClassesRepository.findAll().stream()
                    .map(canceledClasses -> canceledClasses.getLocalDate())
                    .forEach(localDate -> {if(monthDateList.contains(localDate)){
                        monthDateList.remove(localDate);
                    }
                    });
        }

        return monthDateList;
    }

    public List<CalendarCell> cellsForGroupById(Long id, int monthNumber, int yearNumber) {

        Optional<GroupModel> optionalGroupModel = groupModelRepository.findById(id);

        List<LocalDate> classDatesForGroup = datesForGroupById(id, monthNumber, yearNumber);
        List<CalendarCell> cells = new ArrayList<>();
        if(optionalGroupModel.isPresent()){
            GroupModel groupModel = optionalGroupModel.get();
            //        Year year = Year.of(yearNumber);
            Month month = Month.of(monthNumber);
            int length = month.length(Year.isLeap(yearNumber));
//        int length = Month.of(monthNumber).length(Year.isLeap(yearNumber));

//        DayOfWeek firstDayOfMonth = LocalDate.of(yearNumber, monthNumber, 1).getDayOfWeek();
//        for (int i = 1; i <= 7; i++) {
//            if(DayOfWeek.of(i).equals(firstDayOfMonth)){
//                break;
//            }
//            cells.add(new CalendarCell(LocalDate.of(yearNumber, monthNumber-1, i)));
//        }

            LocalDate firstDayOfMonth = LocalDate.of(yearNumber, monthNumber, 1);
            int periodInt = firstDayOfMonth.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue();

            for (int i = periodInt; i > 0 ; i--) {
                cells.add(new CalendarCell(LocalDate.of(yearNumber, monthNumber, 1).minus(Period.ofDays(i))));
            }

            for (int i = 1; i <= length; i++) {
                cells.add(new CalendarCell(LocalDate.of(yearNumber, monthNumber, i)));
            }

            cells.stream()
                    .forEach(calendarCell -> {
                        if (classDatesForGroup.contains(calendarCell.getDate())) {
                            calendarCell.setDay(groupModel.getDayOfWeek().toString());
                            calendarCell.setDescription(groupModel.getName() + " " + groupModel.getLocalTime().toString());
                        }
//                    classDatesForGroup.stream()
//                            .filter();

                    });

        }
        return cells;
    }
}
