package com.oket.micro.auth.controller;

import com.oket.micro.auth.dto.PermissionDto;
import com.oket.micro.auth.entity.SysPermission;
import com.oket.micro.auth.service.PermissionService;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 功能
 */
@Api(value = "功能",tags = {"功能接口"})
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @ApiOperation(value = "新增修改",notes = "请求示例：{\"parentId\":\"\",\"url\":\"/auth/sysPermission/save\",\"funCode\":\"fun_save\"" +
            ",\"funName\":\"保存功能\",\"requiredFun\":1,\"status\":\"1\"" +
            ",\"type\":0}")
    @PostMapping(value = "/saveOrUpdate",produces = "application/json")
    public Result<String> saveFun(@RequestBody SysPermission sysPermission){
        boolean isSuccess=permissionService.saveOrUpdate(sysPermission);
        return Result.success(Boolean.toString(isSuccess));
    }

    @ApiOperation(value = "获取菜单树",notes = "",produces = "application/json")
    @PostMapping(value = "/getMenuTree",produces = "application/json")
    public Result<Map<String,Object>> getMenuTree(@RequestBody PermissionDto permissionDto){
        Map<String,Object> map=permissionService.getTree(true,permissionDto);
        if(map.get("pageRow")==null){
            return Result.success(map);
        }else{
            return Result.successPage(Integer.parseInt(map.get("pageRow")+""),(List)map.get("list"),Integer.parseInt(map.get("totalCount")+""));
        }
    }

    @ApiOperation(value = "获取功能树",notes = "")
    @PostMapping(value = "/getFunTree",produces = "application/json")
    public Result<Map<String,Object>> getFunTree(@RequestBody PermissionDto permissionDto){
        Map<String,Object> map=permissionService.getTree(false,permissionDto);
        if(map.get("pageRow")==null){
            return Result.success(map);
        }else{
            return Result.successPage(Integer.parseInt(map.get("pageRow")+""),(List)map.get("list"),Integer.parseInt(map.get("totalCount")+""));
        }
    }

}
