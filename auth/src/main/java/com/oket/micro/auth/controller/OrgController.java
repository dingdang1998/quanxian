package com.oket.micro.auth.controller;

import com.oket.micro.auth.entity.Organization;
import com.oket.micro.auth.service.OrganizationService;
import com.oket.micro.common.constant.Common;
import com.oket.micro.common.exception.BusiException;
import com.oket.micro.common.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program:
 * @description: 组织
 **/
@Api(value = "组织",tags = "组织接口")
@RestController
@RequestMapping(value = "/company")
@Slf4j
public class OrgController {
    @Resource
    private OrganizationService organizationService;

    //状态：有效
    private static final String VALID="1";

    @ApiOperation(value = "组织列表查询",notes = "")
    @PostMapping("/list")
    public Result<List<Organization>> list(@RequestBody Organization organization) {
        return Result.success(organizationService.getCurrentUserTree(organization));
    }

    @ApiOperation(value = "查询组织是否存在",notes = "")
    @PostMapping(value = "/exists",produces = "application/json")
    public Result<Boolean> queryExistOrg(@RequestBody Organization organization) {
        /*if  (organization.get("companyCode")==null){
            CommonUtil.hasAllRequired(organization, "companyName");
        }else {
            CommonUtil.hasAllRequired(organization, "companyCode");
        }*/
        boolean isHave=organizationService.queryExistOrg(organization);
        return Result.success(isHave);
    }

    /*@ApiOperation(value = "修改状态")
    @PutMapping("/status")
    public Result<String> changeStatus(@RequestBody Map<String,Object> organization) {
        CommonUtil.hasAllRequired(organization, "companyId,status");
        return organizationService.updateStatus(organization);
    }

    @ApiOperation(value = "当前登录人所绑定的组织树")
    @GetMapping("/currentUserTree")
    public JSONObject currentUserTree() {
        JSONObject jsonObject=new JSONObject();
        //只查有效的组织
        jsonObject.put("status",VALID);
        return CommonUtil.successJson(organizationTree.getGurrentUserTree(true,jsonObject));
    }

    @ApiOperation(value = "当前登录人所绑定的组织树(不含油站)-hjt")
    @GetMapping("/currentUserTreeNoStation")
    public JSONObject currentUserTreeNoStation(HttpServletRequest request) {
        return CommonUtil.successJson(organizationTree.getGurrentUserTree(false, CommonUtil.request2Json(request)));
    }

    @ApiOperation(value = "查询该公司的所有上级companyCodes(0,244,245,249)-hjt")
    @GetMapping("/getParentCodes")
    public JSONObject getParentCodes(@RequestParam String companyCode){
        String parentCodes=organizationTree.getParentCodes(companyCode).substring(0,organizationTree.getParentCodes(companyCode).lastIndexOf(","));
        return CommonUtil.successJson(parentCodes);
    }*/

    @ApiOperation(value = "增加组织", notes = "需要传入选择的上级组织的parentId(不传为顶级公司)")
    @PostMapping(value = "/save",produces = "application/json")
    public Result<String> save(@RequestBody Organization org) {
        try {
            organizationService.saveOrganization(org);
            return Result.success("");
        }catch (Exception e){
            log.error("OrgController,save",e);
            throw new BusiException(Common.Error.INTERNAL_ERROR);
        }
    }

    @ApiOperation(value = "更新公司信息", notes = "")
    @PutMapping(value = "/update",produces = "application/json")
    public Result<String> update(@RequestBody Organization org) throws Exception {
        organizationService.updateOrg(org);
        return Result.success("");
    }

    @ApiOperation(value="获取顶级组织列表",notes = "")
    @GetMapping(value = "/topList")
    public Result<List<Organization>> getAllTopList(){
        return Result.success(organizationService.getAllTopList());
    }

    @ApiOperation(value="限制组织列表",notes = "")
    @GetMapping(value = "/internal/orgFilter")
    public List<Integer> orgFilter(@RequestParam(required = false) String orgIds){
        return organizationService.orgFilter(orgIds);
    }

}
