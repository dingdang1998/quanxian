package com.oket.micro.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oket.micro.auth.dao.PermissionDao;
import com.oket.micro.auth.dao.RoleDao;
import com.oket.micro.auth.dto.PermissionDto;
import com.oket.micro.auth.entity.SysPermission;
import com.oket.micro.auth.service.PermissionService;
import com.oket.micro.common.util.LoginUserHolder;
import com.oket.micro.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, SysPermission> implements PermissionService {
    @Resource
    private PermissionDao permissionDao;
    @Autowired(required = false)
    LoginUserHolder loginUserHolder;
    @Autowired(required = false)
    RoleDao roleDao;
    //菜单
    private static final int IS_MENU=0;
    //按钮
    private static final int IS_BUTTON=1;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdateFun(SysPermission sysPermission) {
        return saveOrUpdate(sysPermission);
    }

    @Override
    public List<SysPermission> getPermissionListByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        return permissionDao.selectListByUserId(map);
    }

    @Override
    public Map<String,Object> getTree(boolean isMenu, PermissionDto permissionDto) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",loginUserHolder.getCurrentUser().getId());
        Integer pageNum=null;
        Integer pageRow=null;
        int totalCount=0;
        if(permissionDto.getReqPageParamDto()!=null){
            pageNum=permissionDto.getReqPageParamDto().getPageNum();
            pageRow=permissionDto.getReqPageParamDto().getPageRow();
        }
        map.put("funName",permissionDto.getFunName());
        //List<SysPermission> permissionList=permissionDao.selectListByUserId(map);
        QueryWrapper<SysPermission> queryWrapper=new QueryWrapper<>();
        if(permissionDto.getFunName()!=null){
            queryWrapper.like("fun_name",permissionDto.getFunName());
        }
        Map<String,Object> queryParam=new HashMap<>();
        queryParam.put("status",permissionDto.getStatus());
        queryParam.put("isMenu",isMenu);
        /*if(permissionDto.getStatus()!=null){
            queryWrapper.like("status",permissionDto.getStatus());
        }*/
        List<SysPermission> permissionList=permissionDao.selectList(queryWrapper);
        List<SysPermission> list=buildTreeList(queryParam,permissionList);
        totalCount=list.size();
        /*if(isMenu){
            //只查询菜单
            permissionList=permissionList.stream().filter(p->p.getType()==IS_MENU).collect(Collectors.toList());
        }
        List<SysPermission> PList=permissionList.stream().filter(p->p.getParentId()==null).collect(Collectors.toList());
        permissionList=permissionList.stream().filter(p->p.getParentId()!=null).collect(Collectors.toList());
        List<SysPermission> list=buildTree(permissionList,PList);*/
        //分页
        if(pageNum!=null && pageRow!=null){
            list= PageUtil.startPage(list,pageNum,pageRow);
        }
        Map<String,Object> result=new HashMap<>();
        result.put("list",list);
        result.put("pageRow",pageRow);
        result.put("totalCount",totalCount);
        return result;
    }

    @Override
    public List<SysPermission> buildTreeList(Map<String,Object> queryParam,List<SysPermission> permissionList){
        if(Boolean.parseBoolean(queryParam.get("isMenu")+"")){
            //只查询菜单
            permissionList=permissionList.stream().filter(p->p.getType()==IS_MENU).collect(Collectors.toList());
        }
        if(queryParam.get("status")!=null){
            permissionList=permissionList.stream().filter(p->p.getStatus().equals(queryParam.get("status")+"")).collect(Collectors.toList());
        }
        List<SysPermission> PList=permissionList.stream().filter(p->p.getParentId()==null).collect(Collectors.toList());
        permissionList=permissionList.stream().filter(p->p.getParentId()!=null).collect(Collectors.toList());
        List<SysPermission> list=buildTree(permissionList,PList);
        return list;
    }

    @Override
    public List<SysPermission> getFunListByUserId(Integer userId) {
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        map.put("type",IS_BUTTON);
        return permissionDao.selectListByUserId(map);
    }

    /** 格式化成树
     * @param list
     * @param Plist
     * @return
    **/
    private List<SysPermission> buildTree(List<SysPermission> list, List<SysPermission> Plist){
        for(SysPermission Porg:Plist){
            Porg.setChildren(new ArrayList<>());
            for(int i=0;i<list.size();i++){
                SysPermission org=list.get(i);
                if(org.getParentId().equals(Porg.getId())){
                    Porg.getChildren().add(org);
                    list.remove(org);
                    i--;
                }
            }
            buildTree(list,Porg.getChildren());
        }
        return Plist;
    }
}
