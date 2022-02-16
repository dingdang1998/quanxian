package com.oket.micro.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: czf
 * @Description: 网关白名单
 * @Date: 2021-07-23 15:54
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class IgnoreUrlsConfig {
    private List<String> urls;
}
