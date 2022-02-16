package com.oket.serverconsumer.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-13 15:20
 * @Version: 1.0
 **/

@FeignClient(name = "serviceprovider")
public interface TestProvideService {

	/**
	 * 测试代码
	 *
	 * @return
	 */
	@GetMapping("/test/getTest")
	String getTest();

}
