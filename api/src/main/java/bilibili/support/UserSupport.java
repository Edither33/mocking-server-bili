package bilibili.support;

import bilibili.exception.ConditionException;
import bilibili.util.TokenUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {

    public Long getCurrentUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("Authorization");
        if(StrUtil.isBlankIfStr(token)) {
            token = requestAttributes.getRequest().getHeader("token");
        }

        JWT jwtInfo = TokenUtil.getJWTInfo(token);
        if(TokenUtil.isExpire(token)) {
            throw new ConditionException("555","登录过期");
        }

        return Long.valueOf((String) jwtInfo.getPayload(TokenUtil.USERID));
    }
}
