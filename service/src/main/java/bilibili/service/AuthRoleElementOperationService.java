package bilibili.service;

import bilibili.dao.AuthRoleElementOperationDao;
import bilibili.entity.auth.AuthRoleElementOperation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class AuthRoleElementOperationService {

    @Resource
    private AuthRoleElementOperationDao authRoleElementOperationDao;

    public List<AuthRoleElementOperation> getRoleElementOperationsByRoleIds(Set<Long> roleIdList) {
        return authRoleElementOperationDao.getRoleElementOperationsByRoleIds(roleIdList);
    }
}
