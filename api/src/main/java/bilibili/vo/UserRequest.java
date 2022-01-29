package bilibili.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("用户登录请求体")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("验证码唯一标识")
    private String uuid;
}
