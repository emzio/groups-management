package pl.coderslab.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.repository.GroupModelRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DBGroupService implements GroupService{
    private final GroupModelRepository groupModelRepository;

    public DBGroupService(GroupModelRepository groupModelRepository) {
        this.groupModelRepository = groupModelRepository;
    }

    @Override
    public List<GroupModel> findAllWithCanceledClasses(){
        List<GroupModel> all = groupModelRepository.findAll();
        all.stream()
                .forEach(groupModel -> Hibernate.initialize(groupModel.getCanceledClasses()));
        return all;
    }

    @Override
    public Optional<GroupModel> findByIdWithCanceledClasses(Long id){
        Optional<GroupModel> groupModelOptional = groupModelRepository.findById(id);
        Hibernate.initialize(groupModelOptional.get().getCanceledClasses());
        return groupModelOptional;
    }

    @Override
    public List<GroupModel> findAll() {
        return groupModelRepository.findAll();
    }

    @Override
    public Optional<GroupModel> findById(Long id) {
        return groupModelRepository.findById(id);
    }

    @Override
    public void save(GroupModel groupModel) {
        groupModelRepository.save(groupModel);
    }

    @Override
    public void deleteById(Long id) {
        groupModelRepository.deleteById(id);
    }

    @Override
    public void update(GroupModel groupModel) {
        groupModelRepository.save(groupModel);
    }
}
