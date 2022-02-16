package com.oket.micro.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Mapper
public interface RoleDao extends BaseMapper<SysRole> {

    /**
     * 根据用户id获取角色列表
     */
    List<RoleDto> selectListByUserId(Map<String,Object> param);

    /**
     * 根据角色id查角色功能关联关系表
     */
    List<Integer> getFunByRoleId(int roleId);
    /**
     * 根据用户id获取角色列表总数
     */
    int selectListByUserIdCount(Map<String,Object> param);

    /**
     * 根据用户用户管理员id获取组织角色列表
     */
    List<RoleDto> selectListByUser(Map<String,Object> param);

    /**
     * 根据用户用户管理员id获取组织角色列表总数
     */
    int selectListByUserCount(Map<String,Object> param);

    /**
     * 获取所有角色列表
     */
    List<RoleDto> selectAllList(Map<String,Object> param);

    /**
     * 获取所有角色列表总数
     */
    int selectAllListCount(Map<String,Object> param);

    /**
     * 删除角色功能绑定关系表
     * @return
     */
    int deleteRoleFunByRoleId(Integer roleId);

    /**
     * 根据角色主键删除用户角色关系
     * @param roleId
     * @return
     */
    int deleteUserRoleByRoleId(Integer roleId);

    /**
     * 根据组织id查角色
     * @param orgId
     * @return
     */
    List<RoleDto> selectListByOrgId(Integer orgId);

    /**
     * 角色功能绑定关系
     * @param role
     * @return
     */
    int saveRoleFun(SysRole role);

    /**
     * 根据组织查其绑定的角色列表
     */
    List<RoleDto> queryListByOrg(Integer orgId);

    /**
     * 根据权限查绑定的角色id列表
     * @return
     */
    List<String> queryRoleIdsByFun(Integer funId);
}
