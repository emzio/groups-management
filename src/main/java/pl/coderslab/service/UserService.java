package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.User;


public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);
}
