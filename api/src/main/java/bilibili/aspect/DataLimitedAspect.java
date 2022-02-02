package bilibili.aspect;

import bilibili.annotation.ApiRoleLimited;
import bilibili.annotation.DataLimited;
import bilibili.constant.UserRoleConstant;
import bilibili.entity.UserMoments;
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
public class DataLimitedAspect {

    @Autowired
    private UserSupport userSupport;

    @Autowired
    private UserRoleService userRoleService;

    @Pointcut("@annotation(bilibili.annotation.DataLimited)")
    public void check(){}

    @Before("check()")
    public void doBefore(JoinPoint joinPoint){
        Long userId = userSupport.getCurrentUserId();
        List<UserRole> userRoleList = userRoleService.getUserRoleByUserId(userId);
        Set<String> userRoleSet = userRoleList.stream().map(UserRole::getRoleCode).collect(Collectors.toSet());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if(arg instanceof UserMoments) {
                UserMoments userMoments = (UserMoments) arg;
                if(userMoments.getType().equals("0") && userRoleSet.contains(UserRoleConstant.ROLE_LV0)) {
                    throw new ConditionException("权限不足");
                }
            }
        }
    }
}
