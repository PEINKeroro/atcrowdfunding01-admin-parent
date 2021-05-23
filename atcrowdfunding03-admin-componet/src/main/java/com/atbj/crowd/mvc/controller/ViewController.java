package com.atbj.crowd.mvc.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;

@Controller
public class ViewController {

    @RequestMapping("/admin/to/login/page.html")
    public String toLogin(){
        return "admin-login";
    }

    @RequestMapping("/admin/to/main/page.html")
    public String toLogout(){
        return "admin-main";
    }

    @RequestMapping("/admin/to/add/page.html")
    public String toAdd(){
        return "admin-add";
    }

    @RequestMapping("/role/to/page.html")
    public String toRole(){
        return "role-page";
    }

    @RequestMapping("/menu/to/page.html")
    public String toMenu() {
        return "menu-page";
    }

}
