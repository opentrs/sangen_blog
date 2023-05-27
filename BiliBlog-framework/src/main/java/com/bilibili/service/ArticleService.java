package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
public interface ArticleService extends IService<Article>{

    ResponseResult hotArticleList();

    ResponseResult getArticleList(Long categoryId, Integer pageSize, Integer pageNumber);

    ResponseResult getArticleDetail(String id);

    ResponseResult updateViewCount(String id);
}
