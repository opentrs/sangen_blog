package com.bilibili.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.User;

/**
 * @ClassName UserService
 * @Description TODO
 * @Version 1.0
 */
public interface UserService extends IService<User> {
    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult userLogon(User user);
}
