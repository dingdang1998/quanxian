package com.oket.micro.common.util;

/**
 * @Author: czf
 * @Date: 2021-07-19 10:14
 * @Description: 结果枚举
 * @Version: 1.0
 **/
public enum ResultCode {
	SUCCESS(200, "操作成功"),
	FAILED(500, "操作失败"),
	VALIDATE_FAILED(404, "参数检验失败"),
	UNAUTHORIZED(401, "暂未登录或token已经过期"),
	FORBIDDEN(403, "没有相关权限");
	private final long code;
	private final String message;

	ResultCode(long code, String message) {
		this.code = code;
		this.message = message;
	}

	public long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
