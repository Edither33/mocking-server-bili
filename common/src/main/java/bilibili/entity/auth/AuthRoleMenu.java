package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色页面菜单关联表(AuthRoleMenu)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRoleMenu implements Serializable {
    private static final long serialVersionUID = -92906045411000592L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 页面菜单id
    */
    private Long menuId;
    /**
    * 创建时间
    */
    private Date createTime;

    private AuthMenu authMenu;
}