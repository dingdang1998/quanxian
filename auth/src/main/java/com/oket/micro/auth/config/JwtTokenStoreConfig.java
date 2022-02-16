package com.oket.micro.auth.config;

import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-19 11:26
 * @Version: 1.0
 **/

public class JwtTokenStoreConfig {


	public TokenStore jwtTokenStore() {
		return new JwtTokenStore(jwtAccessTokenConverter());
	}

	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
		//配置JWT使用的秘钥
		accessTokenConverter.setSigningKey("test_key");
		return accessTokenConverter;
	}

}
