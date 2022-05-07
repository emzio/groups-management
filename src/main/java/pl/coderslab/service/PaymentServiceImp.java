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
    public Map<String, BigDecimal> paymentAndClasses (List<CalendarCell> cells) {
        Map<String, BigDecimal> paymentsInfo = new HashMap<>();
        BigDecimal paymentAmount = cells.stream()
                .map(CalendarCell::getAddToFee)
                .filter(addToFee -> addToFee !=null)
                .reduce((bigDecimal, bigDecimal2) -> bigDecimal.add(bigDecimal2)).orElse(BigDecimal.valueOf(0));

        paymentsInfo.put("paymentAmount", paymentAmount);
        return paymentsInfo;
    }
}
