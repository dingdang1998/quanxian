package com.oket.micro.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.oket.micro.auth.entity.Organization;
import com.oket.micro.common.exception.BusiException;

import java.util.List;
import java.util.Map;

public interface OrganizationService extends IService<Organization> {
	/**
	 * 组织列表
	 *
	 * @return
	 */
	Map<String,Object> list(Organization params);

	/**
	 * 增加公司
	 * @param params
	 * @return
	 */
	void saveOrganization(Organization params);

	/**
	 * 修改公司
	 * @param params
	 * @return
	 */
	void updateOrg(Organization params) throws BusiException;

	/**
	 * 根据公司名或公司编码查询公司是否存在
	 * @param params
	 * @return
	 */
	boolean queryExistOrg(Organization params);

	/**
	 * 修改公司状态
	 * @param params
	 * @return
	 */
    Map<String,Object> updateStatus(Organization params);

	/**
	 * 下拉列表查询公司
	 * @return
	 */
    List<Map<String,Object>> selectList();

	/**
	 * 通过用户获取公司
	 * @param user
	 * @return
	 */
    List<Organization> getOrgByUser(Map<String,Object> user);

	/**
	 * 获取当前登录人所绑定的组织树
	 * @param
	 * @return
	 */
	List<Organization> getCurrentUserTree( Organization org);

	/**
	 * 针对一个人绑定多家公司
	 * @param params
	 * @return
	 */
	List<Organization> getOrganizationTreeFromMany(List<String> params,Organization org);

	/**
	 * 获取顶级组织列表
	 * @return
	 */
	List<Organization> getAllTopList();

	/**
	 * 当前用户组织过滤（业务数据）
	 * @param orgIds
	 * @return
	 */
	List<Integer> orgFilter(String orgIds);

	/**
	 * 根据userId获取其权限范围下所有的公司companyCode
	 * @param userId
	 * @return
	 */
	List<Integer> getAllCompanyIds(Integer userId);
}
