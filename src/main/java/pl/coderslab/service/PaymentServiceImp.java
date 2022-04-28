package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.bean.CalendarCell;
import pl.coderslab.entity.Payment;
import pl.coderslab.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImp implements PaymentService{
    private final PaymentRepository paymentRepository;

    public PaymentServiceImp(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Optional<Payment> findById(Long paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public void delete(Payment payment) {
        paymentRepository.delete(payment);
    }

    @Override
    public Map<String, BigDecimal> paymentAndClasses (List<CalendarCell> cells){
        Map<String, BigDecimal> paymentsInfo = new HashMap<>();
        long count = cells.stream()
                .filter(calendarCell -> calendarCell.getAddToFee() != null)
                .filter(calendarCell -> calendarCell.getAddToFee() == true)
                .count();
        paymentsInfo.put("numberOfClasses", BigDecimal.valueOf(count));
        BigDecimal paymentRate = new MockPaymentRate().getPaymentRate();
        BigDecimal multiply = paymentRate.multiply(BigDecimal.valueOf(count));
        paymentsInfo.put("paymentAmount", multiply);
        return paymentsInfo;
    }
//    @Override
//    public Map<String, BigDecimal> paymentAndClassesForUser(List<CalendarCell> cells){
//        Map<String, BigDecimal> paymentsInfo = new HashMap<>();
//        long count = cells.stream()
//                .filter(calendarCell -> calendarCell.getAddToFee() != null)
//                .filter(calendarCell -> calendarCell.getAddToFee() == true)
//                .count();
//        paymentsInfo.put("numberOfClasses", BigDecimal.valueOf(count));
//        BigDecimal paymentRate = new MockPaymentRate().getPaymentRate();
//        BigDecimal multiply = paymentRate.multiply(BigDecimal.valueOf(count));
//        paymentsInfo.put("paymentAmount", multiply);
//        return paymentsInfo;
//    }
}
