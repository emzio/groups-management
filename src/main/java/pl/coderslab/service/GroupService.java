package pl.coderslab.service;

import pl.coderslab.entity.GroupModel;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    public List<GroupModel> findAll();

    public Optional<GroupModel> findById(Long id);

    public void save(GroupModel groupModel);

    public void deleteById(Long id);

    public void update(GroupModel groupModel);

    public GroupModel findJoiningUsers(Long id);

    List<GroupModel> findAllJoiningUsers();

    List<GroupModel> findGroupsWithFreePlaces();

    void editGroupModel(GroupModel groupModel);
}
