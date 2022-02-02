package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户角色关联表(UserRole)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRole implements Serializable {
    private static final long serialVersionUID = 807197846696346367L;
    
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 角色id
    */
    private Long roleId;
    /**
    * 创建时间
    */
    private Date createTime;

    private String roleName;

    private String roleCode;

}