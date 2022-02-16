package com.oket.micro.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;



/**
 * @author liuzhixiang liuzhixiang
 * DATE   2021/8/31
 */
@Data
public class ReqTokenDto {

    @ApiModelProperty(value = "客户端", example = "client-app", required = true)
    private String client_id = "client-app";

    @ApiModelProperty(value = "认证类型", example = "password", required = true)
    private String grant_type = "password";

    @ApiModelProperty(value = "客户端密钥", example = "123456", required = true)
    private String client_secret = "123456";

    @ApiModelProperty(value = "用户名", example = "admin", required = false)
    @Length(min = 3,message = "最少3个字符")
    private String username;

    @ApiModelProperty(value = "密码", example = "123456", required = false)
    private String password;

    @ApiModelProperty(value = "refresh_token", example = "",required = false)
    private String refresh_token;
}
