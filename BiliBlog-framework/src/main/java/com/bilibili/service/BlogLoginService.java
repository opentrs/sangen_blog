package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.User;

/**
 * @ClassName BlogLoginService
 * @Description TODO
 * @Version 1.0
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
