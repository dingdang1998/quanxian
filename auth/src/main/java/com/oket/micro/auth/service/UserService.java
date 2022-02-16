package com.oket.micro.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oket.micro.auth.dto.UserDto;
import com.oket.micro.auth.entity.SysUser;

import java.util.List;
import java.util.Map;


public interface UserService extends IService<SysUser> {

    /**
     * 根据登录名获取用户
     * @param name
     * @return
     */
    SysUser getUserByName(String name);

    /**
     * 获取用户关联组织id
     * @param userId
     * @return
     */
    List<String> getOrgIdsByUserId(Integer userId);

    /**
     * 新增或修改
     * @param user
     * @return
     */
    void saveOrUpdateUser(SysUser user);

    /**
     * 根据传入条件查询用户列表
     * @param user
     * @return
     */
    Map<String,Object> getUserList(UserDto user);
    /**
     * 根据传入条件查询用户列表
     * @param loginName
     * @return
     */
    boolean queryNameExist(String loginName);

    /**
     * 用户密码重置
     * @param userId
     */
    void resetPassword(Integer userId);

    /**
     * 获取用户相关信息
     * @return
     */
    Map<String,Object> getInfo();

    /**
     * 修改当前密码
     * @param oldPwd
     * @param newPwd
     */
    void udpPwd(String oldPwd,String newPwd);

}
