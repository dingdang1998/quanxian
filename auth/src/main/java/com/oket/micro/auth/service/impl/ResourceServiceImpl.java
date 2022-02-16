package com.oket.micro.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oket.micro.auth.dao.RoleDao;
import com.oket.micro.auth.entity.SysPermission;
import com.oket.micro.auth.service.PermissionService;
import com.oket.micro.auth.service.ResourceService;
import com.oket.micro.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: czf
 * @Date: 2021-07-16 17:06
 * @Description: 初始化的时候把资源与角色匹配关系缓存到Redis中，方便网关服务进行鉴权的时候获取
 * @Version: 1.0
 **/
@Service
public class ResourceServiceImpl implements ResourceService {
	private Map<String, List<String>> resourceRolesMap;

	@Autowired
	private HashOperations<String,String, Object> hashOperations;
	@Autowired
	PermissionService permissionService;
	@Autowired(required = false)
	RoleDao roleDao;


	@Override
	@PostConstruct
	public void initData() {
		resourceRolesMap = new TreeMap<>();
		//查功能对应的角色
		QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("type",1);//功能
		queryWrapper.eq("status","1");
		List<SysPermission> permissionList=permissionService.list(queryWrapper);
		for (SysPermission p:permissionList) {
			//对应的角色id列表
			List<String> roleIds=roleDao.queryRoleIdsByFun(p.getId());
			if(resourceRolesMap.get(p.getUrl())!=null){
				roleIds.addAll(resourceRolesMap.get(p.getUrl()));
			}
			resourceRolesMap.put(p.getUrl(), roleIds);
		}
		hashOperations.putAll(Constants.RESOURCE_ROLES_MAP, resourceRolesMap);

	}


}
