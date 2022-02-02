package bilibili.api;

import bilibili.constant.UserConstant;
import bilibili.entity.JsonResponse;
import bilibili.entity.PageResult;
import bilibili.entity.User;
import bilibili.entity.UserInfo;
import bilibili.service.UserFollowingService;
import bilibili.service.UserService;
import bilibili.support.UserSupport;
import bilibili.util.RSAUtil;
import bilibili.vo.UserRequest;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api("用户操作接口")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserFollowingService userFollowingService;

    @ApiOperation("获取RSA密钥")
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKeyStr);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        Long userId = userSupport.getCurrentUserId();
        User user = userService.getUserInfo(userId);
        user.setPassword(null);
        user.setSalt(null);
        return new JsonResponse<>(user);
    }

    @ApiOperation("添加用户")
    @PostMapping("/users")
    public JsonResponse<String> addUser(@RequestBody UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        userService.addUser(user);
        return JsonResponse.success();
    }

    @ApiOperation("用户登录")
    @PostMapping("/user-tokens")
    public JsonResponse<String> login(@RequestBody UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        String token = userService.login(user);
        return new JsonResponse<>(token);
    }

    @ApiOperation("双令牌用户登录")
    @PostMapping("/user-dts")
    public JsonResponse<Map<String, Object>> loginDts(@RequestBody UserRequest userRequest) {
        User user = new User();
        BeanUtils.copyProperties(userRequest, user);
        Map<String, Object> map = userService.loginDts(user);
        return new JsonResponse<>(map);
    }

    @ApiOperation("双令牌用户退出登录")
    @DeleteMapping("/refresh-token")
    public JsonResponse<String> loginOut(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        Long userId = userSupport.getCurrentUserId();
        userService.loginOut(refreshToken, userId);
        return JsonResponse.success();
    }

    @ApiOperation("双令牌, 刷新token")
    @GetMapping("/access-token")
    public JsonResponse<String> getNewAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("refreshToken");
        String accessToken = userService.getNewAccessToken(refreshToken);
        return new JsonResponse<>(accessToken);
    }

    @ApiOperation("用户信息更新")
    @PutMapping("/user-infos")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        Long userId = userSupport.getCurrentUserId();
        userInfo.setUserId(userId);
        userService.updateUserInfo(userInfo);
        return JsonResponse.success();
    }

    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user){
        Long userId = userSupport.getCurrentUserId();
        user.setId(userId);
        userService.updateUser(user);
        return JsonResponse.success();
    }


    @ApiOperation("获取分页用户信息")
    @GetMapping("/user-infos")
    public JsonResponse<PageResult<UserInfo>> pageListUserInfo(@RequestParam(name = "no") Integer page,
                                                               @RequestParam(name = "size") Integer pageSize,
                                                               String nick) {
        Long userId = userSupport.getCurrentUserId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce(UserConstant.PAGE_NO, page);
        jsonObject.putOnce(UserConstant.PAGE_SIZE, pageSize);
        jsonObject.putOnce(UserConstant.PAGE_NICK, nick);
        jsonObject.putOnce(UserConstant.PAGE_USERID, userId);
        PageResult<UserInfo> res = userService.pageUserInfoList(jsonObject);
        if(res.getTotal() > 0) {
            List<UserInfo> checkFollowedList = userFollowingService.checkFollowedList(res.getData(), userId);
            res.setData(checkFollowedList);
        }
        return new JsonResponse<>(res);
    }
}
