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
@TableName("sys_fun")
public class SysPermission extends Model<SysPermission> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "父id，不写为顶级菜单", example = "2", required = false)
    @TableField(value = "parent_id")
    private Integer parentId;
    @ApiModelProperty(value = "路径", example = "/auth/save/fun", required = false)
    @TableField(value = "url")
    private String url;
    @ApiModelProperty(value = "功能编码", example = "save_fun", required = true)
    @TableField(value = "fun_code")
    private String funCode;
    @ApiModelProperty(value = "功能名", example = "保存功能", required = true)
    @TableField(value = "fun_name")
    private String funName;
    @ApiModelProperty(value = "是否本菜单必选权限, 1.必选 0非必选 通常是\"列表\"权限是必选", example = "1", required = true)
    @TableField(value = "required_fun")
    //是否本菜单必选权限, 1.必选 0非必选 通常是"列表"权限是必选
    private Integer requiredFun;
    //1:有效 2:无效
    @ApiModelProperty(value = "1:有效 0:无效", example = "1", required = true)
    @TableField(value = "status")
    private String status;
    //类型（0菜单，1功能按钮）
    @ApiModelProperty(value = "类型（0菜单，1功能按钮）", example = "1", required = true)
    @TableField(value = "type")
    private Integer type;
    //子菜单
    @ApiModelProperty(value = "请求时无此参数")
    @TableField(exist = false)
    List<SysPermission> children;
}