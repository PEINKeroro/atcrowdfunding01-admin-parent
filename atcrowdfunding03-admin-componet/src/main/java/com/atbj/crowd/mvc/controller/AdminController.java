package com.atbj.crowd.mvc.controller;

import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.domain.Role;
import com.atbj.crowd.service.api.AdminService;
import com.atbj.crowd.service.api.RoleService;
import com.atbj.crowd.util.CrowdConstantUtil;
import com.atbj.crowd.util.ResultEntityUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpSession;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/do/login.do")
    @ResponseBody
    public ResultEntityUtil<String> doLogin(@RequestParam("adminAcct") String loginAcct,
                                            @RequestParam("adminPswd") String loginPswd,
                                            HttpSession session) {
        // 1.调用Service方法执行登录检查
        // 这个方法如果能够返回admin对象说明登录成功，如果账号、密码不正确则会抛出异常
        Admin admin = adminService.getAdminByLoginAcct(loginAcct, loginPswd);
        // 2.将登录成功返回的admin对象存入Session域
        session.setAttribute(CrowdConstantUtil.ATTR_NAME_LOGIN_ADMIN, admin);
        // 3.返回 成功
        return ResultEntityUtil.successWithoutData();
    }

    @RequestMapping("admin/do/logout.do")
    public String doLogout(HttpSession session){
        //强制Session失效
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("/admin/get/page.do")
    public String getPageInfo(
        //使用@Reques tParam注解的defaultValue属性，指定對认值，在请求中没有携带对应参数时使用默认值
        // keyword默认值 使用空字符串，和SQL语句配合实现两种情况适配
        @RequestParam(value="keyword", defaultValue="") String keyword,
        // pageNum默认值使用1
        @RequestParam(value="pageNum", defaultValue="1") Integer pageNum,
        // pageSize默认值使用5
        @RequestParam(value="pageSize", defaultValue="5") Integer pageSize,
        ModelMap modelMap) {
        //调用Service方法获取PageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        //将PageInfo对象存入模
        modelMap.addAttribute(CrowdConstantUtil.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }
    @RequestMapping("/admin/do/remove.do")
    public String doRemove(@RequestParam("adminId") Integer adminId,
                              @RequestParam("pageNum") String pageNum,
                              @RequestParam("keyword") String keyword){

        // 执行删除操作
        adminService.removeAdmin(adminId);
        // 回到分页页面
        return "redirect:/admin/get/page.do?pageNum="+ pageNum +"&keyword="+ keyword;
    }

    @RequestMapping("/admin/do/save.do")
    @ResponseBody
    public ResultEntityUtil<String> doSave(Admin admin) {

        adminService.saveAdmin(admin);

        return ResultEntityUtil.success(CrowdConstantUtil.MESSAGE_USER_SAVE_SUCCESS);
    }

    @RequestMapping("/admin/do/update.do")
    @ResponseBody
    public ResultEntityUtil<String> doUpdate(Admin admin) {

        adminService.updateAdmin(admin);

        return ResultEntityUtil.success(CrowdConstantUtil.MESSAGE_USER_UPDATE_SUCCESS);
    }

}
