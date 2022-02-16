package com.oket.micro.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PermissionDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键，修改时必填", example = "2", required = false)
    private Integer id;
    @ApiModelProperty(value = "父id，不写为顶级菜单", example = "2", required = false)
    private Integer parentId;
    @ApiModelProperty(value = "路径", example = "/auth/save/fun", required = false)
    private String url;
    @ApiModelProperty(value = "功能编码", example = "save_fun", required = false)
    private String funCode;
    @ApiModelProperty(value = "功能名", example = "保存功能", required = false)
    private String funName;
    @ApiModelProperty(value = "是否本菜单必选权限, 1.必选 0非必选 通常是\"列表\"权限是必选", example = "1", required = false)
    //是否本菜单必选权限, 1.必选 0非必选 通常是"列表"权限是必选
    private Integer requiredFun;
    //1:有效 2:无效
    @ApiModelProperty(value = "1:有效 2:无效", example = "1", required = false)
    private String status;
    //类型（0菜单，1功能按钮）
    @ApiModelProperty(value = "类型（0菜单，1功能按钮）", example = "1", required = false)
    private Integer type;

    @ApiModelProperty(value = "请求时无此参数")
    private ReqPageParamDto reqPageParamDto;
}