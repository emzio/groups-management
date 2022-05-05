package pl.coderslab.Utils;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class MonthUtil {
    public static List<Month> allMonths(){
        List<Month> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(Month.of(i));
        }
        return months;
    }
}
