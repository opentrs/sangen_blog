package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
public interface CommentService extends IService<Comment>{


    ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
