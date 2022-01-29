package bilibili.entity;

import lombok.*;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户表(User)实体类
 *
 * @author makejava
 * @since 2022-01-28 11:06:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    private static final long serialVersionUID = 385383181160601352L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 手机号
    */
    private String phone;
    /**
    * 邮箱
    */
    private String email;
    /**
    * 密码
    */
    private String password;
    /**
    * 盐值
    */
    private String salt;
    /**
    * 创建时间
    */
    private Date createtime;
    /**
    * 更新时间
    */
    private Date updatetime;

    private UserInfo userInfo;

}