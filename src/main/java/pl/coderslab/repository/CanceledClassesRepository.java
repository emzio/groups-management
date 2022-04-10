package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.CanceledClasses;

public interface CanceledClassesRepository extends JpaRepository<CanceledClasses, Long> {

}
