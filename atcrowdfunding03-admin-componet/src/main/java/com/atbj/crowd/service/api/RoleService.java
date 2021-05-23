package com.atbj.crowd.service.api;

import com.atbj.crowd.domain.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    void doSaveRole(Role role);

    void doUpdateRole(Role role);

    void doRemoveRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer admin);

    List<Role> getUnAssignedRole(Integer admin);
}
