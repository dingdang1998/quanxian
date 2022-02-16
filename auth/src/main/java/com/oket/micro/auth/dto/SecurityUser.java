package com.oket.micro.auth.dto;

import com.oket.micro.auth.entity.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: czf
 * @Description: Spring Security 用户
 * @Date: 2021-07-16 14:03
 * @Version: 1.0
 **/

@Data
public class SecurityUser implements UserDetails {



	/**
	 * ID
	 */
	//private Integer id;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 用户密码
	 */
	private String password;
	/**
	 * 用户状态
	 */
	private Boolean enabled;
	/**
	 * 权限数据
	 */
	private Collection<GrantedAuthority> authorities;


	public SecurityUser(String username, String password, Collection<GrantedAuthority> authorities) {
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	public SecurityUser(SysUser sysUser) {
		//this.setId(sysUser.getId());
		this.setUsername(sysUser.getLoginName());
		this.setPassword(sysUser.getPassword());
		this.setEnabled(sysUser.getStatus().equals("1"));
		if (sysUser.getRoleList() != null) {
			authorities = new ArrayList<>();
			// 为用户加入权限信息
			sysUser.getRoleList().forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getId()+"")));
		}
	}

	public SecurityUser() {
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
