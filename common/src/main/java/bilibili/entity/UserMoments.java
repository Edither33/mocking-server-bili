package bilibili.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 用户动态表(UserMoments)实体类
 *
 * @author makejava
 * @since 2022-02-01 11:05:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMoments implements Serializable {
    private static final long serialVersionUID = 676205824125871590L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 用户id
    */
    private Long userId;
    /**
    * 动态类型：0视频 1直播 2专栏动态
    */
    private String type;
    /**
    * 内容详情id
    */
    private Long contentId;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}