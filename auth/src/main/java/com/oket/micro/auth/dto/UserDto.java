package com.oket.micro.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 查询返回
 */
@Data
public class UserDto {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    private Integer id;
    @ApiModelProperty(value = "登陆用户名，不可重复", example = "macro", required = true)
    private String loginName;
    /*@ApiModelProperty(value = "密码", example = "123456", required = true)
    @TableField(value = "password",exist = false)
    private String password;*/
    @ApiModelProperty(value = "昵称", example = "丁丁", required = true)
    private String userName;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更改时间")
    private Date updateTime;
    @ApiModelProperty(value = "1:有效 2:无效", example = "1", required = true)
    private String status;
    @ApiModelProperty(value = "最后登录时间",example = "",required = false)
    private Date loginTime;
    @ApiModelProperty(value = "邮箱", example = "2343435@qq.com", required = false)
    private String email;


    @ApiModelProperty(value = "关联角色信息，用于展示", example = "", required = false)
    private List<Integer> roleIds;
    @ApiModelProperty(value = "组织主键,用于展示", example = "[1,2,3]", required = false)
    private List<Integer> orgIds;

    @ApiModelProperty(value = "关联角色名，用于展示", example = "", required = false)
    private List<String> roleNames;

    @ApiModelProperty(value = "关联组织名,用于展示", example = "化工厂2", required = false)
    private String orgName;
    @ApiModelProperty(value = "关联组织主键,用于展示", example = "2", required = false)
    private Integer orgId;

    @ApiModelProperty(value = "分页参数")
    private ReqPageParamDto reqPageParamDto;
}
