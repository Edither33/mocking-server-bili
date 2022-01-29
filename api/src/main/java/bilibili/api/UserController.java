package bilibili.api;

import bilibili.entity.JsonResponse;
import bilibili.entity.User;
import bilibili.entity.UserInfo;
import bilibili.service.UserService;
import bilibili.support.UserSupport;
import bilibili.util.RSAUtil;
import bilibili.vo.UserRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api("用户操作")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSupport userSupport;

    @ApiOperation("获取RSA密钥")
    @GetMapping("/rsa-pks")
    public JsonResponse<String> getRsaPublicKey() {
        String publicKeyStr = RSAUtil.getPublicKeyStr();
        return new JsonResponse<>(publicKeyStr);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("/users")
    public JsonResponse<User> getUserInfo() {
        Long userId = userSupport.getUserId();
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

    @ApiOperation("用户信息更新")
    @PutMapping("/user-info")
    public JsonResponse<String> updateUserInfo(@RequestBody UserInfo userInfo) {
        Long userId = userSupport.getUserId();
        userInfo.setUserid(userId);
        userService.updateUserInfo(userInfo);
        return JsonResponse.success();
    }

    @PutMapping("/users")
    public JsonResponse<String> updateUsers(@RequestBody User user){
        Long userId = userSupport.getUserId();
        user.setId(userId);
        userService.updateUser(user);
        return JsonResponse.success();
    }

}
