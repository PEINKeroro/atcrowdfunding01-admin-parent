package com.atbj.crowd.mvc.controller;

import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestCtroller {

    @Autowired
    AdminService adminService;

    @RequestMapping("/send/array.do")
    @ResponseBody
    public void testJsp() {
        System.out.println("2222222222222222222222222222222222222222222222");
        int a =  10/0;
        System.out.println("???????????????????????????????");
    }

    @RequestMapping("/login.do")
    public String toLogin(){
        return "admin-login";
    }



//    @RequestMapping("/send/array.do")
//    @ResponseBody
//    public void testReceiveArrayTwo(@RequestBody List<Integer> array) {
////        Logger logger = LoggerFactory.getLogger(TestCtroller.class);
////        for (Integer number : array) {
////            logger.info("number=" + number);
////        }
//      int a =  10/0;
//        System.out.println(a);
//
//    }


//    @RequestMapping(value = "/send/array.do", produces = "text/plain;charset=utf-8")
//    public String testReceiveArray0ne(@RequestParam("array[]") List<Integer> array,
//                                      @RequestParam("aaa")String bbb) {
//        for (Integer number : array) {
//            System.out.println("number=" + number);
//        }
//        return "target";
//    }

    @RequestMapping("/test/ssm.html")
    public String testssm(ModelMap modelMap){
       List<Admin> adminList =  adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
       return "target";
    }
}
