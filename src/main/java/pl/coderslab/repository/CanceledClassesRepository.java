package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.entity.CanceledClasses;
import pl.coderslab.entity.User;

import java.util.List;

public interface CanceledClassesRepository extends JpaRepository<CanceledClasses, Long> {
    @Query("SELECT c FROM CanceledClasses c order by c.localDate")
    List<CanceledClasses> findAllOrderedByLocalDate();
}
