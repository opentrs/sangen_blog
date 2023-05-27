package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Article;
import com.bilibili.domain.entity.Category;
import com.bilibili.domain.vo.CategoryVO;
import com.bilibili.mapper.CategoryMapper;
import com.bilibili.service.ArticleService;
import com.bilibili.service.CategoryService;
import com.bilibili.utils.BeanCopyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.bilibili.constants.SystemConstants.ARTICLE_STATUS_NORMAL;
import static com.bilibili.constants.SystemConstants.CATEGORY_STATUS_NORMAL;


@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Resource
    ArticleService articleService;

    /**
     * 获取已有文章的分类
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        // 1.获取文章表
        LambdaQueryWrapper<Article> articleLambdaQueryWrapperWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapperWrapper.eq(Article::getStatus, ARTICLE_STATUS_NORMAL); // 查询已发布文章
        List<Article> articleList = articleService.list(articleLambdaQueryWrapperWrapper);

        // // 2.获取分类表
        // LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // categoryLambdaQueryWrapper.eq(Category::getStatus, CATEGORY_STATUS_NORMAL);// 查询启用的分类
        // List<Category> categoryList = baseMapper.selectList(categoryLambdaQueryWrapper);
        //
        // // 3.取出文章id 和 分类id
        // Set<Long> articleCategoryIds = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());
        // List<Long> categoryIds = categoryList.stream().map(Category::getId).collect(Collectors.toList());
        //
        // // 4. 取articleCategoryIds 和 categoryIds交集
        // List<Long> acid = (List<Long>) CollectionUtils.intersection(articleCategoryIds, categoryIds);
        //
        // // 5. 把 categoryList 中 有 acid 的 提取
        // List<Category> categoryListExtract = categoryList.stream().filter(a -> acid.contains(a.getId())).collect(Collectors.toList());
        //
        // List<CategoryVO> categoryVOS = BeanCopyUtils.copyBean(categoryListExtract, CategoryVO.class);
        //
        // return ResponseResult.okResult(categoryVOS);

        Set<Long> articleList_categoryIds = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        List<Category> categories = listByIds(articleList_categoryIds);

        categories = categories.stream().
                filter(category -> category.getStatus().equals(String.valueOf(CATEGORY_STATUS_NORMAL)))
                .collect(Collectors.toList());

        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBean(categories, CategoryVO.class);

        return ResponseResult.okResult(categoryVOS);
    }
}
