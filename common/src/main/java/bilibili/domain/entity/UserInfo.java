package bilibili.domain.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户基本信息表(UserInfo)实体类
 *
 * @author makejava
 * @since 2022-01-28 11:08:01
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 931266731672682374L;
    /**
    * 主键
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userid;
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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
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