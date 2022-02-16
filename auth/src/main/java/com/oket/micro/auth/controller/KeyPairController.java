package com.oket.micro.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Author: czf
 * @Date: 2021-07-16 16:13
 * @Description: 由于我们的网关服务需要RSA的公钥来验证签名是否合法，所以认证服务需要有个接口把公钥暴露出来；
 * @Version: 1.0
 **/

@Api(value = "公钥",tags = "获取公钥接口")
@RestController
public class KeyPairController {
	@Autowired
	private KeyPair keyPair;

	@GetMapping("/rsa/publicKey")
	public Map<String, Object> getPublicKey() {
		RSAPublicKey aPublic = (RSAPublicKey) keyPair.getPublic();
		RSAKey build = new RSAKey.Builder(aPublic).build();
		return new JWKSet(build).toJSONObject();
	}

}
