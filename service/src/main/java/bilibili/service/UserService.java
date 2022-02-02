package bilibili.service;

import bilibili.constant.UserConstant;
import bilibili.dao.UserDao;
import bilibili.dao.UserInfoDao;
import bilibili.entity.PageResult;
import bilibili.entity.User;
import bilibili.entity.UserInfo;
import bilibili.exception.ConditionException;
import bilibili.util.MD5Util;
import bilibili.util.RSAUtil;
import bilibili.util.TokenUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    @Resource
    private UserDao userDao;

    @Resource
    private UserInfoDao userInfoDao;

    public void addUser(User user) {
        String phone = user.getPhone();
        String email = user.getEmail();
        if (StrUtil.isBlankIfStr(phone)) {
            log.warn("手机号不能为空!");
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
            log.error("密码解密失败",e);
            throw new ConditionException("密码解密失败");
        }

        User userByPhone = getUserByPhone(phone);
        if (userByPhone != null) {
            log.warn("手机号已被注册!");
            throw new ConditionException("手机号已被注册!");
        }



        String signPassword = MD5Util.sign(decrypt, salt, "UTF-8");
        user.setCreateTime(now);
        user.setPassword(signPassword);
        user.setSalt(salt);

        userDao.insertUser(user);
        // 添加用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setGender(UserConstant.GENDER_MALE);
        userInfo.setCreateTime(now);
        userInfoDao.insert(userInfo);


    }

    public User getUserByPhone(String phone) {
        return userDao.queryUserByPhone(phone);
    }
    public User getUserByEmail(String email) {
        return userDao.queryUserByEmail(email);
    }
    public User getUserById(Long id) {
        return userDao.queryById(id);
    }

    public String login(User user) {
        String phone = user.getPhone();
        String email = user.getEmail();

        boolean p = StrUtil.isBlankIfStr(phone);
        boolean e = StrUtil.isBlankIfStr(email);
        if(p && e) {
            log.warn("账号不能为空");
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
            log.warn("33300", "用户不存在");
            throw new ConditionException("33300", "用户不存在");
        }

        String salt = userByQuery.getSalt();
        boolean verify = MD5Util.verify(decrypt, userByQuery.getPassword(), salt, "UTF-8");
        if (!verify) {
            log.warn("44401", "密码验证失败",user);
            throw new ConditionException("44401", "密码验证失败");
        }
        String token = null;
        try {
            token = TokenUtil.generateToken(userByQuery.getId());
        } catch (Exception e1) {
            log.error("55500", "生成令牌失败",e1);
            throw new ConditionException("55500", "生成令牌失败");
        }
        return token;
    }

    public User getUserInfo(Long userId) {
        User user = userDao.queryById(userId);
        if (null == user) {
            log.error("非法的用户id", userId);
            throw new ConditionException("非法的用户id");
        }
        UserInfo userInfo = userInfoDao.queryByUserId(userId);
        if (null == userInfo) {
            log.error("非法的用户id", userId);
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
            log.warn("手机号不能为空!");
            throw new ConditionException("手机号不能为空!");
        }
        String password = user.getPassword();
        Date now = new Date();
        String salt = String.valueOf(now.getTime());
        String decrypt;
        try {
            decrypt = RSAUtil.decrypt(password);
        } catch (Exception e) {
            log.error("密码解密失败", e);
            throw new ConditionException("密码解密失败");
        }

        String signPassword = MD5Util.sign(decrypt, salt, "UTF-8");
        user.setPassword(signPassword);
        user.setSalt(salt);
        userDao.update(user);
    }

    public List<UserInfo> getUserInfoByIds(Set<Integer> userList) {
        return userInfoDao.getUserInfoByIds(userList);
    }

    public PageResult<UserInfo> pageUserInfoList(JSONObject jsonObject) {
        Integer no = jsonObject.getInt(UserConstant.PAGE_NO);
        Integer size = jsonObject.getInt(UserConstant.PAGE_SIZE);
        Integer page = no-1 > 0 ? no - 1: 0;
        jsonObject.putOnce(UserConstant.PAGE_START, page*size);

        Integer total = userInfoDao.pageCountTotal(jsonObject);
        List<UserInfo> userInfoList = userInfoDao.pageUserInfoList(jsonObject);
        return new PageResult<>(total,page+1,size, userInfoList);
    }
}
