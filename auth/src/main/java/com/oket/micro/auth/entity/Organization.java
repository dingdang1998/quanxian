package com.oket.micro.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@TableName(value = "sys_org")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    // 公司名
    @ApiModelProperty(value = "组织名", example = "化工厂A", required = true)
    @TableField(value = "org_name")
    private String orgName;
    // 备注
    @ApiModelProperty(hidden = true)
    @TableField(value = "remark")
    private String remark;
    // 父ID
    @ApiModelProperty(value = "父组织id(为空就是顶级组织)", example = "2", required = false)
    @TableField(value = "parent_id")
    private String parentId;
    //所有父级id
    @ApiModelProperty(hidden = true)
    @TableField(value = "parent_ids")
    private String parentIds;
    // 类型（0化工厂 1车队 2政府等）
    @ApiModelProperty(value = "类型（0化工厂 1车队 2政府等）",example = "0",required = true)
    @TableField(value = "type")
    private String type;
    // 有效标识 1:有效 2:无效
    @ApiModelProperty(value = "有效标识 1:有效 2:无效",example = "1",required = true)
    @TableField(value = "status")
    private String status;
    // 地址
    @ApiModelProperty(value = "地址",example = "",required = false)
    @TableField(value = "address")
    private String address;
    //子公司集合
    @ApiModelProperty(value = "子级组织（只有列表查询时才有）",example = "",required = false)
    @TableField(exist = false)
    private List<Organization> children;
}
