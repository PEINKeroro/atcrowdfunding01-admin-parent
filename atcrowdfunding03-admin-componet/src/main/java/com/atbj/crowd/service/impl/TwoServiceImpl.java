package com.atbj.crowd.service.impl;

import com.atbj.crowd.mapper.TwoMapper;
import com.atbj.crowd.service.api.TwoService;
import com.atbj.crowd.vo.City;
import com.atbj.crowd.vo.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwoServiceImpl implements TwoService {

    @Autowired
    TwoMapper twoMapper;

    @Override
    public List<Province> queryProvinces() {
        return twoMapper.selectProvinceAll();
    }

    @Override
    public List<City> queryCity(Integer pid) {
        return twoMapper.selectCityByProId(pid);
    }
}
