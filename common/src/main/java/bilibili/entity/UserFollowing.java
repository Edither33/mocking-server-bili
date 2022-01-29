package bilibili.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户关注表(UserFollowing)实体类
 *
 * @author makejava
 * @since 2022-01-29 14:04:08
 */
public class UserFollowing implements Serializable {
    private static final long serialVersionUID = -14536513662224258L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userid;
    /**
    * 关注用户id
    */
    private Integer followingid;
    /**
    * 关注分组id
    */
    private Integer groupid;
    /**
    * 创建时间
    */
    private Date createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getFollowingid() {
        return followingid;
    }

    public void setFollowingid(Integer followingid) {
        this.followingid = followingid;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

}