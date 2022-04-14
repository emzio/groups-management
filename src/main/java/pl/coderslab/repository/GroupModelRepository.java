package pl.coderslab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.entity.GroupModel;

public interface GroupModelRepository extends JpaRepository<GroupModel, Long> {
}
