package com.oket.micro.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oket.micro.auth.entity.Organization;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 公司组织dao层 hjt
 */
@Mapper
public interface OrganizationDao extends BaseMapper<Organization> {

    /**
     * 根据公司主键查询其下一级
     * @param
     * @return
     */
    List<Organization> getChildsByParentId(Map<String,Object> param);

    /**
     * 根据公司主键查询其下一级公司
     * @param parentCode
     * @return
     */
    @Select("select id companyCode,id id,org_name orgName,remark,parent_id parentCode,parent_id parentId,parent_ids parentCodes,type,status,address from sys_org where status=1 and type=0 and parent_id=#{parentCode} order by type DESC")
    List<Organization> getChildOrgsByParentId(String parentCode);

    /**
     * 根据公司主键查询其下一级公司
     * @param parentCode
     * @return
     */
    @Select("select id id from sys_org where status=1 and parent_id=#{parentCode}")
    List<String> getChildsIdByParentId(String parentCode);

    /**
     * 根据公司主键查询其下一级公司code
     * @param parentCode
     * @return
     */
    @Select("select id from sys_org where status=1 and type=0 and parent_id=#{parentCode} order by type DESC")
    List<String> getChildOrgIdsByParentId(String parentCode);


    /**
     * 根据主键组合查询公司（格式："001,002"）
     * @return
     */
    List<Organization> getOrganizationByIds(Map<String,Object> param);

    /**
     * 根据主键查询公司
     * @param
     * @return
     */
    @Select("select * from sys_org where id = #{id}")
    Organization getOrganizationByCode(Integer id);

    /**
     * 根据公司主键查询其所有有效下级公司
     * @param parentId
     * @return
     */
    @Select("select * from sys_org where status='1' and position ( concat ( ',',#{parentId}, ',' ) in parent_ids ) >0 order by type DESC")
    List<Organization> queryAllValidChilsOrganizationsByPid(String parentId);
    /**
     * 根据公司主键查询其所有下级公司
     * @param
     * @return
     */
    List<Organization> queryAllChilsOrganizationsByPid(Map<String,Object> param);

    /**
     * 查询其所有公司 返回类型json
     * @return
     */
    @Select("select id companyCode,id id,org_name orgName,remark,parent_id parentCode,parent_id parentId,parent_ids parentCodes,parent_ids parent_ids,type,status,address from sys_org where status='1' ")
    List<Map<String,Object>> queryAllOrganizations();


    /**
     * 获取当前最大主键
     * @return
     */
    @Select("select last_value from auth.sys_org_id_seq")
    int queryMaxId();
    /**
     * 条件查询
     * @param
     * @return
     */
  /*  List<Organization> queryAllChilsOrganizationsByPidAndName(Map<String,Object> params);

    *//**
     * 条件查询子公司
     * @param params
     * @return
     *//*
    List<Organization> getChildsByParentIdAndName(Map<String,Object> params);*/

    /**
     * 获取其下所有的公司(包括自己)
     * @param parentId
     * @return
     */
    @Select("select id from sys_org where status='1' and position ( concat ( ',',#{parentId}, ',' ) in parent_ids ) >0")
    List<Integer> queryAllCompanyIdsByPid(Integer parentId);

    /**
     * 模糊搜索
     * @param
     * @return
     */
    @Select("select id companyCode,id id,org_name orgName,remark,parent_id parentCode,parent_id parentId,parent_ids parentCodes,type,status,address from sys_org where status=1 and type=0 and org_name like concat('%',#{orgName},'%') order by type DESC")
    List<Organization> getOrgListNoStationByName(String orgName);

    /**
     * 查询用户所有绑定的公司
     * @param userId
     * @return
     */
    @Select("select org_id from sys_user_org where user_id=#{userId}")
    List<Integer> queryAllOrgIdsByUser(Integer userId);

    /**
     * 根据公司主键查询其下所有在用油站
     * @param parentCode
     * @return
     */
    @Select("select a.id id,a.org_name orgName,a.remark,a.parent_id parentCode,a.parent_id parentId,a.parent_ids parentCodes,a.type,a.status,a.address,b.code stationCode  " +
            " from sys_org a " +
            " join bd_station b on b.id=a.id " +
            " where b.status=1 and a.type=1 and locate(concat(',',#{parentCode},','),a.parent_ids) order by type DESC")
    List<Organization> queryAllNormalChilsStationByPid(String parentCode);
    /**
     * 根据公司主键查询其下所有油站
     * @param parentCode
     * @return
     */
    @Select("select a.id id,a.org_name orgName,a.remark,a.parent_id parentCode,a.parent_id parentId,a.parent_ids parentCodes,a.type,a.status,a.address,b.code stationCode  " +
            " from sys_org a " +
            " join bd_station b on b.id=a.id " +
            " where a.type=1 and locate(concat(',',#{parentCode},','),a.parent_ids) order by type DESC")
    List<Organization> queryAllChilsStationByPid(String parentCode);
    /**
     * 根据公司主键查询其下所有油站companyCode
     * @param parentCode
     * @return
     */
    @Select("select id companyCode from sys_org where status=1 and type=1 and locate(concat(',',#{parentCode},','),parent_ids)")
    List<String> queryAllChilsStationCompanyCodesByPid(String parentCode);

    /**
    * 修改
     * @param
     * @return
     */
    int updateOrg(Map<String,String> params);

    /**
     * 修改组织后修改其下所有子公司的组织
     * @param params
     * @return
     */
    @Select("update sys_org set parent_ids=CONCAT(#{newParentIds},SUBSTRING_INDEX(parent_ids,#{oldParentIds},-1)) where position ( concat ( ',',#{id}, ',' ) in parent_ids ) and id <> #{id}")
    void updateAllChildsParentIds(Map<String,Object> params);

    /**
    * 修改所有子公司的状态
     * @param
     * @return
     */
    void updateOrgsStatus(Map<String,Object> params);

    /**
     * 根据多个公司主键查询其下所有油站
     * @param parentCodes
     * @return
     */
  /*  List<String> queryAllChilStationsByPids(List<String> parentCodes);

    *//**
     * 获取指定油站的上一级组织（即片区）
     * @return
     *//*
    List<Map<String,Object>> getStationsParents(List<String> companyCodes);

    *//**
     * 查询该公司的所有上级parentCodes
     * @param companyCode
     * @return
     *//*
    @Select("select parent_ids parentCodes from sys_org where status=1 and id = #{companyCode}")
    String getParentCodes(String companyCode);

    *//**
     * 根据用户名获取对应的组织Id 及类型
     * @param username
     * @return
     *//*
    List<Map<String,Object>> getCompanyCodesByUsername(@Param("username") String username);

    *//**
     * 新增
     * @param
     * @return
     *//*
    int saveOrg(Map<String,Object> params);



    *//**
     * 删除
     * @param companyCode
     * @return
     *//*
    int deleteOrg(String companyCode);

    *//**
     * 获取当前最大主键
     * @return
     *//*
    @Select("select max(id) from sys_org")
    int queryMaxId();
    *//**
     * 获取下一个自增主键
     * @return
     *//*
    @Select("SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='iot_show' AND TABLE_NAME='sys_org'")
    int queryNextId();

    *//**
     * 修改组织后修改其下所有子公司的组织
     * @param params
     * @return
     *//*
    @Select("update sys_org set parent_ids=CONCAT(#{newParentIds},SUBSTRING_INDEX(parent_ids,#{oldParentIds},-1)) where locate(concat(',',#{id},','),parent_ids) and id <> #{id}")
    void updateAllChildsParentIds(Map<String,Object> params);



    *//**
     * 获取组织下绑定该用户的所有机构
     * @param username
     * @param parentCodes
     * @return
     *//*
    List<String> queryAllChildCompanyByPidsAndUsername(@Param("username") String username, @Param("parentCodes") List<String> parentCodes);
*/
}
