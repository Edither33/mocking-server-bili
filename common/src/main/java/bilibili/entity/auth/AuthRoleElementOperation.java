package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色与元素操作关联表(AuthRoleElementOperation)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRoleElementOperation implements Serializable {
    private static final long serialVersionUID = 644505235350730620L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 元素操作id
    */
    private Long elementOperationId;
    /**
    * 创建时间
    */
    private Date createTime;
}