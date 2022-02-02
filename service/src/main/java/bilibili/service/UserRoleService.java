package bilibili.service;

import bilibili.dao.UserRoleDao;
import bilibili.entity.auth.UserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserRoleService {
    @Resource
    private UserRoleDao userRoleDao;

    public List<UserRole> getUserRoleByUserId(Long userId) {
        return userRoleDao.getUserRoleByUserId(userId);
    }


    public void addDefaultRoleToUser(UserRole userRole) {
        userRole.setCreateTime(new Date());
        userRoleDao.insert(userRole);
    }
}
