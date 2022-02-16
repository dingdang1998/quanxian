package com.oket.micro.common.util;

import com.google.gson.Gson;
import com.oket.micro.common.bean.UserDTO;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-19 11:15
 * @Version: 1.0
 **/

@Component
public class LoginUserHolder {

    public UserDTO getCurrentUser(){
        //从Header中获取用户信息
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userStr = request.getHeader("user");
        Gson gson=new Gson();
        Map<String,Object> userMap = gson.fromJson(userStr, Map.class);
        Map<String,Object> userDTOMap=(Map)userMap.get("userDTO");
        UserDTO userDTO=gson.fromJson(gson.toJson(userDTOMap), UserDTO.class);
        return userDTO;
    }


}
