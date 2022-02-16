package com.oket.micro.gateway.servic;

import cn.hutool.core.convert.Convert;
import com.oket.micro.common.constant.Constants;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-23 15:56
 * @Version: 1.0
 **/
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    boolean granted=false;

    @SneakyThrows
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
        Object obj = redisTemplate.opsForHash().get(Constants.RESOURCE_ROLES_MAP, uri.getPath());
        List<String> authorities = Convert.toList(String.class, obj);
        authorities = authorities.stream().map(i -> i = Constants.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //admin用户或者认证通过且角色匹配的用户可访问当前路径
        List<String> finalAuthorities = authorities;
        return mono.filter(Authentication::isAuthenticated).map(authentication -> {
            boolean isAdmin = ((Jwt) (authentication.getPrincipal())).getClaims().get("user_name").equals("admin");
            granted= isAdmin;
            authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).forEach(s -> {
                if (finalAuthorities.contains(s)) {
                    granted=true;
                }
            });
            return new AuthorizationDecision(granted);
        });

    }

}
