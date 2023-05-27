package com.bilibili.service.impl;

import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.MyUserDetails;
import com.bilibili.domain.entity.User;
import com.bilibili.domain.vo.BlogUserLoginVo;
import com.bilibili.domain.vo.UserInfoVo;
import com.bilibili.service.BlogLoginService;
import com.bilibili.utils.BeanCopyUtils;
import com.bilibili.utils.JwtUtil;
import com.bilibili.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @ClassName BlogLoginServiceImpl
 * @Description TODO
 * @Version 1.0
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    @Override
    public ResponseResult login(User user) {
        // 1.封装到authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        // 2.尝试对传递的Authentication对象进行身份验证，如果成功，则返回完全填充的Authentication对象 (包括已授予的权限)。
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        // 3.判断authentication对象是否有效（不为空）
        if (ObjectUtils.isEmpty(authentication)) {
            throw new RuntimeException("用户名或密码错误");
        }
        // 4.获取userID,使用JWT工具类生成token
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        String userId = userDetails.getUser().getId().toString();
        String token = JwtUtil.createJWT(userId);
        // 5.把用户信息存入Redis
        String key =  SystemConstants.BLOG_LOGIN + userId;
        redisCache.setCacheObject(key,userDetails);
        // 6.把token和用户信息返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(userDetails.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(token,userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        Long userid = myUserDetails.getUser().getId();
        String key = SystemConstants.BLOG_LOGIN + userid;
        redisCache.deleteObject(key);
        return ResponseResult.okResult();
    }
}
