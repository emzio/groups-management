package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;


public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Query(value = "SELECT * FROM payment where user_id = ?1", nativeQuery = true)
    User findWithPayments(Long id);
}
