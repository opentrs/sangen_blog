package com.bilibili.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bilibili.constants.SystemConstants;
import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.Link;
import com.bilibili.domain.vo.LinkVO;
import com.bilibili.mapper.LinkMapper;
import com.bilibili.service.LinkService;
import com.bilibili.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        // 1.查询所有审核通过的
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> allLink = list(linkLambdaQueryWrapper);

        // 2.封装vo
        List<LinkVO> linkVOS = BeanCopyUtils.copyBean(allLink, LinkVO.class);
        return ResponseResult.okResult(linkVOS);
    }
}
