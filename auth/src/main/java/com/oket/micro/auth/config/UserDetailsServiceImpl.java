package com.oket.micro.auth.config;

import com.google.common.collect.Lists;
import com.oket.micro.auth.constant.MessageConstant;
import com.oket.micro.auth.dao.RoleDao;
import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.dto.SecurityUser;
import com.oket.micro.auth.entity.SysPermission;
import com.oket.micro.auth.entity.SysUser;
import com.oket.micro.auth.service.OrganizationService;
import com.oket.micro.auth.service.PermissionService;
import com.oket.micro.auth.service.UserService;
import com.oket.micro.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 登录返回的用户实体
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private OrganizationService organizationService;
    @Autowired(required = false)
    private RoleDao roleDao;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = userService.getUserByName(s);
        List<RoleDto> roles=new ArrayList<>();
        if(sysUser!=null){
            Map<String,Object> param=new HashMap<>();
            param.put("userId",sysUser.getId());
            roles=roleDao.selectListByUserId(param);
            sysUser.setRoleList(roles);
        }
        SecurityUser securityUser = new SecurityUser(sysUser);
        if (!securityUser.getEnabled()) {
            throw new DisabledException(MessageConstant.ACCOUNT_DISABLED);
        }
        if (!securityUser.isAccountNonLocked()) {
            throw new LockedException(MessageConstant.ACCOUNT_LOCKED);
        }
        if (!securityUser.isAccountNonExpired()) {
            throw new AccountExpiredException(MessageConstant.ACCOUNT_EXPIRED);
        }
        if (!securityUser.isCredentialsNonExpired()) {
            throw new CredentialsExpiredException(MessageConstant.CREDENTIALS_EXPIRED);
        }

        List<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
        if (Objects.nonNull(sysUser)) {
            sysUser.getRoleList().forEach(role -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getId()+"");
                grantedAuthorities.add(grantedAuthority);
            });
        }
        //将用户信息（用户角色，关联组织等）放入redis中，便于内部使用
        List<SysPermission> sysPermissionList =null;
        if(s.equals(Constants.ADMIN)){
            //admin全查
            sysPermissionList=permissionService.list();
        }else{
            sysPermissionList =permissionService.getPermissionListByUserId(sysUser.getId());
        }
        List<String> orgIds=userService.getOrgIdsByUserId(sysUser.getId());
        Map<String,Object> userMap=new TreeMap<>();
        userMap.put("orgIds",orgIds);
        List<Integer> allOrgIds=organizationService.getAllCompanyIds(sysUser.getId());
        userMap.put("allOrgIds",allOrgIds);
        //组织树
        userMap.put("orgTree",organizationService.getOrganizationTreeFromMany(orgIds,null));
        userMap.put("funList", sysPermissionList);
        Map<String,Object> queryParam=new HashMap<>();
        queryParam.put("status",Constants.IS_VALID);
        queryParam.put("isMenu",true);
        //菜单树
        userMap.put("funTree",permissionService.buildTreeList(queryParam,sysPermissionList));
        userMap.put("roleList",roles);
        //更新登录时间
        sysUser.setLoginTime(new Date());
        userService.updateById(sysUser);
        redisTemplate.opsForHash().putAll(Constants.USER_INFO+"_"+sysUser.getLoginName(),userMap);
        return new User(sysUser.getLoginName(), sysUser.getPassword(), grantedAuthorities);
    }
}
