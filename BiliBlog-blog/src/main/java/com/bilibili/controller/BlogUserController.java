package com.bilibili.controller;

import com.bilibili.annotation.SysLog;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.User;
import com.bilibili.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName BlogUserController
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
public class BlogUserController {
    @Resource
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.getUserInfo();
    }


    @SysLog(businessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    public ResponseResult userLogon(@RequestBody User user){
        return userService.userLogon(user);
    }

}
