package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.repository.CanceledClassesRepository;
import pl.coderslab.repository.GroupModelRepository;

import java.math.BigDecimal;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CalendarCellService implements CalendarCellServiceInterface{

    private final UserService userService;
    private final GroupModelRepository groupModelRepository;
    private final CanceledClassesRepository canceledClassesRepository;

    public CalendarCellService (UserService userService, GroupModelRepository groupModelRepository, CanceledClassesRepository canceledClassesRepository) {
        this.userService = userService;
        this.groupModelRepository = groupModelRepository;
        this.canceledClassesRepository = canceledClassesRepository;
    }

    @Override
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

    @Override
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

    @Override
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
                            calendarCell.setAddToFee(groupModel.getPaymentRate());
                            calendarCell.setDescription(groupModel.getName() );
                        }
                        if(canceledClassesDates.contains(calendarCell.getDate())){
                            calendarCell.setAddToFee(BigDecimal.valueOf(0));
                            calendarCell.setDescription("Free Day!");
                        }
                    });
        }
        return cells;
    }

    @Override
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

                                calendarCell.setDescription(cellsForGroup.get(index).getDescription());
                                calendarCell.setAddToFee(cellsForGroup.get(index).getAddToFee());
                            }
                        });
            }
        }
        return cells;
    }

    public List<List<CalendarCell>> divideCalendarCardIntoWeeks(List<CalendarCell> cells){
        Map<Integer, List<CalendarCell>> cellsMap =
                cells.stream().collect(Collectors.groupingBy(calendarCell -> cells.indexOf(calendarCell)/7));
        List<List<CalendarCell>> weeks = new ArrayList<List<CalendarCell>>(cellsMap.values());
        return weeks;
    }
}
