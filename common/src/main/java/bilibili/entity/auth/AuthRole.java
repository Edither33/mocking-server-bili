package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--角色表(AuthRole)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRole implements Serializable {
    private static final long serialVersionUID = -30940983916744791L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 角色名称
    */
    private String name;
    /**
    * 角色唯一编码
    */
    private String code;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}