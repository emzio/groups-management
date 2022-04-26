package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.Payment;
import pl.coderslab.repository.PaymentRepository;

@Service
public class PaymentServiceImp implements PaymentService{
    private final PaymentRepository paymentRepository;

    public PaymentServiceImp(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void save(Payment payment) {
        paymentRepository.save(payment);
    }
}
