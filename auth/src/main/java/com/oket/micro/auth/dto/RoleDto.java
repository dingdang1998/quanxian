package com.oket.micro.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    //@TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "角色名", example = "客户管理员", required = true)
    //@TableField(value = "role_name")
    private String roleName;
    @ApiModelProperty(value = "关联组织id", example = "2", required = true)
    //@TableField(value = "org_id")
    private Integer orgId;
    @ApiModelProperty(value = "组织名",example = "化工厂a",required = false)
    //@TableField(value = "org_name")
    private String orgName;
    /*@ApiModelProperty(value = "绑定的功能id数组",required = false)
    //@TableField(exist = false)
    private List<SysPermission> funIdList;*/

    @ApiModelProperty(value = "绑定的功能id数组",required = false)
    //@TableField(exist = false)
    private List<Integer> funIds;
}
