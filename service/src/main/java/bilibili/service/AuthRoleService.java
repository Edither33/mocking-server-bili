package bilibili.service;

import bilibili.dao.AuthRoleDao;
import bilibili.entity.auth.AuthRole;
import bilibili.entity.auth.AuthRoleElementOperation;
import bilibili.entity.auth.AuthRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class AuthRoleService {
    @Resource
    private AuthRoleDao authRoleDao;

    @Autowired
    private AuthRoleElementOperationService authRoleElementOperationService;

    @Autowired
    private AuthRoleMenuService authRoleMenuService;

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdList) {
        return authRoleElementOperationService.getRoleElementOperationsByRoleIds(roleIdList);
    }

    public List<AuthRoleMenu> getAuthRoleMenus(Set<Long> roleIdList) {
        return authRoleMenuService.getAuthRoleMenus(roleIdList);
    }

    public AuthRole getRoleByCodeId(String roleCode) {
        return authRoleDao.getRoleByCodeId(roleCode);
    }
}
