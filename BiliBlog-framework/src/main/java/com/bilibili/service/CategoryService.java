package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CategoryService extends IService<Category>{
    ResponseResult getCategoryList();


}
