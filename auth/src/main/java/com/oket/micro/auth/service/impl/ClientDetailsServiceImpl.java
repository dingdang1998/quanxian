package com.oket.micro.auth.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @Author: czf
 * @Description: 这个可以自己定义获取客户端信息的方法
 * @Date: 2021-07-23 10:47
 * @Version: 1.0
 **/

@AllArgsConstructor
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return null;
	}
}
