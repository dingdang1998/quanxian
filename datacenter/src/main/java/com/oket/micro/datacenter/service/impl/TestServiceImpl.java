package com.oket.micro.datacenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oket.micro.datacenter.dao.TestDao;
import com.oket.micro.datacenter.entity.Test;
import com.oket.micro.datacenter.service.TestService;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl extends ServiceImpl<TestDao,Test> implements TestService {
}
