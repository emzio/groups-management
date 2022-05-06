package pl.coderslab.service;

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
    public List<User> findAllActiveWithGroupsAndPayments();
    public User findByUserNameWithGroupsAndPayments(String username);

    public List<GroupModel> findGroupsForUserId(Long id);

    List<User> findUsersOutOfGroup(GroupModel groupModel);
}
