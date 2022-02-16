package com.oket.micro.datacenter.controller;

import com.oket.micro.datacenter.dao.TestDao;
import com.oket.micro.datacenter.entity.Test;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "业务测试接口",tags = {"业务测试接口"})
@RestController
@RequestMapping("/test")
public class TestController {

    /*@Autowired
    OrgService orgService;*/
    @Resource
    TestDao testDao;

    @ApiOperation(value = "业务列表查询测试接口")
    @GetMapping("/getTest")
    public Result<List<Test>> getTestList(HttpServletRequest httpServletRequest, @RequestParam(required = false) String orgIds){
        String orgIdss=httpServletRequest.getHeader("orgIds");
        Map<String ,Object> param=new HashMap<>();
        //param.put("orgIdList",orgService.orgFilter(orgIds).getBody());
        param.put("orgIds",orgIdss);
        List<Test> list=testDao.queryList(param);
        return Result.success(list);
    }


}
