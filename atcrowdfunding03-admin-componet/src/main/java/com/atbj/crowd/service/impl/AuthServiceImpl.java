package com.atbj.crowd.service.impl;

import com.atbj.crowd.domain.Auth;
import com.atbj.crowd.domain.AuthExample;
import com.atbj.crowd.mapper.AuthMapper;
import com.atbj.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Transactional
    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        // 1.获取roleId的值
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 2.删除旧关联关系数据
        authMapper.deleteRelationship(roleId);
        // 3. 获取authIdList
        List<Integer> authIdList = map.get("authIdArray");
        // 4.判断authIdList是否有效
        if(authIdList != null && authIdList.size() > 0) {
            authMapper.insertRelationship(roleId, authIdList);
        }
    }


}
