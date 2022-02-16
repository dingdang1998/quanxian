package com.oket.serverconsumer.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.oket.micro.common.util.LoginUserHolder;
import com.oket.serverconsumer.service.OpenFeignService;
import com.oket.serverconsumer.service.TestProvideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/feign")
public class OpenFeignController {
	@Autowired(required = false)
	LoginUserHolder loginUserHolder;

	static {
		initFlowRules();
	}

	@Autowired
	private TestProvideService testProvideService;

	@Autowired
	private OpenFeignService openFeignService;

	@PostMapping("/save")
	public String save(@RequestBody Map<String,Object> map){

		return "hello world";
	}

	@GetMapping("/test")
	public String feignEcho() throws InterruptedException {
		System.out.println("----------消费者开始消费----------");
		return openFeignService.openFeignTest();
	}


	/***
	 *  这里在代码层进行限流，对代码侵入性比较大
	 * @return
	 */
	@GetMapping("/test1")
	public String test1() {
		System.out.println("----------消费者开始消费----------" + loginUserHolder.getCurrentUser());
		Entry entry = null;
		try {
			entry = SphU.entry("hello");
			System.out.println("进行业务逻辑处理");
			TimeUnit.SECONDS.sleep(1);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BlockException e) {
			System.out.println("--------拒绝------");
			return "----blocked----";
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}
		return testProvideService.getTest();
	}


	public static void initFlowRules() {
		List<FlowRule> rules = new ArrayList<FlowRule>();
		FlowRule rule = new FlowRule();
		rule.setResource("hello");
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setCount(1);
		rules.add(rule);
		FlowRuleManager.loadRules(rules);

	}
}
