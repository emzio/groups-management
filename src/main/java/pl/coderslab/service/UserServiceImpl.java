package pl.coderslab.service;

import org.hibernate.Hibernate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.Payment;
import pl.coderslab.entity.Role;
import pl.coderslab.entity.User;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final PaymentService paymentService;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           BCryptPasswordEncoder passwordEncoder, PaymentService paymentService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.paymentService = paymentService;
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUserNameWithGroupsAndPayments(String username) {
        User byUsername = userRepository.findByUsername(username);
        Hibernate.initialize(byUsername.getPayments());
        Hibernate.initialize(byUsername.getGroups());
        return byUsername;
    }

    //find all
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public List<User> findAllActive(){
        return userRepository.findByEnabledIsTrue();
    }

    @Override
    public List<User> findAllActiveWithGroupsAndPayments(){
        List<User> users = userRepository.findByEnabledIsTrue();
        users.stream()
                .forEach(user -> {
                    Hibernate.initialize(user.getGroups());
                    Hibernate.initialize(user.getPayments());
                });
        return users;
    }

    // findById:

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findByIdWithGroups(Long id) {
        User user = userRepository.findById(id).get();
        Hibernate.initialize(user.getGroups());
        return user;
    }
    @Override
    public User findByIdWithGroupsAndPayments(Long id){
        User user = userRepository.findById(id).get();
        Hibernate.initialize(user.getPayments());
        Hibernate.initialize(user.getGroups());
        return user;
    }

    @Override
    public User findWithPayments(Long id){
        return userRepository.findWithPayments(id);
    }

//    add:
    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(new HashSet<>(Arrays.asList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        Role userRole = roleRepository.findByName("ROLE_USER");
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        user.setRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(user);
    }



    // delete

    @Override
    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setEnabled(false);
            user.getGroups().removeAll(user.getGroups());

            userRepository.save(user);
        }
    }

    //update:
    @Override
    public void update(User user) {
        user.setEnabled(true);
        String rawPassword = user.getPassword();
        String formerPassword = userRepository.findById(user.getId()).get().getPassword();

        User userPreUpdated = userRepository.findById(user.getId()).get();
        user.setRoles(userPreUpdated.getRoles());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if(rawPassword.equals(formerPassword)){
            user.setPassword(formerPassword);
        }
        userRepository.save(user);
    }

    //  Payment
    @Override
    public void addPaymentToUser(User user, Payment payment) {
        user.getPayments().add(payment);
        paymentService.save(payment);
        userRepository.save(user);
    }

    @Override
    public void deletePaymentForUser(Long userId, Long paymentId){
        User user = findByIdWithGroupsAndPayments(userId);
        Payment payment = paymentService.findById(paymentId).get();
        user.getPayments().remove(payment);
        userRepository.save(user);
        paymentService.delete(payment);
    }


    @Override
    @Transactional
    public void save(User user){
        userRepository.save(user);
    }

// test

    public List<GroupModel> findGroupsForUserId(Long id){
        return userRepository.findGroupsForUserId(id);
    }

    @Override
    public List<User> findUsersOutOfGroup(GroupModel groupModel){


        return userRepository.findUsersOutOfGroup(groupModel);
    }

}