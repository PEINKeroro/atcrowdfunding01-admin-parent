package com.atbj.crowd.mvc.controller;

import com.atbj.crowd.domain.Auth;
import com.atbj.crowd.domain.Role;
import com.atbj.crowd.service.api.AdminService;
import com.atbj.crowd.service.api.AuthService;
import com.atbj.crowd.service.api.RoleService;
import com.atbj.crowd.util.ResultEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignController {

    @Autowired
    AdminService adminService;

    @Autowired
    RoleService roleService;

    @Autowired
    AuthService authService;

    @RequestMapping("/assign/to/assign/role/page.do")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId,
                                   ModelMap modelMap){

        // 1.查询分配与未分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        List<Role> unAssignedRoleList = roleService.getUnAssignedRole(adminId);

        // 2.分别存放进请求域
        modelMap.addAttribute("assignedRoleList", assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList", unAssignedRoleList);

        // 3.页面跳转到
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.do")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            // 参数可以为空
            @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList){

        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        // 重定向到admin页面
        return "redirect:/admin/get/page.do";
    }


    @RequestMapping("/assgin/get/all/auth.do")
    @ResponseBody
    public ResultEntityUtil<List<Auth>> getAllAuth() {
        List<Auth> authList = authService.getAll();
        return ResultEntityUtil.success(authList);
    }

    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.do")
    public ResultEntityUtil<List<Integer>> getAssignedAuthIdByRoleId(
            @RequestParam("roleId") Integer roleId) {
        List<Integer> authIdList = authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntityUtil.success(authIdList);
    }

    @RequestMapping("assign/do/role/assign/auth.do")
    @ResponseBody
    public ResultEntityUtil saveRoleAuthRelationship(
            @RequestBody Map<String, List<Integer>> map){

        authService.saveRoleAuthRelationship(map);

        return ResultEntityUtil.success(null);
    }


}
