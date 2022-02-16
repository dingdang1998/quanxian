package com.oket.micro.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_role")
public class SysRole extends Model<SysRole> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "角色名", example = "客户管理员", required = true)
    @TableField(value = "role_name")
    private String roleName;
    @ApiModelProperty(value = "关联组织id", example = "2", required = true)
    @TableField(value = "org_id")
    private Integer orgId;
    @ApiModelProperty(value = "绑定的功能id数组",required = false)
    @TableField(exist = false)
    private List<Integer> funIdList;
}
