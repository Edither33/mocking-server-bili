package bilibili.service;

import bilibili.dao.AuthRoleMenuDao;
import bilibili.entity.auth.AuthRoleMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class AuthRoleMenuService {
    @Resource
    private AuthRoleMenuDao authRoleMenuDao;

    public List<AuthRoleMenu> getAuthRoleMenus(Set<Long> roleIdList) {
        return authRoleMenuDao.getAuthRoleMenus(roleIdList);
    }
}
