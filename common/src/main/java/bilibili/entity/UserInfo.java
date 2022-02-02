package bilibili.entity;

import lombok.*;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户基本信息表(UserInfo)实体类
 *
 * @author makejava
 * @since 2022-01-28 11:08:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 931266731672682374L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 昵称
    */
    private String nick;
    /**
    * 头像
    */
    private String avatar;
    /**
    * 签名
    */
    private String sign;
    /**
    * 性别：0男 1女 2未知
    */
    private String gender;
    /**
    * 生日
    */
    private String birth;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

    private Boolean followed;
}