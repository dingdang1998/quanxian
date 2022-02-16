package com.oket.micro.auth.controller;

import com.oket.micro.auth.dto.UserDto;
import com.oket.micro.auth.entity.SysUser;
import com.oket.micro.auth.service.UserService;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(value = "用户",tags = {"用户管理"})
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户新增修改",notes = "")
    @PostMapping(value = "/save",produces = "application/json")
    public Result save(@RequestBody SysUser user){
        userService.saveOrUpdateUser(user);
        return Result.success("");
    }
    @ApiOperation(value="用户列表查询",notes = "请求示例：{\"userName\":\"用户管理员\",\"status\":\"1\"}")
    @PostMapping(value = "/list",produces = "application/json")
    public Result<Map<String,Object>> list(@RequestBody UserDto user){
        return Result.success(userService.getUserList(user));
    }
    @ApiOperation(value="查询用户名是否重复",notes = "请求示例：{\"userName\":\"用户管理员\"}")
    @GetMapping(value = "/queryNameExist",produces = "application/json")
    public Result<Boolean> queryNameExist(@RequestParam String loginName){
        return Result.success(userService.queryNameExist(loginName));
    }

    @ApiOperation(value = "获取当前用户信息",notes = "")
    @GetMapping(value = "/getInfo")
    public Result<Map<String,Object>> getInfo(){
        Map<String,Object> userInfo=userService.getInfo();
        return Result.success(userInfo);
    }

    @ApiOperation(value="用户密码重置",notes = "")
    @GetMapping(value = "/resetPassword")
    public Result resetPassword(@RequestParam Integer userId){
        userService.resetPassword(userId);
        return Result.success("");
    }
    @ApiOperation(value = "密码修改",notes = "")
    @PutMapping("/updPwd")
    public Result updPwd(@RequestParam String oldPwd,@RequestParam String newPwd){
        userService.udpPwd(oldPwd,newPwd);
        return Result.success("");

    }
}
