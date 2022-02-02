package bilibili.service;

import bilibili.constant.UserRoleConstant;
import bilibili.entity.UserAuthorities;
import bilibili.entity.auth.AuthRole;
import bilibili.entity.auth.AuthRoleElementOperation;
import bilibili.entity.auth.AuthRoleMenu;
import bilibili.entity.auth.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserAuthService {

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AuthRoleService authRoleService;

    /**
     * 获取当前用户的权限
     * @param userId 用户id
     * @return 权限列表
     */
    public UserAuthorities getUserAuthorities(Long userId) {
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<Long> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
        List<AuthRoleElementOperation> authRoleElementOperationList = authRoleService.getRoleElementOperationsByRoleIds(roleIdList);
        List<AuthRoleMenu> authRoleMenuList = authRoleService.getAuthRoleMenus(roleIdList);
        return new UserAuthorities(authRoleElementOperationList, authRoleMenuList);
    }

    /**
     * 添加默认权限
     * @param userId 用户id
     */
    public void addDefaultRoleToUser(Long userId) {
        AuthRole authRole = authRoleService.getRoleByCodeId(UserRoleConstant.ROLE_LV0);
        UserRole userRole = new UserRole();
        userRole.setRoleId(authRole.getId());
        userRole.setUserId(userId);
        userRoleService.addDefaultRoleToUser(userRole);
    }
}
