package pl.coderslab.service;

import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.GroupModel;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;

public interface CalendarCellServiceInterface {

    List<LocalDate> classesDatesForGroup(GroupModel groupModel, Month month, Year year);

    List<CalendarCell> emptyCalendarCard(Month month, Year year);

    public List<CalendarCell> calendarCardForGroup(Long id, Month month, Year year);

    public List<CalendarCell> calendarCardForUser(Long userId, Month month, Year year);

    List<List<CalendarCell>> divideCalendarCardIntoWeeks(List<CalendarCell> cells);

    void addEndOfWeek(List<CalendarCell> cells, Month month, Year year);
}
