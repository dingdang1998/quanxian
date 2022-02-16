package com.oket.micro.common.exception;


/**
 * @author ysh
 * @description: 本系统使用的自定义错误类
 */
public class BusiException extends RuntimeException {
    public BusiException(String message) {
        super(message);
    }

    public BusiException(String message, Throwable cause) {
        super(message, cause);
    }
}
