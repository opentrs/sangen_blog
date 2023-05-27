package com.bilibili.filter;

import com.alibaba.fastjson.JSON;
import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.MyUserDetails;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.utils.JwtUtil;
import com.bilibili.utils.RedisCache;
import com.bilibili.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description TODO
 * @Version 1.0
 */
@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 获取头总token
        String token = request.getHeader("token");
        if (ObjectUtils.isEmpty(token)){//token为空说明不需要登录直接放行
            filterChain.doFilter(request, response);
            return;//不需要执行下面的代码
        }
        // 2. 解析获取userid
        Claims jwt;
        try {
            jwt = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            // 使用WebUtils返回错误信息
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response,JSON.toJSONString(result));
            throw new RuntimeException(e);
        }
        String userid = jwt.getSubject();
        // 3. 从redis获取用户信息
        MyUserDetails userDetails = redisCache.getCacheObject(SystemConstants.BLOG_LOGIN + userid);
        if (ObjectUtils.isEmpty(userDetails)){//获取不到需要重新登录
            // 使用WebUtils返回错误信息
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response,JSON.toJSONString(result));
            throw new RuntimeException(AppHttpCodeEnum.NEED_LOGIN.getMsg());
        }
        // 4. 存入security context holder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
