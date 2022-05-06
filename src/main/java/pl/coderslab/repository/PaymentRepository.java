package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.Payment;


public interface PaymentRepository extends JpaRepository<Payment,Long> {
//    @Query(value = "SELECT * FROM payment where user_id = ?1", nativeQuery = true)
//    User findWithPayments(Long id);
}
