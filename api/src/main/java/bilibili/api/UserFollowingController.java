package bilibili.api;

import bilibili.entity.FollowingGroup;
import bilibili.entity.JsonResponse;
import bilibili.entity.UserFollowing;
import bilibili.service.UserFollowingService;
import bilibili.support.UserSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("关注操作")
@RestController
public class UserFollowingController {
    @Autowired
    private UserFollowingService userFollowingService;

    @Autowired
    private UserSupport userSupport;

    @ApiOperation("关注")
    @PostMapping("/user-followings")
    public JsonResponse<String> addUserFollowing(@RequestBody UserFollowing userFollowing) {
        Long userId = userSupport.getCurrentUserId();
        userFollowing.setUserId(userId);
        userFollowingService.addUserFollowing(userFollowing);
        return JsonResponse.success();
    }

    @ApiOperation("查询关注列表")
    @GetMapping("/user-followings")
    public JsonResponse<List<FollowingGroup>> getUserFollowing() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> res = userFollowingService.getUserFollowings(userId);
        return new JsonResponse<>(res);
    }

    @ApiOperation("查询粉丝")
    @GetMapping("/user-fans")
    public JsonResponse<List<UserFollowing>> getUserFans() {
        Long userId = userSupport.getCurrentUserId();
        List<UserFollowing> res = userFollowingService.getUserFans(userId);
        return new JsonResponse<>(res);
    }

    @ApiOperation("添加分组")
    @PostMapping("/user-following-group")
    public JsonResponse<Long> addFollowingGroup(@RequestBody FollowingGroup followingGroup) {
        Long userId = userSupport.getCurrentUserId();
        followingGroup.setUserId(userId);
        Long res = userFollowingService.addFollowingGroup(followingGroup);
        return new JsonResponse<>(res);
    }

    @ApiOperation("获取分组")
    @GetMapping("/user-following-group")
    public JsonResponse<List<FollowingGroup>> getFollowingGroup() {
        Long userId = userSupport.getCurrentUserId();
        List<FollowingGroup> res = userFollowingService.getFollowingGroup(userId);
        return new JsonResponse<>(res);
    }

}
