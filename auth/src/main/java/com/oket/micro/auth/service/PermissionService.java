package com.oket.micro.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oket.micro.auth.dto.PermissionDto;
import com.oket.micro.auth.entity.SysPermission;

import java.util.List;
import java.util.Map;

/**
 * 权限
 */
public interface PermissionService extends IService<SysPermission> {

    /**
     * 保存或修改
     * @param sysPermission
     * @return
     */
    boolean saveOrUpdateFun(SysPermission sysPermission);
    /**
     * 根据用户获取其权限列表
     * @param userId
     * @return
     */
    List<SysPermission> getPermissionListByUserId(Integer userId);

    /**
     * 获取菜单树
     * @return
     */
    Map<String,Object> getTree(boolean isMenu, PermissionDto permissionDto);

    /**
     * 根据条件筛选结果树
     * @param queryParam
     * @param permissionList
     * @return
     */
    List<SysPermission> buildTreeList(Map<String,Object> queryParam, List<SysPermission> permissionList);

    /**
     * 根据用户获取其按钮列表
     * @param userId
     * @return
     */
    List<SysPermission> getFunListByUserId(Integer userId);
}
