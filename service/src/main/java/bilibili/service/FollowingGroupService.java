package bilibili.service;

import bilibili.dao.FollowingGroupDao;
import bilibili.entity.FollowingGroup;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FollowingGroupService {
    @Resource
    private FollowingGroupDao followingGroupDao;

    public FollowingGroup getByType(String type) {
        return followingGroupDao.getByType(type);
    }

    public FollowingGroup getById(Long id) {
        return followingGroupDao.queryById(id);
    }

    public List<FollowingGroup> getByUserId(Long userId) {
        return followingGroupDao.getByUserId(userId);
    }

    public void addFollowingGroup(FollowingGroup followingGroup) {
        followingGroupDao.insert(followingGroup);
    }

    public List<FollowingGroup> getFollowingGroupByUserId(Long userId) {
        FollowingGroup followingGroup = new FollowingGroup();
        followingGroup.setUserId(userId);
        return followingGroupDao.queryAll(followingGroup);
    }
}
