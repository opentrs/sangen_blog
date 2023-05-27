package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Article;
import com.bilibili.domain.vo.ArticleDetailVO;
import com.bilibili.domain.vo.ArticleListVO;
import com.bilibili.domain.vo.HotArticleVO;
import com.bilibili.domain.vo.PageVO;
import com.bilibili.mapper.ArticleMapper;
import com.bilibili.service.ArticleService;
import com.bilibili.service.CategoryService;
import com.bilibili.utils.BeanCopyUtils;
import com.bilibili.utils.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static com.bilibili.constants.SystemConstants.ARTICLE_STATUS_NORMAL;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Resource
    private CategoryService categoryService;

    @Resource
    private RedisCache redisCache;

    /**
     * 需要查询浏览量最高的前10篇文章的信息。要求展示文章标题和浏览量。把能让用户自己点击跳转到具体的文章详情进行浏览。
     * 注意:不能把草稿展示出来，不能把删除了的文章查询出来。要按照浏览量进行降序排序。
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article>  queryWrapper = new LambdaQueryWrapper<>();
        // 1.正式文章 status 状态（0已发布，1草稿）
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        // 2.按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        // 3.取前10篇文章
        Page<Article> page = new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();

        /* List<HotArticleVO> hotArticleVOS = new ArrayList<>();
        // BeanUtils拷贝 封装成VO
        for (Article article : articles) {
            HotArticleVO articleVO = new HotArticleVO();
            BeanUtils.copyProperties(article,articleVO);
            hotArticleVOS.add(articleVO);
        } */

        List<HotArticleVO> ts = BeanCopyUtils.copyBean(articles, HotArticleVO.class);
        return ResponseResult.okResult(ts);
    }

    /**
     * 分页查询文章列表
     * @param categoryId
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Override
    public ResponseResult getArticleList(Long categoryId, Integer pageSize, Integer pageNumber) {
        // 判断 pageSize 和 pageNumber 是否为空
        if (Objects.isNull(pageSize) && Objects.isNull(pageNumber)){
            pageSize = 10;
            pageNumber = 1;
        }
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 1.判断categoryId
        boolean b = Objects.nonNull(categoryId) && categoryId > 0;
        queryWrapper.eq(b,Article::getCategoryId,categoryId);
        // 2.正式发布的文章
        queryWrapper.eq(Article::getStatus,ARTICLE_STATUS_NORMAL);
        // 3.TOP文章置顶(isTop倒序排列)
        queryWrapper.orderByDesc(Article::getIsTop);
        // 3.分页查询
        Page<Article > page = new Page<>(pageNumber,pageSize);
        page(page,queryWrapper);
        // todo 查询getCategory
        // List<Article> articles = page.getRecords();
        // Map<Long,Long> acIdMap = new HashMap<>();
        // for (Article article : articles) {
        //     acIdMap.put(article.getId(),article.getCategoryId());
        // }
        // // 4.拷贝到文章列表VO
        // List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBean(page.getRecords(), ArticleListVO.class);
        // acIdMap.forEach((k,v)->{
        //     articleListVOS.forEach(articleListVO ->{
        //         if(articleListVO.getId().equals(k)){
        //             articleListVO.setCategoryName(categoryService.getById(v).getName());
        //         }
        //     });
        // });
        // PageVO pageVO = new PageVO(articleListVOS, page.getTotal());
        // return ResponseResult.okResult(pageVO);
        List<Article> articles = page.getRecords();
        // Set<Long> categoryIds = articles.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBean(articles, ArticleListVO.class);
        articleListVOS.forEach(articleListVO -> articleListVO.setCategoryName(categoryService.getById(articleListVO.getCategoryId()).getName()));
        PageVO pageVO = new PageVO(articleListVOS, page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult getArticleDetail(String id) {
        // 1.根据id查询文章
        Article article = getById(id);
        Long cacheMapValue = redisCache.getCacheMapValue("article:viewCount", id);
        article.setViewCount(cacheMapValue);
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);
        // 2.查询分类名
        Long categoryId = articleDetailVO.getCategoryId();
        articleDetailVO.setCategoryName(categoryService.getById(categoryId).getName());

        return ResponseResult.okResult(articleDetailVO);
    }

    @Override
    public ResponseResult updateViewCount(String id) {
        // 从redis获取ViewCount
        redisCache.incrementCacheMapValue("article:viewCount" , id , 1);
        return ResponseResult.okResult();
    }
}