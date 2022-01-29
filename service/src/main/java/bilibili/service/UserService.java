package bilibili.service;

import bilibili.constant.UserConstant;
import bilibili.dao.UserDao;
import bilibili.dao.UserInfoDao;
import bilibili.entity.User;
import bilibili.entity.UserInfo;
import bilibili.exception.ConditionException;
import bilibili.util.MD5Util;
import bilibili.util.RSAUtil;
import bilibili.util.TokenUtil;
import cn.hutool.core.util.StrUtil;
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
        String email = user.getEmail();
        if (StrUtil.isBlankIfStr(phone)) {
            throw new ConditionException("手机号不能为空!");
        }

        // 解密密码
        String password = user.getPassword();
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String decrypt;
        try {
            decrypt = RSAUtil.decrypt(password);
        } catch (Exception e) {
            throw new ConditionException("密码解密失败");
        }

        User userByPhone = getUserByPhone(phone);
        if (userByPhone != null) {
            throw new ConditionException("手机号已被注册!");
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
    public User getUserByEmail(String email) {
        return userDao.queryUserByEmail(email);
    }

    public String login(User user) {
        String phone = user.getPhone();
        String email = user.getEmail();

        boolean p = StrUtil.isBlankIfStr(phone);
        boolean e = StrUtil.isBlankIfStr(email);
        if(p && e) {
            throw new ConditionException("44300","账号不能为空");
        }

        String password = user.getPassword();
        String decrypt;
        try {
            decrypt = RSAUtil.decrypt(password);
        } catch (Exception e1) {
            throw new ConditionException("44400", "密码解密失败");
        }

        User userByQuery = null ;
        if(p) {
            userByQuery = getUserByEmail(email);
        }else if(e){
            userByQuery = getUserByPhone(phone);
        }
        if (null == userByQuery) {
            throw new ConditionException("33300", "用户不存在");
        }

        String salt = userByQuery.getSalt();
        boolean verify = MD5Util.verify(decrypt, userByQuery.getPassword(), salt, "UTF-8");
        if (!verify) {
            throw new ConditionException("44401", "密码验证失败");
        }
        String token = null;
        try {
            token = TokenUtil.generateToken(userByQuery.getId());
        } catch (Exception e1) {
            throw new ConditionException("55500", "生成令牌失败");
        }
        return token;
    }

    public User getUserInfo(Long userId) {
        User user = userDao.queryById(userId);
        if (null == user) {
            throw new ConditionException("非法的用户id");
        }
        UserInfo userInfo = userInfoDao.queryByUserId(userId);
        if (null == userInfo) {
            throw new ConditionException("非法的用户id");
        }
        user.setUserInfo(userInfo);
        return user;
    }

    public void updateUserInfo(UserInfo userInfo) {
        userInfoDao.update(userInfo);
    }

    public void updateUser(User user) {
        String phone = user.getPhone();
        if (StrUtil.isBlankIfStr(phone)) {
            throw new ConditionException("手机号不能为空!");
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
        user.setPassword(signPassword);
        user.setSalt(salt);
        userDao.update(user);
    }
}
