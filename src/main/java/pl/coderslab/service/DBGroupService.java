package pl.coderslab.service;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.GroupModel;
import pl.coderslab.entity.User;
import pl.coderslab.repository.GroupModelRepository;

import java.util.Collections;
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
        List<GroupModel> groupModelList = groupModelRepository.findAll();
        Collections.sort(groupModelList);
        return groupModelList;
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
    public void deleteGroupModel(GroupModel groupModel){
        GroupModel joiningUsers = findJoiningUsers(groupModel.getId());
        joiningUsers.getUsers().stream()
                .map(user -> userService.findByIdWithGroupsAndPayments(user.getId()))
                .forEach(user -> {
                            user.getGroups().removeIf(gm -> gm.getId().equals(groupModel.getId()));
                            userService.save(user);
                        }
                );
        deleteById(groupModel.getId());
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
                .filter(groupModel -> groupModel.getSize() > groupModel.getUsers().size()).sorted().collect(Collectors.toList());
        return groupModelsWithFreePlaces;
    }

    @Override
    @Transactional
    public void editGroupModel(GroupModel groupModel) {
        GroupModel groupToUpdate = findJoiningUsers(groupModel.getId());
        groupToUpdate.getUsers().stream()
                .map(user -> userService.findByIdWithGroupsAndPayments(user.getId()))
                .forEach(user -> {
                    user.getGroups().removeIf(gm -> gm.getId().equals(groupModel.getId()));
                    userService.save(user);
                });

        // groupModel.getUserListId() ???
//        groupToUpdate.getUsers().removeIf(u -> !groupModel.getUsers().stream().map(User::getId).collect(Collectors.toList()).contains(u.getId()));

        groupToUpdate.getUsers().removeIf(u -> !groupModel.getUserListId().contains(u.getId()));

        save(groupToUpdate);
        groupToUpdate.setUsers(groupModel.getUsers());
        groupToUpdate.getUsers().stream()
                .map(user -> userService.findByIdWithGroupsAndPayments(user.getId()))
                .forEach(user -> {
                    if (!user.getGroups().stream().map(GroupModel::getId).collect(Collectors.toList()).contains(groupModel.getId())) {
                        user.getGroups().add(groupModel);
                        userService.save(user);
                    }
                });
        save(groupModel);
    }

    @Override
    public boolean verificationOfOversize(GroupModel groupModel, List<User> users){
        return groupModel.getSize() >= users.size();
    }

    @Override
    public void addUserToGroup(GroupModel groupModel){
        groupModel.getUsers().stream()
                .map(user -> userService.findByIdWithGroupsAndPayments(user.getId()))
                .forEach(user -> {
                    user.getGroups().add(groupModel);
                    userService.save(user);
                });
//        groupService.save(groupModel);
    }


}
