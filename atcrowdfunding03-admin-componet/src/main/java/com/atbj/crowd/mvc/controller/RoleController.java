package com.atbj.crowd.mvc.controller;

import com.atbj.crowd.domain.Role;
import com.atbj.crowd.service.api.RoleService;
import com.atbj.crowd.util.CrowdConstantUtil;
import com.atbj.crowd.util.ResultEntityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("role/get/page/Info.do")
    @ResponseBody
    public ResultEntityUtil<PageInfo<Role>> getPageInfo(
            @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
            @RequestParam(value="keyword", defaultValue="") String keyword){

        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        return ResultEntityUtil.success(pageInfo);
    }

    @RequestMapping("role/do/save.do")
    @ResponseBody
    public ResultEntityUtil<String> doSaveRole(Role role){

        roleService.doSaveRole(role);

        return ResultEntityUtil.success(CrowdConstantUtil.MESSAGE_ROLE_SAVE_SUCCESS);
    }

    @RequestMapping("role/do/update.do")
    @ResponseBody
    public ResultEntityUtil<String> doUpdateRole(Role role){

        roleService.doUpdateRole(role);

        return ResultEntityUtil.success(CrowdConstantUtil.MESSAGE_ROLE_UPDATE_SUCCESS);
    }

    @RequestMapping("role/do/remove.do")
    @ResponseBody
    public ResultEntityUtil<String> doRemoveRole(@RequestBody List<Integer> roleIdList){

        roleService.doRemoveRole(roleIdList);

        return ResultEntityUtil.success(CrowdConstantUtil.MESSAGE_ROLE_SELECT_SUCCESS);
    }

}
