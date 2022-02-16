package com.oket.micro.datacenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oket.micro.datacenter.entity.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TestDao extends BaseMapper<Test> {

    List<Test> queryList(Map<String, Object> param);

}
