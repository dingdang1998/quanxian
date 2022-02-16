package com.oket.micro.common.constant;


/**
 * @description: 通用常量类, 单个业务的常量请单开一个类, 方便常量的分类管理
 */
public class Constants {
    public static final String SUCCESS_CODE = "100";
    public static final String SUCCESS_MSG = "请求成功";
    public static final String FAIL_CODE = "400";
    public static final String FAIL_MSG = "已经存在";

    /**
     * auth
     */
    public static final String AUTHORITY_PREFIX = "ROLE_";
    public static final String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * redis 缓存的角色信息的key
     */
    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";

    /**
     * redis缓存用户信息的key
     */
    public static final String USER_INFO = "USER_INFO";
    /**
     * 超管
     */
    public static final String ADMIN="admin";

    /**
     * 状态：有效
     */
    public static final String IS_VALID="1";

    /**
     * 存放组织列表
     */
    public static final String ORGANIZATIONS = "ORGANIZATIONS:ALL";

}
