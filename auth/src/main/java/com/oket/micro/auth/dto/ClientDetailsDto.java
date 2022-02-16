package com.oket.micro.auth.dto;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Author: czf
 * @Description: 关于客户端的相关信息 这个类可以根据自己需求来实现，但是已经有框架实现好的类了 {@BaseClientDetails}
 * @Date: 2021-07-23 10:50
 * @Version: 1.0
 **/
@Setter
public class ClientDetailsDto implements ClientDetails {

	/**
	 * 客户端Id
	 */
	private String clientId;
	/**
	 * 此客户端可以访问的资源，如果为空，调用者可以忽略
	 */
	private Set<String> resourceIds;
	/**
	 * 客户端做认证是否需要机密
	 */
	private boolean secretRequired;
	/**
	 * 客户端机密，如果不是必须，可以忽略
	 */
	private String clientSecret;
	/**
	 * 此客户端是否仅限于特定范围。如果为 false，则身份验证请求的范围将被忽略
	 */
	private boolean isScoped;

	/**
	 * 客户端被限定的范围
	 */
	private Set<String> scopes;

	/**
	 * 此客户端被授权的授权类型。
	 */
	private Set<String> authorizedGrantTypes;

	/**
	 * 此客户端在“authorization_code”访问授权期间使用的预定义重定向 URI。请参阅 OAuth 规范，第 4.1.1 节。
	 */
	private Set<String> registeredRedirectUri;

	/**
	 * 返回授予 OAuth 客户端的权限。无法返回 <code>null<code>。请注意，这些不是授予具有授权访问令牌的用户的权限。相反，这些权限是客户端本身固有的。
	 */
	private Collection<GrantedAuthority> authorities;

	/**
	 * 此客户端的访问令牌有效期。如果未明确设置，则为 Null（例如，实现可能使用该事实来提供默认值）
	 */
	private Integer accessTokenValiditySeconds;

	/**
	 * 此客户端的刷新令牌有效期。令牌服务设置的默认值为空，未过期令牌为零或负值。
	 */
	private Integer refreshTokenValiditySeconds;


	@Override
	public String getClientId() {
		return this.clientId;
	}

	@Override
	public Set<String> getResourceIds() {
		return this.resourceIds;
	}

	@Override
	public boolean isSecretRequired() {
		return this.secretRequired;
	}

	@Override
	public String getClientSecret() {
		return this.clientSecret;
	}

	@Override
	public boolean isScoped() {
		return this.isScoped;
	}

	@Override
	public Set<String> getScope() {
		return this.scopes;
	}

	@Override
	public Set<String> getAuthorizedGrantTypes() {
		return this.authorizedGrantTypes;
	}

	@Override
	public Set<String> getRegisteredRedirectUri() {
		return this.registeredRedirectUri;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public Integer getAccessTokenValiditySeconds() {
		return this.accessTokenValiditySeconds;
	}

	@Override
	public Integer getRefreshTokenValiditySeconds() {
		return this.refreshTokenValiditySeconds;
	}

	/**
	 * Test whether client needs user approval for a particular scope.
	 * 测试客户端是否需要用户来审批特定的范围
	 *
	 * @param scope
	 * @return
	 */
	@Override
	public boolean isAutoApprove(String scope) {
		return false;
	}

	/**
	 * 客户端的额外信息
	 *
	 * @return
	 */
	@Override
	public Map<String, Object> getAdditionalInformation() {
		return null;
	}
}
