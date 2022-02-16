package com.oket.micro.gateway.servic;

import com.oket.micro.gateway.component.FeignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 组织服务远程调用，限制组织范围
 */
@ConditionalOnProperty(value = {"oket.enableOrgFilter"}, matchIfMissing = false)
@FeignClient(value = "oauth2-auth", configuration = FeignInterceptor.class)
public interface OrgService {

	@GetMapping(value = "/company/internal/orgFilter")
	ResponseEntity<List<Integer>> orgFilter(@RequestParam("orgIds") String orgIds);

}