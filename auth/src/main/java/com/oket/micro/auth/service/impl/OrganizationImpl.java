package com.oket.micro.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oket.micro.auth.dao.OrganizationDao;
import com.oket.micro.auth.entity.Organization;
import com.oket.micro.auth.service.OrganizationService;
import com.oket.micro.common.bean.UserDTO;
import com.oket.micro.common.constant.Common;
import com.oket.micro.common.constant.Constants;
import com.oket.micro.common.exception.BusiException;
import com.oket.micro.common.util.LoginUserHolder;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrganizationImpl extends ServiceImpl<OrganizationDao,Organization> implements OrganizationService {

    @Autowired(required = false)
    OrganizationDao organizationDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired(required = false)
    LoginUserHolder loginUserHolder;

    //顶级公司id
    private static final String MAIN_ORGID="0";
    //油站类型
    private static final String STATION_TYPE="1";

    /**
     * 只有一个顶级的情况下
     * @param params
     * @return
     */
    public List<Organization> getOrganizationTreeFromOne(String params){
        Map<String,Object> map=new HashMap<>();
        map.put("parentId",params);
        List<Organization> list=organizationDao.queryAllChilsOrganizationsByPid(map);
        map.put("ids",params);
        return getOrganizationChilds(list,organizationDao.getOrganizationByIds(map));
    }

    /**
     * 针对一个人绑定多家公司
     * @param params
     * @return
     */
    @Override
    public List<Organization> getOrganizationTreeFromMany(List<String> params,Organization org){
        List<Organization> list2=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        if(org!=null){
            map.put("status",org.getStatus());
        }
        if(params!=null && params.contains(MAIN_ORGID)){
            //包含顶级权限,查所有
            map.put("parentId",MAIN_ORGID);
            List<Organization> list=organizationDao.queryAllChilsOrganizationsByPid(map);
            list2.addAll(getOrganizationChilds(list,organizationDao.getChildsByParentId(map)));
            return list2;
        }
        for(String orgId:params){
            map.put("parentId",orgId);
            List<Organization> list=organizationDao.queryAllChilsOrganizationsByPid(map);
            map.put("ids",orgId);
            list2.addAll(getOrganizationChilds(list,organizationDao.getOrganizationByIds(map)));
        }
        return list2;
    }

    @Override
    public List<Organization> getAllTopList() {
        QueryWrapper<Organization> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",MAIN_ORGID);
        return organizationDao.selectList(queryWrapper);
    }


    @Override
    public List<Integer> orgFilter(String orgIds) {
        List<Integer> orgIdList=new ArrayList<>();
        //前端查询组织传参
        if(StringUtils.isNotEmpty(orgIds)){
            List<String> queryParamOrgIds = Arrays.asList(orgIds.split(","));
            List<Integer> orgs=queryParamOrgIds.stream().map(id->Integer.parseInt(id)).collect(Collectors.toList());
            for(Integer id:orgs){
                orgIdList.addAll(organizationDao.queryAllCompanyIdsByPid(id));
            }
        }
        UserDTO user=loginUserHolder.getCurrentUser();
        //当前用户最大组织范围
        List<Integer> allOrgIds = (List<Integer>) redisTemplate.opsForHash().get(Constants.USER_INFO+"_"+user.getUsername(),"allOrgIds");
        if (orgIdList != null && orgIdList.size() > 0) {
            if(allOrgIds!=null){
                //求交集
                orgIdList.retainAll(allOrgIds);
            }
        } else {
            orgIdList=allOrgIds;
        }
        return orgIdList;
    }

    /**
     * 获取当前登录人所绑定的组织树
     * @return
     */
    @Override
    public List<Organization> getCurrentUserTree(Organization org){
        UserDTO user=loginUserHolder.getCurrentUser();
        //当前用户最大组织范围
        List<String> companyCodes = user.getOrgs();
        if(org!=null && StringUtils.isNotBlank(org.getOrgName())){
            QueryWrapper<Organization> queryWrapper=new QueryWrapper<>();
            queryWrapper.like("org_name","%"+org.getOrgName()+"%");
            List<Organization> list=organizationDao.selectList(queryWrapper);
            companyCodes=list.stream().map(organization->{
                return organization.getId()+"";
            }).collect(Collectors.toList());
        }
        return getOrganizationTreeFromMany(companyCodes,org);

    }

    /**
     * 模糊搜索
     * @return
     */
    public List<Organization> getOrgListNoStationByName(String companyName){
        return organizationDao.getOrgListNoStationByName(companyName);
    }

    @Override
    public List<Integer> getAllCompanyIds(Integer userId){
        List<Integer> allCompanyCodes=new ArrayList<>();
        List<Integer> orgIds=organizationDao.queryAllOrgIdsByUser(userId);
        for(Integer orgId:orgIds){
            List<Integer> list=organizationDao.queryAllCompanyIdsByPid(orgId);
            allCompanyCodes.addAll(list);
        }
        return allCompanyCodes;
    }

    /**
     * 格式化成树
     * @param list
     * @param Plist
     * @return
     */
    private List<Organization> getOrganizationChilds(List<Organization> list, List<Organization> Plist){
        for(Organization Porg:Plist){
            Porg.setChildren(new ArrayList<>());
            for(int i=0;i<list.size();i++){
                Organization org=list.get(i);
                if(org.getParentId().equals(Porg.getId()+"")){
                    Porg.getChildren().add(org);
                    list.remove(org);
                    i--;
                }
            }
            getOrganizationChilds(list,Porg.getChildren());
        }
        return Plist;
    }

    /**
     * 查询该公司的所有上级parentCodes(0,244,245,249)
     * @return
     */
   /* public String getParentCodes(String param){
        String parentCodes = organizationDao.getParentCodes(param);
        if(StringUtils.isBlank(parentCodes)) throw new CommonMapException(ErrorEnum.E_40002);
        if(parentCodes.startsWith(",")) parentCodes=parentCodes.substring(1);
        if(parentCodes.endsWith(",")) parentCodes=parentCodes.substring(0,parentCodes.length()-1);
        return parentCodes;
    }*/

   /* *
     * 新增
     * @param map
     * @return
     */
    /*public Map<String,Object> saveOrg(Map<String,Object> map){
        int n=saveOrganization(map);
        if(n>0){
            return CommonUtil.successResult();
        }else{
            log.error("保存组织失败");
            return CommonUtil.failJson();
        }
    }*/

    /**
     * 新增方法实现
     * @return
     */
    @Synchronized
    @Override
    public void saveOrganization(Organization org){
        String parentId=org.getParentId()+"";
        int companyId=organizationDao.queryMaxId()+1;
        //新增子级
        if(StringUtils.isNotBlank(parentId)){
            String parentIds=null;
            Map<String,Object> map=new HashMap<>();
            map.put("ids",parentId);
            List<Organization> parantOrgs=organizationDao.getOrganizationByIds(map);
            if(parantOrgs!=null && parantOrgs.size()>0){
                Organization parentOrg=parantOrgs.get(0);
                parentIds=parentOrg.getParentIds()+companyId+",";
            }else{
                throw new BusiException(Common.Error.ORG_NO_PARENT);
            }

            org.setParentIds(parentIds);
        }else{
            //新增顶级
            org.setParentId(MAIN_ORGID);
            org.setParentIds(",0,"+companyId+",");
        }
        organizationDao.insert(org);
    }

    /**
     * 修改
     * @param params
     * @return
     */
    @Override
    public void updateOrg(Organization params) throws BusiException{
        //若修改其上级组织，不能改为在自己的下级或者自己之下
        List<Integer> childIds=organizationDao.queryAllCompanyIdsByPid(params.getId());
        if(childIds.contains(Integer.parseInt(params.getParentId()))){
            throw new BusiException(Common.Error.ORG_NO_MOVE);
        }
        int n=updateOrganization(params);
        if(n==0){
            throw new BusiException(Common.Error.ORG_UPD_FAIL);
        }
    }
    /**
     * 修改方法实现
     * @param
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int updateOrganization(Organization newOrg){
        int n=0;
        boolean b=false;
        Organization org=organizationDao.selectById(newOrg.getId());
        Map<String,Object> map=new HashMap<>();
        map.put("id",newOrg.getId());
        if(StringUtils.isNotBlank(newOrg.getParentId())){
            //改变上级
            if(org!=null && !org.getParentId().equals(newOrg.getParentId())){
                String oldParentIds = org.getParentIds().substring(0,org.getParentIds().lastIndexOf(org.getId()));
                //修改上级（查出parentId对应的rank+companyId+","）
                //查出parentId对应的rank
                //得到修改后的父rank
                if(!(newOrg.getParentId()).equals(MAIN_ORGID)){
                    String newParentIds =   organizationDao.selectById(newOrg.getParentId()).getParentIds() ;
                    map.put("newParentIds",newParentIds);
                }else{
                    //直接修改为顶级组织
                    map.put("newParentIds",",0,");
                }

                map.put("oldParentIds",oldParentIds);
                //修改父组织标识
                b=true;
                Organization parentOrg=organizationDao.selectById(newOrg.getParentId());
                if(parentOrg!=null){
                    //map.put("parentIds",parentOrg.getParentIds()+map.get("id")+",");
                    newOrg.setParentIds(parentOrg.getParentIds()+newOrg.getId()+",");
                }else{
                    //map.put("parentIds",",0,"+map.get("id")+",");
                    newOrg.setParentIds(",0,"+newOrg.getId()+",");
                }
            }
        }
        n=organizationDao.updateById(newOrg);
        if(StringUtils.isNotBlank(newOrg.getStatus()) && !org.getStatus().equals(newOrg.getStatus())){
            //修改状态，将其子公司都改一遍
            map.put("status",newOrg.getStatus());
            organizationDao.updateOrgsStatus(map);
        }
        if(b){
            updateOrganzationRank(map);
        }
        //redisDataUtil.refreshSubOrganization();
        return n;
    }

    /**
     * 修改所有子公司的rank
     * @param map
     * @return
     */
    public int updateOrganzationRank(Map<String,Object> map){
        int i=0;
        //1、修改父组织
        //若传来的parentId不为空
        if(StringUtils.isNotBlank(map.get("parentId")+"")){
            //查companyId对应的全部信息
            organizationDao.updateAllChildsParentIds(map);
            i=1;
        }
        return i;
    }


    @Override
    public Map<String, Object> list(Organization params) {
        return null;
    }


    @Override
    public boolean queryExistOrg(Organization org) {
        QueryWrapper<Organization> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("org_name","%"+org.getOrgName()+"%");
        List<Organization> list=organizationDao.selectList(queryWrapper);
        return list != null && list.size() > 0;
    }

    @Override
    public Map<String, Object> updateStatus(Organization params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> selectList() {

        return organizationDao.queryAllOrganizations();
    }

    @Override
    public List<Organization> getOrgByUser(Map<String, Object> user) {
        return null;
    }
}
