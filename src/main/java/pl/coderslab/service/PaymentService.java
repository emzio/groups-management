package pl.coderslab.service;


import pl.coderslab.entity.Payment;

import java.util.Optional;

public interface PaymentService {

   Optional<Payment> findById(Long paymentId);

   void save(Payment payment);

   void delete(Payment payment);
}
