package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Comment;
import com.bilibili.domain.vo.CommentVO;
import com.bilibili.domain.vo.PageVO;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.exception.SystemRuntimeException;
import com.bilibili.mapper.CommentMapper;
import com.bilibili.service.CommentService;
import com.bilibili.service.UserService;
import com.bilibili.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService{

    @Resource
    UserService userService;

    @Override
    public ResponseResult getCommentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageSize == null || pageNum < 1 || pageSize < 1 ){
            pageNum = 1;
            pageSize = 10;
        }

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.TYPE_ARTICLE.equals(commentType),Comment::getArticleId,articleId);//如果是TYPE_ARTICLE的评论，才查询
        queryWrapper.eq(Comment::getRootId,SystemConstants.ROOT_COMMENT);
        queryWrapper.eq( Comment::getType,commentType);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        Page <Comment> page = new Page<>(pageNum, pageSize);
        page(page,queryWrapper);

        List<CommentVO> commentVOList = commentListToVO(page.getRecords());
        //插入子评论
        commentVOList.forEach(comment -> comment.setChildren(getChildrenByCommentId(comment.getId())));

        return ResponseResult.okResult(new PageVO(commentVOList, page.getTotal()));
    }

    /**
     * 获取子评论
     * @param id
     * @return
     */
    private List<CommentVO> getChildrenByCommentId(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByDesc(Comment::getCreateTime);
        List<Comment> childlenCommentList = list(queryWrapper);
        return commentListToVO(childlenCommentList);
    }

    /**
     *  转换评论list转换vo
     *  同时插入昵称
     * @param commentList
     * @return
     */
    private List<CommentVO> commentListToVO(List<Comment> commentList){
        List<CommentVO> commentVOS = BeanCopyUtils.copyBean(commentList, CommentVO.class);
        commentVOS.forEach(commentVO -> {
            // 查询用户昵称
            commentVO.setUsername(userService.getById(commentVO.getCreateBy()).getNickName());
            // 查询toCommentUser名称
            Long userid = commentVO.getToCommentUserId();
            if(userid!= -1){
                commentVO.setToCommentUserName(userService.getById(userid).getNickName());
            }
        });
        return commentVOS;
    }


    /**
     * 添加评论
     * @param comment
     * @return
     */
    @Override
    public ResponseResult addComment(Comment comment) {
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemRuntimeException(AppHttpCodeEnum.COMMENT_CONTENT_IS_EMPTY);
        }
        // todo 评论敏感词检测
        save(comment);
        return ResponseResult.okResult();
    }
}
