package com.bilibili.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ArticleDetailVO
 * @Description TODO
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVO {

    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 文章内容
     */
    private String content;

    private Long categoryId;

    private String categoryName;
    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 访问量
     */
    private Long viewCount;

    private Date createTime;

}
