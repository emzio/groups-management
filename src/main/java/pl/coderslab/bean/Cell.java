package pl.coderslab.bean;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Cell {
    LocalDate getDate();
    void setDate(LocalDate localDate);
    BigDecimal getAddToFee();
    void setAddToFee(BigDecimal addToFee);
    String getDescription();
    void setDescription(String description);
}
