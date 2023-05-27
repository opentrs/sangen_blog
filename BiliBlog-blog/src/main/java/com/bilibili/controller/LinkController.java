package com.bilibili.controller;

import com.bilibili.domain.ResponseResult;
import com.bilibili.service.LinkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName LinkController
 * @Description TODO
 * @Version 1.0
 */

@RestController
@RequestMapping("/link")
public class LinkController {
    @Resource
    LinkService  linkService;

    @GetMapping("/getAllLink")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}