package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {

}
