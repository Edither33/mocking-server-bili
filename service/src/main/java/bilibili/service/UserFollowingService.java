package bilibili.service;

import bilibili.constant.UserConstant;
import bilibili.dao.UserFollowingDao;
import bilibili.entity.FollowingGroup;
import bilibili.entity.User;
import bilibili.entity.UserFollowing;
import bilibili.entity.UserInfo;
import bilibili.exception.ConditionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserFollowingService {
    @Resource
    private UserFollowingDao userFollowingDao;

    @Autowired
    private FollowingGroupService followingGroupService;

    @Autowired
    private UserService userService;

    /**
     * 添加、修改关注
     * @param userFollowing
     */
    @Transactional
    public void addUserFollowing(UserFollowing userFollowing) {
        if (userFollowing.getFollowingId().equals(userFollowing.getUserId().intValue())) {
            log.warn("61000","不可以关注自己");
            throw new ConditionException("61000","不可以关注自己");
        }
        // 处理分组
        Integer groupId = userFollowing.getGroupId();
        if(groupId == null) {
            // 添加到默认分组
            FollowingGroup defaultGroup = followingGroupService.getByType(UserConstant.DEFAULT_GROUP);
            userFollowing.setGroupId(defaultGroup.getId().intValue());
        }else {
            FollowingGroup selectedGroup = followingGroupService.getById(userFollowing.getGroupId().longValue());
            if(selectedGroup == null) {
                log.warn("61001","分组不存在");
                throw new ConditionException("61001","分组不存在");
            }
        }
        // 检查被关注的用户是否存在
        Integer followingId = userFollowing.getFollowingId();
        User followedUser = userService.getUserById(followingId.longValue());
        if(followedUser == null) {
            log.warn("61002","被关注的用户不存在");
            throw new ConditionException("61002", "被关注的用户不存在");
        }
        // 如果原先关注了，就先删除
        userFollowingDao.deleteByUserIdAndFollowedUserId(userFollowing.getUserId(), followingId);
        // 设置 关注/修改 的时间
        userFollowing.setCreateTime(new Date());
        // 插入数据
        userFollowingDao.insert(userFollowing);
    }

    /**
     * 获取某个用户的关注列表
     * @param userId 用户id
     * @return List<FollowingGroup> 用户的关注列表
     */
    public List<FollowingGroup> getUserFollowings(Long userId) {
        List<UserFollowing> followings = userFollowingDao.queryAll(UserFollowing.builder().userId(userId).build());
        Set<Integer> collect = followings.stream().map(UserFollowing::getFollowingId).collect(Collectors.toSet());
        List<UserInfo> userInfoList = new ArrayList<>();
        if(collect.size() > 0) {
            userInfoList = userService.getUserInfoByIds(collect);
        }

        for (UserFollowing following : followings) {
            for (UserInfo userInfo : userInfoList) {
                if(following.getFollowingId().equals(userInfo.getUserId().intValue())) {
                    following.setUserInfo(userInfo);
                }
            }
        }
        // 返回的分组
        List<FollowingGroup> res = new ArrayList<>();

        // 创建全部分组
        FollowingGroup allFG = new FollowingGroup();
        allFG.setName(UserConstant.DEFAULT_GROUP_ALL_NAME);
        allFG.setUserInfoList(userInfoList);
        res.add(allFG);
        // 获取某个用户的分组（包含系统自带的分组）
        List<FollowingGroup> followingGroupList = followingGroupService.getByUserId(userId);
        for (FollowingGroup followingGroup : followingGroupList) {
            List<UserInfo> group = new ArrayList<>();
            for (UserFollowing following : followings) {
                if(Long.valueOf(following.getGroupId()).equals(followingGroup.getId())) {
                    group.add(following.getUserInfo());
                }
            }
            followingGroup.setUserInfoList(group);
            res.add(followingGroup);
        }
        return res;
    }

    /**
     * 获取粉丝列表,并查询互粉状态
     * @param userId 用户id
     * @return List<UserFollowing> 粉丝列表
     */
    public List<UserFollowing> getUserFans(Long userId){
        List<UserFollowing> fanList = userFollowingDao.queryAll(UserFollowing.builder().followingId(userId.intValue()).build());
        Set<Integer> collect = fanList.stream().map(e-> e.getUserId().intValue()).collect(Collectors.toSet());
        // 获取粉丝的用户信息
        List<UserInfo> userInfoList = new ArrayList<>();
        if(collect.size() > 0) {
            userInfoList = userService.getUserInfoByIds(collect);
        }
        for(UserFollowing fan: fanList) {
            for (UserInfo userInfo : userInfoList) {
                if(fan.getUserId().equals(userInfo.getUserId())) {
                    userInfo.setFollowed(false);
                    fan.setUserInfo(userInfo);
                }
            }
            for (UserFollowing userFollowing : fanList) {
                if(userFollowing.getFollowingId().equals(fan.getUserId().intValue())) {
                    fan.getUserInfo().setFollowed(true);
                }
            }
        }
        return fanList;
    }

    /**
     * 添加分组
     * @param followingGroup 分组信息
     * @return 分组id
     */
    public Long addFollowingGroup(FollowingGroup followingGroup) {
        followingGroup.setCreateTime(new Date());
        followingGroup.setType(UserConstant.DEFAULT_GROUP_USER);
        followingGroupService.addFollowingGroup(followingGroup);
        return followingGroup.getId();
    }

    /**
     * 获取当前用户的关注分组
     * @param userId 用户id
     * @return 分组信息
     */
    public List<FollowingGroup> getFollowingGroup(Long userId) {
        return followingGroupService.getFollowingGroupByUserId(userId);
    }

    /**
     * 获取分页用户信息
     * @param data 分页用户
     * @param userId 用户id
     * @return 分页用户信息
     */
    public List<UserInfo> checkFollowedList(List<UserInfo> data, Long userId) {
        // 查询当前用户关注了谁
        List<UserFollowing> followings = userFollowingDao.queryAll(UserFollowing.builder().userId(userId).build());
        for (UserInfo userInfo : data) {
            userInfo.setFollowed(false);
            for (UserFollowing userFollowing : followings) {
                if(userFollowing.getFollowingId().equals(userInfo.getUserId().intValue())) {
                    userInfo.setFollowed(true);
                }
            }
        }
        return data;
    }
}
