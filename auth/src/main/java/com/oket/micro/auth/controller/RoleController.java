package com.oket.micro.auth.controller;

import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.entity.SysRole;
import com.oket.micro.auth.service.RoleService;
import com.oket.micro.common.util.PageUtil;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Api(value = "角色controller",tags = {"角色接口"})
@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 新增修改
     * @param role
     * @return
     */
    @ApiOperation(value = "角色新增修改",notes = "请求示例：{\"roleName\":\"用户管理员\",\"orgId\":2}")
    @PostMapping(value = "/saveOrUpdate",produces = "application/json")
    public Result<String> saveOrUpdate(@RequestBody SysRole role){
        roleService.saveOrUpdateRole(role);
        return Result.success("");
    }

    @ApiOperation(value = "角色删除",notes = "请求示例：{\"roleId\":2}")
    @DeleteMapping(value = "/delete",produces = "application/json")
    public Result<String> delete(@RequestParam Integer roleId){
        boolean isSuccess=roleService.deleteRole(roleId);
        return Result.success(Boolean.toString(isSuccess));
    }
    @ApiOperation(value = "用户角色查询",notes = "请求示例：{\"userId\":2}")
    @GetMapping(value = "/user/list",produces = "application/json")
    public Result<Map<String,Object>> queryList(@RequestParam Integer userId,
                                           @RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageRow){
        Map<String,Object> param= PageUtil.fillPageParam(pageNum,pageRow);
        param.put("userId",userId);
        return Result.success(roleService.queryList(param));
    }

    @ApiOperation(value = "角色列表查询",notes = "请求示例：{\"roleName\":\"角色\"}")
    @GetMapping(value = "/list")
    public Result<Map<String,Object>> queryList(@RequestParam(required = false) String roleName,
                                           @RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageRow){
        Map<String,Object> param= PageUtil.fillPageParam(pageNum,pageRow);
        param.put("roleName",roleName);
        return Result.success(roleService.queryList(param));
    }

    @ApiOperation(value = "根据组织查其绑定的角色列表",notes = "请求示例：{\"orgId\":2}")
    @GetMapping(value = "/org/list")
    public Result<List<RoleDto>> queryListByOrg(@RequestParam Integer orgId){
        return Result.success(roleService.queryListByOrg(orgId));
    }

}
