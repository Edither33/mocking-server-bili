package bilibili.support;

import bilibili.util.TokenUtil;
import cn.hutool.jwt.JWT;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserSupport {

    public Long getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String token = requestAttributes.getRequest().getHeader("Authorization");

        JWT jwtInfo = TokenUtil.getJWTInfo(token);
        return Long.valueOf((String) jwtInfo.getPayload(TokenUtil.USERID));
    }
}
