package com.oket.micro.gateway.filter;

import com.oket.micro.gateway.config.IgnoreUrlsConfig;
import com.oket.micro.gateway.servic.OrgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * OrgFilter
 *
 * @author wujh
 * @description 数据权限过滤器, 如果配置了enableOrgFilter才生效
 * @date 2021/10/12
 */
@ConditionalOnProperty(value = {"oket.enableOrgFilter"}, matchIfMissing = false)
@Component
@Slf4j
public class OrgFilter implements GlobalFilter, Ordered {
	@Autowired
	private IgnoreUrlsConfig ignoreUrlsConfig;
	@Autowired
	private OrgService orgService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		if (request.getQueryParams() != null && StringUtils.isNotEmpty(request.getQueryParams().getFirst("orgIds"))) {
			String orgIds = request.getQueryParams().getFirst("orgIds");
			//非白名单，设置数据权限
			if (!ignoreUrlsConfig.getUrls().contains(request.getPath())) {
				request = exchange.getRequest().mutate().header("orgIds", orgService.orgFilter(orgIds).getBody().toString()).build();
				exchange = exchange.mutate().request(request).build();
			}
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return 1;
	}
}
