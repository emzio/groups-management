package pl.coderslab.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.coderslab.entity.CanceledClasses;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

@Component
public class DayOfWeekConverter implements Converter<String, DayOfWeek> {

    @Override
    public DayOfWeek convert(String source) {
//        DayOfWeek dayOfWeek = null;
//                switch (source) {
//            case "SUNDAY":
//                dayOfWeek = (DayOfWeek.SUNDAY);
//                break;
//            case "SATURDAY":
//                dayOfWeek = (DayOfWeek.SATURDAY);
//                break;
//            default:
//                dayOfWeek = DayOfWeek.valueOf(source);
//        }
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(source);
        return dayOfWeek;
    }
}
