package com.oket.micro.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oket.micro.auth.dao.RoleDao;
import com.oket.micro.auth.dto.RoleDto;
import com.oket.micro.auth.entity.SysRole;
import com.oket.micro.auth.service.RoleService;
import com.oket.micro.common.bean.UserDTO;
import com.oket.micro.common.constant.Constants;
import com.oket.micro.common.util.LoginUserHolder;
import com.oket.micro.common.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 角色
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, SysRole> implements RoleService {

    @Resource
    private RoleDao roleDao;
    @Autowired
    LoginUserHolder loginUserHolder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteRole(Integer roleId) {
        //先删除关联关系表
        roleDao.deleteRoleFunByRoleId(roleId);
        roleDao.deleteUserRoleByRoleId(roleId);
        int n=roleDao.deleteById(roleId);
	    return n > 0;
    }

    @Override
    public Map<String,Object> queryList(Map<String,Object> param) {
        List<RoleDto> roles=null;
        int totalCount=0;
        if(param.get("userId")!=null){
            //根据用户id查
            roles=roleDao.selectListByUserId(param);
        }else{
            //全查
            UserDTO user=loginUserHolder.getCurrentUser();
            if(user.getUsername().equals(Constants.ADMIN)){
                //超管查所用角色
                roles=roleDao.selectAllList(param);
                totalCount=roleDao.selectAllListCount(param);
            }else{
                //用户管理员查关联顶级组织下的
                param.put("userId",user.getId());
                roles=roleDao.selectListByUser(param);
                totalCount=roleDao.selectListByUserCount(param);
            }
        }
        param.put("totalCount",totalCount);
        param.put("list",roles);
        Map<String,Object> result= PageUtil.resultMap(param);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdateRole(SysRole role) {
        saveOrUpdate(role);
        if(role.getFunIdList()!=null && role.getFunIdList().size()>0){
            roleDao.deleteRoleFunByRoleId(role.getId());
            roleDao.saveRoleFun(role);

        }
    }

    @Override
    public List<RoleDto> queryListByOrg(Integer orgId) {
        List<RoleDto> list=roleDao.queryListByOrg(orgId);
        return list;
    }

}
