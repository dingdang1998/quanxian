package com.oket.micro.gateway.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


/**
 * feign接口注入用户信息到请求头
 */
@Slf4j
@Component
@ConditionalOnProperty(value = {"oket.enableOrgFilter"}, matchIfMissing = false)
public class FeignInterceptor implements RequestInterceptor {
	public static final ThreadLocal<String> LOCAL_USER = new ThreadLocal();

	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header("user", LOCAL_USER.get());
	}
}
