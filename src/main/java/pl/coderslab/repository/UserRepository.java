package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;

import java.awt.print.Book;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u JOIN FETCH u.payments where u.id = ?1")
    User findWithPayments(Long id);

    List<User> findByEnabledIsTrue();

    @Query("select u.groups from User u where u.id = ?1")
    List<GroupModel> findGroupsForUserId(Long id);

    @Query("SELECT u FROM User u WHERE ?1 not member of u.groups and u.enabled=true ")
    List<User> findUsersOutOfGroup(GroupModel groupModel);

}
