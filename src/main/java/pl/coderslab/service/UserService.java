package pl.coderslab.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);

    void saveAdmin(User user);

    public Optional<User> findById(Long id);

    public void deleteById(Long id);

    public void update(User user);

    public User findByIdWithGroups(Long id);

    User findByIdWithGroupsAndPayments(Long id);

    public List<User> findAll();

    public void save(User user);

    User findWithPayments(Long id);
    void addPaymentToUser(User user, Payment payment);

    void deletePaymentForUser(Long userId, Long paymentId);

    List<User> findAllActive();
}
