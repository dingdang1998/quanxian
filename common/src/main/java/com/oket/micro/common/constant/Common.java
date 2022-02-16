package com.oket.micro.common.constant;

/**
 * Common
 *
 * @author wujh
 * @description 常量定义
 * @date 2021/07/28
 */
public interface Common {

	/**
	 * 缓存的key 常量
	 */
	interface Cache {

	}
	interface Error{
		String INTERNAL_ERROR="内部错误";

		String ORG_NO_PARENT="没有上级公司资源";
		String ORG_NO_MOVE="修改组织失败,不允许选择自己或者自己的下级作为上级组织";
		String ORG_UPD_FAIL="修改组织失败";
	}

}
