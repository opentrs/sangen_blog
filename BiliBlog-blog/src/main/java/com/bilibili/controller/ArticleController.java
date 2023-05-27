package com.bilibili.controller;

import com.bilibili.domain.ResponseResult;
import com.bilibili.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Version 1.0
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    ArticleService articleService;

    /**
     * 查询热门文章，根据文章热度来进行排序
     * @return
     */
    @GetMapping(value = "/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    /**
     * 分页查询文章列表
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult getArticleList(Long categoryId, Integer pageSize, Integer pageNum) {

        return articleService.getArticleList(categoryId, pageSize, pageNum);
    }

    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") String id){
        return articleService.getArticleDetail(id);
    }

    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") String id){
        return articleService.updateViewCount(id);
    }

}
