package com.oket.micro.datacenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "test")
public class Test implements Serializable {

    @TableId(value = "id", type= IdType.AUTO)
    private Integer id;

    @TableField(value = "content")
    private String content;

    @TableField(value = "org_id")
    private Integer orgId;

    private String orgName;
}
