package pl.coderslab.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CalendarCell implements Cell{
    private LocalDate date;
    private BigDecimal addToFee;
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
                ", day='" + addToFee + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public void setDate(LocalDate localDate) {
        this.date = localDate;
    }

    @Override
    public BigDecimal getAddToFee() {
        return addToFee;
    }

    public void setAddToFee(BigDecimal addToFee) {
        this.addToFee = addToFee;
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
