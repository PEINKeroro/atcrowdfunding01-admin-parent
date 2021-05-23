package com.atbj.crowd.service.impl;

import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.domain.AdminExample;
import com.atbj.crowd.exception.AccountAdditionException;
import com.atbj.crowd.exception.LoginFailedException;
import com.atbj.crowd.mapper.AdminMapper;
import com.atbj.crowd.service.api.AdminService;
import com.atbj.crowd.util.CrowdConstantUtil;
import com.atbj.crowd.util.MD5CodeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public void saveAdmin(Admin admin) {
        // 1.密码加密
        String userPwsd = admin.getUserPswd();
        userPwsd = MD5CodeUtil.toMD5(userPwsd);
        admin.setUserPswd(userPwsd);
        // 2.生成创建时间
        String createTime = null;
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        createTime = format.format(date);
        admin.setCreateTime(createTime);

        // 3.判断实体类的所有属性是否为null或者空串
        String loginAcct = admin.getLoginAcct();
        String email = admin.getEmail();
        if (loginAcct == null || email == null || userPwsd == null || createTime == null ||
                loginAcct.length() == 0 || email.length() == 0 ||
                userPwsd.length() == 0 || createTime.length() == 0)
            throw new AccountAdditionException(CrowdConstantUtil.MESSAGE_STRING_INVALIDATE);

        // 4.捕获账号重复异常
        try{
            adminMapper.insert(admin);
            //会抛出org.springframework.dao.DuplicateKeyException
        } catch (Exception e){
            if (e instanceof DuplicateKeyException)
                throw new AccountAdditionException(CrowdConstantUtil.MESSAGE_ADD_REPEAT);
        }
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    public Admin getAdminByLoginAcct(String loginAcct, String loginPswd) {
        // 1.根据登录账号查询Admin对象
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
            // 在Criteria对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAcct);
            // 调用AdminMapper的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);
        // 2.判断查询数据是不是空
            // 判断list
        if (list == null || list.size() == 0)
            throw new LoginFailedException(CrowdConstantUtil.MESSAGE_LOGIN_FAILED);
        if (list.size() < 1)
            throw new RuntimeException(CrowdConstantUtil.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            // 判断admin
        Admin admin = list.get(0);
        if (admin == null)
            throw new LoginFailedException(CrowdConstantUtil.MESSAGE_LOGIN_FAILED);

        // 3.数据库与页面密码比对
            // 获取数据库md5密码
        String adminPswdDB = admin.getUserPswd();
            // 获取表单md5密码
        String adminPswdFrom = MD5CodeUtil.toMD5(loginPswd);
            // 判断密码是否相等
        if (!Objects.equals(adminPswdDB,adminPswdFrom))
            throw new LoginFailedException(CrowdConstantUtil.MESSAGE_LOGIN_FAILED);
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1. 调用PageHelper的静态方法开启分页功能
        //这里充分体现了PageHelper的“非侵入式”设计:原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum,pageSize);
        // 2.执行查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);
        // 3.封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void removeAdmin(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public void updateAdmin(Admin admin) {

        // 如果用户名重复则报错提示
        try{
            // "Selective"表示有选择的更新，对于null值的字段不更新
            adminMapper.updateByPrimaryKeySelective(admin);
            //会抛出org.springframework.dao.DuplicateKeyException
        } catch (Exception e){
            if (e instanceof DuplicateKeyException)
                throw new AccountAdditionException(CrowdConstantUtil.MESSAGE_ADD_REPEAT);
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 1.根据adminId删除旧的关联关系数据
        adminMapper.deleteRelationship(adminId);
        // 2.根据roleIdList和adminId保存新的关联关系
        if (roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertRelationship(adminId, roleIdList);
        }
    }

}
