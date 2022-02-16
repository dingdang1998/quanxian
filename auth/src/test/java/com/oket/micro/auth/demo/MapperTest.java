package com.oket.micro.auth.demo;

import com.oket.micro.auth.AuthApplication;
import com.oket.micro.auth.dao.PermissionDao;
import com.oket.micro.auth.entity.Organization;
import com.oket.micro.common.constant.Constants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = AuthApplication.class)
@RunWith(SpringRunner.class)
public class MapperTest {
    @Resource
    private PermissionDao permissionDao;

    @Resource
    RedisTemplate redisTemplate;

    @Test
    public void TestPermissionMapper() {
        /*List<SysPermission> permissions = permissionDao.selectListByUserId(37L);
        permissions.forEach(System.out::print);*/
        List<Organization> organizationList=redisTemplate.opsForList().range(Constants.ORGANIZATIONS, 0,-1);
        System.out.println(organizationList.size());
    }


}
