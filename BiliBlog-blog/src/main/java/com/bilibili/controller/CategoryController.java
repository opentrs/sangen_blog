package com.bilibili.controller;

import com.bilibili.domain.ResponseResult;
import com.bilibili.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName CategoryController
 * @Description TODO 文章分类
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Resource
    CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategory(){
        return categoryService.getCategoryList();
    }
}
