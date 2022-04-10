package pl.coderslab.service;

import pl.coderslab.entity.CanceledClasses;

import java.util.List;
import java.util.Optional;

public interface CanceledClassesService {

    public List<CanceledClasses> findAll();

    public Optional<CanceledClasses> findById(Long id);

    public void save(CanceledClasses canceledClasses);

    public void deleteById(Long id);

    public void update(CanceledClasses canceledClasses);
}
