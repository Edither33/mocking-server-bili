package bilibili.service;

import bilibili.constant.UserConstant;
import bilibili.dao.UserDao;
import bilibili.dao.UserInfoDao;
import bilibili.domain.entity.User;
import bilibili.domain.entity.UserInfo;
import bilibili.exception.ConditionException;
import bilibili.util.MD5Util;
import bilibili.util.RSAUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.tokenizer.TokenizerUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private UserInfoDao userInfoDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        if(StrUtil.isBlankIfStr(phone)) {
            throw new ConditionException("手机号不能为空!");
        }
        User userByPhone = getUserByPhone(phone);
        if (userByPhone != null) {
            throw new ConditionException("手机号已被注册!");
        }
        String password = user.getPassword();
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String decrypt;
        try {
            decrypt = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }

        String signPassword = MD5Util.sign(decrypt, salt, "UTF-8");
        user.setCreatetime(now);
        user.setPassword(signPassword);
        user.setSalt(salt);

        userDao.insertUser(user);
        // 添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(user.getId());
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreatetime(now);
        userInfoDao.insert(userInfo);


    }

    public User getUserByPhone(String phone) {
        return userDao.queryUserByPhone(phone);
    }
}
