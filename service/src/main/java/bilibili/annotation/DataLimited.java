package bilibili.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 在 limitedRoleCodeList 集合中的角色不允许访问接口
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Component
public @interface DataLimited {
}
