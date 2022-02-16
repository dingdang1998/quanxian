package com.oket.micro.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-19 11:16
 * @Version: 1.0
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    private String username;
    private String password;
    private Integer status;
    private String pictureUrl;
    private List<String> roles;
    private List<String> orgs;

    public UserDTO() {
    }
}
