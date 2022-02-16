package com.oket.micro.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.oket.micro.auth.dto.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "登陆用户名，不可重复", example = "2", required = true)
    @TableField(value = "login_name")
    private String loginName;
    @ApiModelProperty(value = "密码", example = "123456", required = true)
    @TableField(value = "password")
    private String password;
    @ApiModelProperty(value = "昵称", example = "丁丁", required = true)
    @TableField(value = "user_name")
    private String userName;
    @ApiModelProperty(hidden = true)
    @TableField(value = "create_time")
    private Date createTime;
    @ApiModelProperty(hidden = true)
    @TableField(value = "update_time")
    private Date updateTime;
    @ApiModelProperty(value = "1:有效 2:无效", example = "1", required = true)
    @TableField(value = "status")
    private String status;
    @ApiModelProperty(hidden = true)
    @TableField(value = "login_time")
    private Date loginTime;
    @ApiModelProperty(value = "邮箱", example = "2343435@qq.com", required = false)
    @TableField(value = "email")
    private String email;
    @ApiModelProperty(value = "头像图片地址", example = "/picture/11", required = false)
    @TableField(value = "picture_url")
    private String pictureUrl;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色主键(新增用)", example = "2,3,4", required = false)
    private String roleIds;
    @TableField(exist = false)
    @ApiModelProperty(value = "组织主键(新增用)", example = "1,2,3", required = false)
    private String orgIds;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色信息列表（展示用）", example = "", required = false)
    private List<RoleDto> roleList;
    @TableField(exist = false)
    @ApiModelProperty(value = "组织主键（展示用）", example = "[1,2,3]", required = false)
    private List<String> orgIdList;
}
