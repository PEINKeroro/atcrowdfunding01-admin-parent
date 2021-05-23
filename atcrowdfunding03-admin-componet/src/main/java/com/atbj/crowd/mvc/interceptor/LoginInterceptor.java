package com.atbj.crowd.mvc.interceptor;

import com.atbj.crowd.domain.Admin;
import com.atbj.crowd.exception.AccessForbiddenException;
import com.atbj.crowd.util.CrowdConstantUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 1.获取session对象
        HttpSession session = request.getSession();
        // 2.尝试从session域中获取Admin对
        Admin admin = (Admin)session.getAttribute(CrowdConstantUtil.ATTR_NAME_LOGIN_ADMIN);
        // 3.如果admin为空就抛异常
        if (admin == null) {
            throw new AccessForbiddenException(CrowdConstantUtil.MESSAGE_ACCESS_FORBIDDEN);
        }
        // 4.放行
        return true;
    }
}
