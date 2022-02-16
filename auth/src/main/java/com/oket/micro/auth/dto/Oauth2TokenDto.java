package com.oket.micro.auth.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: czf
 * @Description:
 * @Date: 2021-07-16 14:06
 * @Version: 1.0
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Oauth2TokenDto {


	/**
	 * 访问令牌
	 */
	private String token;
	/**
	 * 刷新令牌
	 */
	private String refreshToken;
	/**
	 * 访问令牌头前缀
	 */
	private String tokenHead;
	/**
	 * 有效时间（秒）
	 */
	private int expiresIn;


}
