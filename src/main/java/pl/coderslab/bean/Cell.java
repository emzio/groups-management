package pl.coderslab.bean;

import java.time.LocalDate;

public interface Cell {
    LocalDate getDate();
    void setDate(LocalDate localDate);
    Boolean getAddToFee();
    void setAddToFee(Boolean addToFee);
    String getDescription();
    void setDescription(String description);
}
