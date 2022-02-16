package com.oket.micro.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oket.micro.auth.entity.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 权限
 */
@Mapper
public interface PermissionDao extends BaseMapper<SysPermission> {
    /**
     * 根据用户获取其功能
     * @param
     * @return
     */
    List<SysPermission> selectListByUserId(Map<String,Object> map);

    /**
     * 绑定功能和超管角色关系
     * @param map
     */
    void saveAdminRoleFun(Map<String,Object> map);

}
