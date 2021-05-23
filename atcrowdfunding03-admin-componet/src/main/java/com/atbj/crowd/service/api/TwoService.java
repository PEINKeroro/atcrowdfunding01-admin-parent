package com.atbj.crowd.service.api;

import com.atbj.crowd.vo.City;
import com.atbj.crowd.vo.Province;

import java.util.List;

public interface TwoService {

   List<Province> queryProvinces();

   List<City> queryCity(Integer pid);


}
