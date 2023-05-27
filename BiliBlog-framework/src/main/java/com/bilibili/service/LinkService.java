package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Link;
import com.baomidou.mybatisplus.extension.service.IService;
public interface LinkService extends IService<Link>{
    ResponseResult getAllLink();
}
