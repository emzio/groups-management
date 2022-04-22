package pl.coderslab.bean;

import java.time.LocalDate;

public interface Cell {
    LocalDate getDate();
    void setDate(LocalDate localDate);
    String getDay();
    void setDay(String day);
    String getDescription();
    void setDescription(String description);
}
