package com.oket.micro.auth.jwt;

import com.oket.micro.auth.entity.SysUser;
import com.oket.micro.auth.service.UserService;
import com.oket.micro.common.bean.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-16 15:36
 * 向jwt的token中加入一些自定义的信息
 * @Version: 1.0
 **/
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserService userService;
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = (User) authentication.getUserAuthentication().getPrincipal();
		final HashMap<String, Object> additionalInformation = new HashMap<>();
		// 注意添加的额外信息，最好不要和已有的json对象中的key重名，容易出现错误
		//additionalInformation.put("custom_info", user.getUsername());
		SysUser sysUser = userService.getUserByName(user.getUsername());
		UserDTO userDTO= new UserDTO();
		userDTO.setId(sysUser.getId());
		userDTO.setStatus(Integer.parseInt(sysUser.getStatus()));
		userDTO.setUsername(sysUser.getLoginName());
		userDTO.setPictureUrl(sysUser.getPictureUrl());
		List<String> orgIds=userService.getOrgIdsByUserId(sysUser.getId());
		userDTO.setOrgs(orgIds);
		additionalInformation.put("userDTO", userDTO);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
		return accessToken;
	}
}
