package com.bilibili.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 评论表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sg_comment")
public class Comment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 评论类型（0代表文章评论，1代表友链评论）
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 文章id
     */
    @TableField(value = "article_id")
    private Long articleId;

    /**
     * 根评论id
     */
    @TableField(value = "root_id")
    private Long rootId;

    /**
     * 评论内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 所回复的目标评论的userid
     */
    @TableField(value = "to_comment_user_id")
    private Long toCommentUserId;

    /**
     * 回复目标评论id
     */
    @TableField(value = "to_comment_id")
    private Long toCommentId;

    @TableField(value = "create_by",fill = FieldFill.INSERT)
    private Long createBy;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_by",fill = FieldFill.UPDATE)
    private Long updateBy;

    @TableField(value = "update_time",fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 删除标志（0代表未删除，1代表已删除）
     */
    @TableField(value = "del_flag")
    private Integer delFlag;
}