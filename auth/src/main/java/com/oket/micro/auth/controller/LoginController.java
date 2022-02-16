package com.oket.micro.auth.controller;

import com.oket.micro.auth.dto.Oauth2TokenDto;
import com.oket.micro.auth.dto.ReqTokenDto;
import com.oket.micro.auth.service.AuthService;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-19 10:10
 * @Version: 1.0
 **/
@Api(value = "认证",tags = "登录认证接口")
@RestController
@Slf4j
public class LoginController {
    //oauth2
    @Autowired
    private AuthService authService;

    /**
     * Oauth2登录认证
     */
//    @SneakyThrows
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation("登录获取token")
    public Result<Oauth2TokenDto> login(@Validated ReqTokenDto reqTokenDto) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("client_id", reqTokenDto.getClient_id());
        parameters.put("client_secret", reqTokenDto.getClient_secret());
        parameters.put("grant_type", reqTokenDto.getGrant_type());
        parameters.put("username", reqTokenDto.getUsername());
        parameters.put("password", reqTokenDto.getPassword());
        parameters.put("refresh_token", reqTokenDto.getRefresh_token());
        OAuth2AccessToken oAuth2AccessToken = new OAuth2AccessToken() {
            @Override
            public Map<String, Object> getAdditionalInformation() {
                return null;
            }

            @Override
            public Set<String> getScope() {
                return null;
            }

            @Override
            public OAuth2RefreshToken getRefreshToken() {
                return null;
            }

            @Override
            public String getTokenType() {
                return null;
            }

            @Override
            public boolean isExpired() {
                return false;
            }

            @Override
            public Date getExpiration() {
                return null;
            }

            @Override
            public int getExpiresIn() {
                return 0;
            }

            @Override
            public String getValue() {
                return null;
            }
        };
        try{
            oAuth2AccessToken = authService.getAccessToken(parameters).getBody();
        }catch (Exception e){
            log.error("登录错误",e);
            System.out.println(e.getMessage());
            if(e.getMessage().startsWith("[401]") || e.getMessage().startsWith("[400]")){
                System.out.println("用户名密码错误！");
                return Result.failed("用户名密码错误！");
            }
        }
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return Result.success(oauth2TokenDto);
    }

}
