package pl.coderslab.bean;

import java.time.LocalDate;

public class CalendarCell implements Cell{
    private LocalDate date;
    private String day;
    private String description;

    public CalendarCell() {
    }

    public CalendarCell(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CalendarCell{" +
                "date=" + date +
                ", day='" + day + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate localDate) {
        this.date = localDate;
    }

    @Override
    public String getDay() {
        return day;
    }

    @Override
    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }
}
