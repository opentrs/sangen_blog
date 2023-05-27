package com.bilibili.exception;

import com.alibaba.fastjson.JSON;
import com.bilibili.domain.ResponseResult;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.utils.WebUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName AuthenticationEntryPointImpl
 * @Description 登录异常处理器
 * @Version 1.0
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        authException.printStackTrace();
        ResponseResult errorResult;
        //InsufficientAuthenticationException 异常token
        if(authException instanceof InsufficientAuthenticationException){
            //Full authentication is required to access this resource
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //BadCredentialsException 密码错误
        else if(authException instanceof BadCredentialsException){
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR.getCode(),authException.getMessage());
        }else {
            errorResult = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),"用户认证异常");
        }
        WebUtils.renderString(response, JSON.toJSONString(errorResult));
    }
}
