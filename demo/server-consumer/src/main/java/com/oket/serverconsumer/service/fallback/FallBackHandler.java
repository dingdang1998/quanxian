package com.oket.serverconsumer.service.fallback;

import org.springframework.stereotype.Component;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-14 16:38
 * @Version: 1.0
 **/

@Component
public class FallBackHandler {

	public String openFeignTest() {
		return "发生异常，进行降级容错";
	}
}
