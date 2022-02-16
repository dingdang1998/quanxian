package com.oket.micro.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 请求入参分页参数
 */
@Data
public class ReqPageParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 第几页
     */
    @ApiModelProperty(value = "第几页", example = "1", required = false)
    private Integer pageNum;

    /**
     * 多少行
     */
    @ApiModelProperty(value = "多少行", example = "20", required = false)
    private Integer pageRow;
}
