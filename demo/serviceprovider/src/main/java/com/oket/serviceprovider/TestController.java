package com.oket.serviceprovider;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-28 8:37
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/test")
public class TestController {


	@SentinelResource(value = "test")
	@GetMapping("/getTest")
	public String getTest() {
		return "服务提供者1：get测试";
	}
}
