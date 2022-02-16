package com.oket.micro.gateway.filter;

import cn.hutool.core.util.StrUtil;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

import static com.oket.micro.gateway.component.FeignInterceptor.LOCAL_USER;

/**
 * @Author: czf
 * @Description: 统一将token解析为用户信息放入请求头中，使得其他服务不用再解析
 * @Date: 2021-07-23 16:01
 * @Version: 1.0
 **/
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
	@Value("${oket.enableOrgFilter:false}")
	boolean orgFilterEnabled;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String token = exchange.getRequest().getHeaders().getFirst("Authorization");
		if (StrUtil.isEmpty(token) || token.startsWith("Basic")) {
			return chain.filter(exchange);
		}
		try {
			//从token中解析用户信息并设置到Header中去
			String realToken = token.replace("Bearer ", "")
					.replace("bearer ", "");
			JWSObject jwsObject = JWSObject.parse(realToken);
			String userStr = jwsObject.getPayload().toString();
			log.info("AuthGlobalFilter.filter() user:{}", userStr);
			if (orgFilterEnabled) {
				LOCAL_USER.set(userStr);
			}
			ServerHttpRequest request = exchange.getRequest().mutate().header("user", userStr).build();
			exchange = exchange.mutate().request(request).build();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 0;
	}
}
