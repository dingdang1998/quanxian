package com.oket.micro.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 认证服务远程调用
 */
@FeignClient("oauth2-auth")
public interface AuthService {

    @PostMapping(value = "/oauth/token")
    ResponseEntity<OAuth2AccessToken> getAccessToken(@RequestParam Map<String, String> parameters);

}