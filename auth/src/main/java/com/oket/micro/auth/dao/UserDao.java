package com.oket.micro.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oket.micro.auth.dto.UserDto;
import com.oket.micro.auth.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 用户
 */
@Mapper
public interface UserDao extends BaseMapper<SysUser> {
    /**
     * 获取用户关联组织id
     * @param userId
     * @return
     */
    List<String> getOrgIdsByUserId(Integer userId);

    /**
     * 保存用户组织关系
     * @return
     */
    int saveUserOrg(Map<String,Object> param);

    /**
     * 删除用户组织关系
     * @param userId
     */
    void deleteUserOrg(Integer userId);

    /**
     * 保存用户角色关系
     * @return
     */
    int saveUserRole(Map<String,Object> param);

    /**
     * 删除用户角色关系
     * @param userId
     */
    void deleteUserRole(Integer userId);

    /**
     * 条件查询列表
     * @param user
     * @return
     */
    List<UserDto> queryUserList(Map<String,Object> user);
    /**
     * 条件查询列表
     * @param user
     * @return
     */
    Integer queryUserListCount(Map<String,Object> user);

    /**
     * 根据用户主键查关联角色名
     * @param userId
     * @return
     */
    List<String> getRoleNamesByUserId(Integer userId);
    /**
     * 根据用户主键查关联角色id
     * @param userId
     * @return
     */
    List<Integer> getRoleIdsByUserId(Integer userId);
}
