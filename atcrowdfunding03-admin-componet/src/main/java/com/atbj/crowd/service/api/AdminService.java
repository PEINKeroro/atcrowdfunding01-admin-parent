package com.atbj.crowd.service.api;

import com.atbj.crowd.domain.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String loginPwd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void removeAdmin(Integer adminId);

    void updateAdmin(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
