package com.oket.micro.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.oket.micro.gateway.config.IgnoreUrlsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @Author: czf
 * 当携带过期或签名不正确的JWT令牌访问时，会直接返回token过期的结果
 * 处理：白名单过滤url的处理，去掉请求头的jwt
 * 特别注意knife4j的Authorize是将client_id,client_secret放到Authorization中，此时不能去掉jwt
 * @Date: 2021-07-23 15:58
 * @Version: 1.0
 **/
@Component
public class IgnoreUrlsRemoveJwtFilter implements WebFilter {
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        String TOKEN_URL = "/auth/oauth/token";
        if (StrUtil.equals(exchange.getRequest().getPath().toString(), TOKEN_URL)) {
            return chain.filter(exchange);
        }
        //白名单路径移除JWT请求头
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = exchange.getRequest().mutate().header("Authorization", "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}
