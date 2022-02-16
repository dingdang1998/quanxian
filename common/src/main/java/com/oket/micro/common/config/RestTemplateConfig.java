package com.oket.micro.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplateConfig
 *
 * @author wujh
 * @description rest请求配置
 * @date 2021/07/28
 */
@Configuration(proxyBeanMethods = false)
public class RestTemplateConfig {
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
