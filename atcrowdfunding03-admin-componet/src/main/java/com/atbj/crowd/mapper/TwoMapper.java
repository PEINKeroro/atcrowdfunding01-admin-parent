package com.atbj.crowd.mapper;

import com.atbj.crowd.vo.City;
import com.atbj.crowd.vo.Province;

import java.util.List;

public interface TwoMapper {


    List<Province> selectProvinceAll();

    List<City> selectCityByProId(Integer pid);
}
