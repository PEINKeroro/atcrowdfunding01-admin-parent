package com.atbj.crowd.service.impl;

import com.atbj.crowd.domain.Role;
import com.atbj.crowd.domain.RoleExample;
import com.atbj.crowd.mapper.RoleMapper;
import com.atbj.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 1.开启分页功能
        PageHelper.startPage(pageNum, pageSize);
        // 2.执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        // 3.封装为PageInfo对象返回
        return new PageInfo(roleList);
    }

    @Override
    public void doSaveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void doUpdateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void doRemoveRole(List<Integer> roleIdList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        //delete from t_ role where id in (5,8,12)
        criteria.andIdIn(roleIdList);
        roleMapper.deleteByExample(example);
    }

    @Override
    public List<Role> getAssignedRole(Integer admin) {
        return roleMapper.selectAssignedRole(admin);
    }

    @Override
    public List<Role> getUnAssignedRole(Integer admin) {
        return roleMapper.selectUnAssignedRole(admin);
    }
}