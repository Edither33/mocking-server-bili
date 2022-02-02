package bilibili.aspect;

import bilibili.annotation.ApiRoleLimited;
import bilibili.entity.auth.UserRole;
import bilibili.exception.ConditionException;
import bilibili.service.UserRoleService;
import bilibili.support.UserSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Order(1)
@Component
@Aspect
public class ApiRoleLimitedAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(bilibili.annotation.ApiRoleLimited)")
    public void check(){}

    @Before("check() && @annotation(apiRoleLimited)")
    public void doBefore(JoinPoint joinPoint, ApiRoleLimited apiRoleLimited){
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        String[] limitedRoleCodeList = apiRoleLimited.limitedRoleCodeList();
        Set<String> limitedRoleCodeSet = Arrays.stream(limitedRoleCodeList).collect(Collectors.toSet());
        Set<String> userRoleSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        userRoleSet.retainAll(limitedRoleCodeSet);
        if(userRoleSet.size() > 0) {
            throw new ConditionException("权限不足");
        }
    }
}
