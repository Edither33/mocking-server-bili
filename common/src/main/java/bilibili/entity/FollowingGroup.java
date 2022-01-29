package bilibili.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户关注分组表(FollowingGroup)实体类
 *
 * @author makejava
 * @since 2022-01-29 14:04:44
 */
public class FollowingGroup implements Serializable {
    private static final long serialVersionUID = 851152219491633623L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userid;
    /**
    * 关注分组名称
    */
    private String name;
    /**
    * 关注分组类型：0特别关注  1悄悄关注 2默认分组  3用户自定义分组
    */
    private String type;
    /**
    * 创建时间
    */
    private Date createtime;
    /**
    * 更新时间
    */
    private Date updatetime;


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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}