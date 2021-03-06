package bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 用户关注分组表(FollowingGroup)实体类
 *
 * @author makejava
 * @since 2022-01-29 14:04:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowingGroup implements Serializable {
    private static final long serialVersionUID = 851152219491633623L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
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
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

    private List<UserInfo> userInfoList;

}