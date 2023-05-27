package com.bilibili.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName HotArticle
 * @Description TODO
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVO {
    private Long id;
    private String title;
    private Long viewCount;
}
