package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制-页面访问表(AuthMenu)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMenu implements Serializable {
    private static final long serialVersionUID = -53806904587269356L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 菜单项目名称
    */
    private String name;
    /**
    * 唯一编码
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