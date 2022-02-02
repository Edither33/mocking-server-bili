package bilibili.api;

import bilibili.entity.JsonResponse;
import bilibili.entity.UserMoments;
import bilibili.service.UserMomentService;
import bilibili.support.UserSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api("用户动态")
@RestController
public class UserMomentsController {
    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserMomentService userMomentService;

    @ApiOperation("发送动态")
    @PostMapping("/user-moments")
    public JsonResponse<String> addMoments(@RequestBody UserMoments moments) {
        Long userId = userSupport.getCurrentUserId();
        moments.setUserId(userId);
        userMomentService.addMoments(moments);
        return JsonResponse.success();
    }

    @ApiOperation("粉丝获取动态")
    @GetMapping("/user-subscribed-moments")
    public JsonResponse<List<UserMoments>> getUserSubscribedMoments() {
        Long userId = userSupport.getCurrentUserId();
        List<UserMoments> res = userMomentService.getUserSubscribedMoments(userId);
        return new JsonResponse<>(res);
    }
}
