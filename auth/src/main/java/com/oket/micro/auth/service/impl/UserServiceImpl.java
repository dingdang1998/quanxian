package com.oket.micro.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oket.micro.auth.constant.MessageConstant;
import com.oket.micro.auth.dao.RoleDao;
import com.oket.micro.auth.dao.UserDao;
import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.dto.UserDto;
import com.oket.micro.auth.entity.SysPermission;
import com.oket.micro.auth.entity.SysUser;
import com.oket.micro.auth.service.OrganizationService;
import com.oket.micro.auth.service.PermissionService;
import com.oket.micro.auth.service.ResourceService;
import com.oket.micro.auth.service.UserService;
import com.oket.micro.common.bean.UserDTO;
import com.oket.micro.common.constant.Constants;
import com.oket.micro.common.exception.BusiException;
import com.oket.micro.common.util.LoginUserHolder;
import com.oket.micro.common.util.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-16 13:40
 * @Version: 1.0
 **/

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserDao, SysUser> implements UserService {

	@Resource
	UserDao userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	LoginUserHolder loginUserHolder;
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	PermissionService permissionService;
	@Autowired(required = false)
	RoleDao roleDao;
	@Autowired
	OrganizationService organizationService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public SysUser getUserByName(String name) {
		QueryWrapper<SysUser> queryWrapper=new QueryWrapper<>();
		queryWrapper.eq("login_name",name);
		SysUser sysUser=null;
		try{
			sysUser =userDao.selectOne(queryWrapper);
		}catch (Exception e){
			log.error(e.getMessage());
		}

		return sysUser;
	}

	@Override
	public List<String> getOrgIdsByUserId(Integer userId) {

		return userDao.getOrgIdsByUserId(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOrUpdateUser(SysUser user) {
		if(user.getPassword()!=null){
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		if(user.getId()!=null){
			//??????
			user.setUpdateTime(new Date());
		}else{
			//??????
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
		}
		saveOrUpdate(user);
		Map<String,Object> param=new HashMap<>();
		param.put("userId",user.getId());
		if(StringUtils.isNotBlank(user.getOrgIds())){
			//????????????????????????
			userDao.deleteUserOrg(user.getId());
			//????????????????????????
			String[] orgIdStr= user.getOrgIds().split(",");
			List<Integer> orgIds=new ArrayList<>();
			for (String id:orgIdStr) {
				orgIds.add(Integer.parseInt(id));
			}
			param.put("orgIds", orgIds);
			userDao.saveUserOrg(param);
		}
		if(StringUtils.isNotBlank(user.getRoleIds())){
			//????????????????????????
			userDao.deleteUserRole(user.getId());
			//????????????????????????
			String[] roleIdStr= user.getRoleIds().split(",");
			List<Integer> roleIds=new ArrayList<>();
			for (String id:roleIdStr) {
				roleIds.add(Integer.parseInt(id));
			}
			param.put("roleIds",roleIds);
			userDao.saveUserRole(param);
		}
	}

	@Override
	public Map<String,Object> getUserList(UserDto user) {
		Map<String,Object> param=new HashMap<>();
		if(user.getReqPageParamDto()!=null){
			param= PageUtil.fillPageParam(user.getReqPageParamDto().getPageNum(),user.getReqPageParamDto().getPageRow());
		}
		param.put("status",user.getStatus());
		param.put("loginName",user.getLoginName());
		UserDTO userDTO=loginUserHolder.getCurrentUser();
		//??????????????????????????????
		List<Integer> allOrgIds = (List<Integer>) redisTemplate.opsForHash().get(Constants.USER_INFO+"_"+userDTO.getUsername(),"allOrgIds");
		param.put("orgIdList",allOrgIds);
		if(user.getReqPageParamDto()!=null){
			int totalCount= userDao.queryUserListCount(param);
			param.put("totalCount",totalCount);
		}
		param.put("list",userDao.queryUserList(param));
		Map<String,Object> result= PageUtil.resultMap(param);
		return result;
	}

	@Override
	public boolean queryNameExist(String loginName) {
		SysUser user=getUserByName(loginName);
		return user != null;
	}

	@Override
	public void resetPassword(Integer userId) {
		SysUser user=new SysUser();
		user.setPassword(passwordEncoder.encode("Oket@2021"));
		user.setId(userId);
		updateById(user);
	}

	@Override
	public Map<String, Object> getInfo() {
		UserDTO userDTO=loginUserHolder.getCurrentUser();
		SysUser user=userDao.selectById(userDTO.getId());
		user.setPassword(null);
		Map<String,Object> userInfo=new HashMap<>();
		/*Object orgTree=redisTemplate.opsForHash().get(Constants.USER_INFO+"_"+user.getUsername(),"orgTree");
		Object funTree=redisTemplate.opsForHash().get(Constants.USER_INFO+"_"+user.getUsername(),"funTree");
		userInfo.put("funTree",funTree);
		userInfo.put("orgTree",orgTree);*/
		//?????????
		List<SysPermission> sysPermissionList =null;
		//??????
		List<SysPermission> buttonList=null;
		if(user.getUserName().equals(Constants.ADMIN)){
			//admin??????
			sysPermissionList=permissionService.list();
			buttonList=permissionService.getFunListByUserId(null);
		}else{
			sysPermissionList =permissionService.getPermissionListByUserId(user.getId());
			buttonList=permissionService.getFunListByUserId(user.getId());
		}
		userInfo.put("buttonList",buttonList);
		//?????????????????????????????????????????????????????????redis????????????????????????
		List<String> orgIds=getOrgIdsByUserId(user.getId());
		userInfo.put("orgIds",orgIds);
		List<Integer> allOrgIds=organizationService.getAllCompanyIds(user.getId());
		userInfo.put("allOrgIds",allOrgIds);
		//?????????
		userInfo.put("orgTree",organizationService.getOrganizationTreeFromMany(orgIds,null));
		userInfo.put("funList", sysPermissionList);
		Map<String,Object> queryParam=new HashMap<>();
		queryParam.put("status",Constants.IS_VALID);
		queryParam.put("isMenu",true);
		//?????????
		userInfo.put("funTree",permissionService.buildTreeList(queryParam,sysPermissionList));
		//????????????
		queryParam.put("userId",user.getId());
		List<RoleDto> roles=roleDao.selectListByUserId(queryParam);
		userInfo.put("roleList",roles);
		//??????redis
		redisTemplate.opsForHash().putAll(Constants.USER_INFO+"_"+user.getLoginName(),userInfo);
		//???????????????????????????????????????
		userInfo.put("user",user);
		//??????redis???????????????
		resourceService.initData();
		//??????redis???????????????
		redisTemplate.delete(Constants.ORGANIZATIONS);
		redisTemplate.opsForList().rightPushAll(Constants.ORGANIZATIONS, organizationService.selectList());
		return userInfo;
	}

	@Override
	public void udpPwd(String oldPwd, String newPwd) {
		//??????????????????
		SysUser user=userDao.selectById(loginUserHolder.getCurrentUser().getId());
		if(passwordEncoder.matches(oldPwd,user.getPassword())){
			//???????????????
			user.setPassword(passwordEncoder.encode(newPwd));
			user.setUpdateTime(new Date());
			saveOrUpdate(user);
		}else{
			throw new BusiException(MessageConstant.PASSWORD_ERROR);
		}

	}


}
