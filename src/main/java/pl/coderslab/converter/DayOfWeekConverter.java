package pl.coderslab.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class DayOfWeekConverter implements Converter<String, DayOfWeek> {

    @Override
    public DayOfWeek convert(String source) {
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(source);
        return dayOfWeek;
    }
}
