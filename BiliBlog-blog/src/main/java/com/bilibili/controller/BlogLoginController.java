package com.bilibili.controller;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.User;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.exception.SystemRuntimeException;
import com.bilibili.service.BlogLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Version 1.0
 */
@RestController
public class BlogLoginController {
    @Resource
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText( user.getUserName())){
            // 用户名不能为空
            throw new SystemRuntimeException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
