package com.bilibili.controller;

import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Comment;
import com.bilibili.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName CommentController
 * @Description TODO
 * @Version 1.0
 */

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId,Integer pageNum,Integer pageSize) {
        return commentService.getCommentList(SystemConstants.TYPE_ARTICLE,articleId,pageNum,pageSize);
    }


    @PostMapping
    public ResponseResult addComment (@RequestBody Comment comment) {
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(Integer pageNum,Integer pageSize) {
        return commentService.getCommentList(SystemConstants.TYPE_LINK, null,pageNum,pageSize);
    }
}
