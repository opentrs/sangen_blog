package com.bilibili.job;

import com.bilibili.domain.entity.Article;
import com.bilibili.service.ArticleService;
import com.bilibili.utils.RedisCache;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName updateViewCountJob
 * @Description 定时更新updateViewCount从redis到数据库
 * @Version 1.0
 */
public class updateViewCountJob {

    @Resource
    RedisCache redisCache;

    @Resource
    ArticleService articleService;

    @Scheduled(cron = "5/10 * * * * ?")
    public void updateViewCount(){
        // 获取redis的浏览量
        Map<String, Integer> cacheMap = redisCache.getCacheMap("article:viewCount");
        List<Article> articleList = cacheMap.entrySet()
                .stream()
                .map(e -> new Article(Long.valueOf(e.getKey()), e.getValue().longValue()))
                .collect(Collectors.toList());
        // 更新存入数据库
        articleService.updateBatchById(articleList);
    }
}
