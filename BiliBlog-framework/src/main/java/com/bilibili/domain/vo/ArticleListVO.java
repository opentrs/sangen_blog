package com.bilibili.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *  private Long id;
 *     private String title;
 *     private Long viewCount;
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListVO {

    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 文章摘要
     */
    private String summary;

    private String categoryName;

    private Long categoryId;

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
