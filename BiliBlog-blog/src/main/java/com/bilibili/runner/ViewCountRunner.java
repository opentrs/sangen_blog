package com.bilibili.runner;

import com.bilibili.domain.entity.Article;
import com.bilibili.mapper.ArticleMapper;
import com.bilibili.utils.RedisCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ViewCountRunner
 * @Description TODO
 * @Version 1.0
 */
@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    ArticleMapper articleMapper;

    @Resource
    RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {

        // 查询文章 id  ViewCount
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> map = articles.stream().collect(Collectors.toMap(a -> a.getId().toString(), b -> b.getViewCount().intValue()));
        // 存入redis
        redisCache.setCacheMap("article:viewCount", map);
    }
}
