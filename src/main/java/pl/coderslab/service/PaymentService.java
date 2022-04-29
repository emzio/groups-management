package pl.coderslab.service;


import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PaymentService {

   Optional<Payment> findById(Long paymentId);

   void save(Payment payment);

   void delete(Payment payment);

   public Map<String, BigDecimal> paymentAndClasses(List<CalendarCell> cells);
}
