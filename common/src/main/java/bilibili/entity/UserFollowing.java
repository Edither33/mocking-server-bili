package bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户关注表(UserFollowing)实体类
 *
 * @author makejava
 * @since 2022-01-29 14:04:08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFollowing implements Serializable {
    private static final long serialVersionUID = -14536513662224258L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 关注用户id
    */
    private Integer followingId;
    /**
    * 关注分组id
    */
    private Integer groupId;
    /**
    * 创建时间
    */
    private Date createTime;

    private UserInfo userInfo;

}