package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.MyUserDetails;
import com.bilibili.domain.entity.User;
import com.bilibili.domain.vo.UserInfoVo;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.exception.SystemRuntimeException;
import com.bilibili.mapper.UserMapper;
import com.bilibili.service.UserService;
import com.bilibili.utils.BeanCopyUtils;
import com.bilibili.utils.RedisCache;
import com.bilibili.utils.SecurityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Version 1.0
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService{
    @Resource
    private RedisCache redisCache;

    @Resource
    PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult getUserInfo() {
        // 1. 获取id
        Long userId = SecurityUtils.getUserId();
        // 2，查询userInfo
        // 有先从Redis中获取,无从数据库中获取
        MyUserDetails userDetails = redisCache.getCacheObject(SystemConstants.BLOG_LOGIN + userId);
        User user = userDetails.getUser();
        if (ObjectUtils.isEmpty(user)){
            // 数据库查询
            user = getById(userId);
        }
        // 3. 封装VO
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        Long userIdSecurity = SecurityUtils.getUserId();
        if (SecurityUtils.getUserId().equals(user.getId())){
            MyUserDetails userDetails = redisCache.getCacheObject(SystemConstants.BLOG_LOGIN + userIdSecurity);
            updateById(user);
            userDetails.setUser(user);
            redisCache.setCacheObject(SystemConstants.BLOG_LOGIN + user.getId(), userDetails);
        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        return ResponseResult.okResult();
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Override
    public ResponseResult userLogon(User user) {
        // 非空判断
        String userName = user.getUserName();
        String userPassword = user.getPassword();
        String email = user.getEmail();
        if (!StringUtils.hasText(userName)){
            throw new SystemRuntimeException(AppHttpCodeEnum.USERNAME_NULL);
        }
        if (!StringUtils.hasText(userPassword)){
            throw new SystemRuntimeException(AppHttpCodeEnum.PASSWORD_NULL);
        }
        if (!StringUtils.hasText(email)){
            throw new SystemRuntimeException(AppHttpCodeEnum.EMAIL_NULL);
        }
        // 判断是否重复
        if (userNameExist(userName)){
            throw new SystemRuntimeException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        // 密码加密
        userPassword = passwordEncoder.encode(userPassword);
        user.setPassword(userPassword);
        // 存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserName, userName);
        return count(lambdaQueryWrapper)>0;
    }
}
