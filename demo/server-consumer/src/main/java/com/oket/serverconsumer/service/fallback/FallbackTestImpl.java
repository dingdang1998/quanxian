package com.oket.serverconsumer.service.fallback;

import com.oket.serverconsumer.service.TestProvideService;
import org.springframework.stereotype.Service;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-13 17:18
 * @Version: 1.0
 **/

@Service
public class FallbackTestImpl implements TestProvideService {
	@Override
	public String getTest() {
		return "get测试服务暂时不可用，降级返回";
	}
}
