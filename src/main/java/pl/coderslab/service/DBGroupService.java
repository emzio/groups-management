package pl.coderslab.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.repository.GroupModelRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DBGroupService implements GroupService{
    private final GroupModelRepository groupModelRepository;
    private final UserService userService;

    public DBGroupService(GroupModelRepository groupModelRepository, UserService userService) {
        this.groupModelRepository = groupModelRepository;
        this.userService = userService;
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

    @Override
    public GroupModel findJoiningUsers(Long id) {
        GroupModel groupModel = groupModelRepository.findById(id).get();
        Hibernate.initialize(groupModel.getUsers());
        return groupModel;
    }

    @Override
    public List<GroupModel> findAllJoiningUsers() {
        List<GroupModel> groupModels = groupModelRepository.findAll();
        groupModels.stream()
                .forEach(groupModel -> Hibernate.initialize(groupModel.getUsers()));
        return groupModels;
    }

    @Override
    public List<GroupModel> findGroupsWithFreePlaces() {
        List<GroupModel> groupModelsWithFreePlaces = findAllJoiningUsers().stream()
                .filter(groupModel -> groupModel.getSize() >= groupModel.getUsers().size())
                .collect(Collectors.toList());
        return groupModelsWithFreePlaces;
    }

// <<<<<<< feature/user_update
    @Override
    @Transactional
    public void editGroupModel(GroupModel groupModel) {
        GroupModel groupToUpdate = findJoiningUsers(groupModel.getId());
        groupToUpdate.getUsers().forEach(user -> {
            user.getGroups().removeIf(gm -> gm.getId().equals(groupModel.getId()));
            userService.save(user);
        });
        // groupModel.getUserListId() ???
        groupToUpdate.getUsers().removeIf(u -> !groupModel.getUsers().stream().map(User::getId).collect(Collectors.toList()).contains(u.getId()));
        save(groupToUpdate);
        groupToUpdate.setUsers(groupModel.getUsers());
        groupToUpdate.getUsers()
                .forEach(user -> {
                    if (!user.getGroups().stream().map(GroupModel::getId).collect(Collectors.toList()).contains(groupModel.getId())) {
                        user.getGroups().add(groupModel);
                        userService.save(user);
                    }
                });
        save(groupModel);
//        save(groupToUpdate);
    }
// =======

    @Override
    public boolean verificationOfOversize(Long groupId, List<User> users){
        return findById(groupId).get().getSize() >= users.size();
// >>>>>>> main
    }

}
