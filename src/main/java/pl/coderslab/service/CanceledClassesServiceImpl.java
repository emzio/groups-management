package pl.coderslab.service;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.CanceledClasses;
import pl.coderslab.repository.CanceledClassesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CanceledClassesServiceImpl implements CanceledClassesService{
    private final CanceledClassesRepository canceledClassesRepository;

    public CanceledClassesServiceImpl(CanceledClassesRepository canceledClassesRepository) {
        this.canceledClassesRepository = canceledClassesRepository;
    }


    @Override
    public List<CanceledClasses> findAll() {
        return canceledClassesRepository.findAll();
    }

    @Override
    public Optional<CanceledClasses> findById(Long id) {
        return canceledClassesRepository.findById(id);
    }

    @Override
    public void save(CanceledClasses canceledClasses) {
        canceledClassesRepository.save(canceledClasses);
    }

    @Override
    public void deleteById(Long id) {
        canceledClassesRepository.deleteById(id);
    }

    @Override
    public void update(CanceledClasses canceledClasses) {
        canceledClassesRepository.save(canceledClasses);
    }
}
