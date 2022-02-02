package bilibili.entity.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 权限控制--页面元素操作表(AuthElementOperation)实体类
 *
 * @author makejava
 * @since 2022-02-02 10:55:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthElementOperation implements Serializable {
    private static final long serialVersionUID = -98465600316821410L;
    /**
    * 主键id
    */
    private Long id;
    /**
    * 页面元素名称
    */
    private String elementName;
    /**
    * 页面元素唯一编码
    */
    private String elementCode;
    /**
    * 操作类型：0可点击  1可见
    */
    private String operationType;
    /**
    * 创建时间
    */
    private Date createTime;
    /**
    * 更新时间
    */
    private Date updateTime;

}