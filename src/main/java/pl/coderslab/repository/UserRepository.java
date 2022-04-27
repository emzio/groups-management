package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.payments where u.id = ?1")
//    @Query("SELECT u FROM User u JOIN FETCH u.payments, u.groups where u.id = ?1")
    User findWithPayments(Long id);
}
