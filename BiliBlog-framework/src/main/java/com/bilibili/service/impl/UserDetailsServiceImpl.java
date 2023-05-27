package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.bilibili.domain.entity.MyUserDetails;
import com.bilibili.domain.entity.User;
import com.bilibili.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UserDetailsServiceImpl
 * @Description TODO
 * @Version 1.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User>  userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,username);
        // 1.根据用户名查找用户信息
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        // 2.判断是否查到用户，未查到抛出异常
        if(ObjectUtils.isNull(user)) {
            throw new RuntimeException("用户名不存在");
        }
        // 3.返回用户信息

        //TODO 4.查询用户的权限

        return  new MyUserDetails(user);
    }
}

