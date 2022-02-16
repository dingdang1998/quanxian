package com.oket.micro.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
public interface RoleService extends IService<SysRole> {

    /**
     * 删除
     * @return
     */
    boolean deleteRole(Integer roleId);

    /**
     * 角色列表查询
     * @return
     */
    Map<String,Object> queryList(Map<String,Object> param);

    /**
     * 新增或修改
     * @param role
     * @return
     */
    void saveOrUpdateRole(SysRole role);

    /**
     * 根据组织查其绑定的角色列表
     * @param orgId
     * @return
     */
    List<RoleDto> queryListByOrg(Integer orgId);
}
