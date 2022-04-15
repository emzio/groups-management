package pl.coderslab.service;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@Service
public class MockDayOfWeekService implements DayOfWeekService{
    private List<String> daysOfWeek;

    public MockDayOfWeekService() {
        this.daysOfWeek = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            daysOfWeek.add(DayOfWeek.of(i).toString());
        }
    }

    @Override
    public List<String> findAll() {
        return daysOfWeek;
    }
}
