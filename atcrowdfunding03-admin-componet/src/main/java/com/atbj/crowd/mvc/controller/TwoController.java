package com.atbj.crowd.mvc.controller;


import com.atbj.crowd.service.api.TwoService;
import com.atbj.crowd.vo.City;
import com.atbj.crowd.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TwoController {

    @Autowired
    TwoService twoService;

    @RequestMapping("/two/province.do")
    @ResponseBody
    public List<Province> queryProvinces(){
        return twoService.queryProvinces();
    }

    @RequestMapping("/two/city.do")
    @ResponseBody
    public List<City> queryCity(@RequestParam("pid") Integer pid){
        return twoService.queryCity(pid);
    }


}
