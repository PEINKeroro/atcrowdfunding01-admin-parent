package com.atbj.crowd.mvc.config;

import com.atbj.crowd.exception.AccessForbiddenException;
import com.atbj.crowd.exception.AccountAdditionException;
import com.atbj.crowd.exception.LoginFailedException;
import com.atbj.crowd.util.CrowdConstantUtil;
import com.atbj.crowd.util.ResultEntityUtil;
import com.atbj.crowd.util.JudgeRequestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


//注解声明异常处理器类
@ControllerAdvice
public class ExceptionResolver {
    //将一个异常类型和一个方法关联起来
    @ExceptionHandler(LoginFailedException.class)
    @ResponseBody
    public Object loginFailedExceptionHandler(Exception exception, HttpServletRequest request) {
        return commonResolve(null, exception, request);
    }

    @ExceptionHandler(AccessForbiddenException.class)
    @ResponseBody
    public Object accessForbiddenExceptionHandler(
            AccessForbiddenException exception, HttpServletRequest request) {
        String viewName = "/tips";
        return commonResolve(viewName, exception, request);
    }

    @ExceptionHandler(AccountAdditionException.class)
    @ResponseBody
    public Object accountAdditionExceptionHandler(
            AccountAdditionException exception, HttpServletRequest request) {
        return commonResolve(null, exception, request);
    }

    private Object commonResolve(String viewName, Exception exception,
                                 HttpServletRequest request) {
        // 1.判断当前请求类型
        boolean judgeResult = JudgeRequestUtil.judgeRequestType(request);
        // 2.如果是Ajax请求
        if(judgeResult) {
            // 3.创建ResultEntity对象
            ResultEntityUtil resultEntity = ResultEntityUtil.failed(exception.getMessage());
            // 4.返回json格式数据
            return resultEntity;
        }
        // 5.如果不是Ajax请求,则创建ModelAndView对象
        ModelAndView mv = new ModelAndView();
        mv.addObject(CrowdConstantUtil.ATTR_NAME_EXCEPTION, exception);
        mv.setViewName(viewName);
        return mv;
    }


    //拦截其他未知异常
    @ExceptionHandler
    public void dosome(Exception exception,HttpServletRequest request,HttpServletResponse response){
        exception.printStackTrace();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ModelAndView mv = new ModelAndView();

    }
}

