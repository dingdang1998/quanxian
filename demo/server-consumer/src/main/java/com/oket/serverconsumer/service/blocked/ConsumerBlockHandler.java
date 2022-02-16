package com.oket.serverconsumer.service.blocked;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-14 16:56
 * @Version: 1.0
 **/
public class ConsumerBlockHandler {

	public String openFeignTest() {
		return "服务阻塞 降级返回";
	}

}
