package com.bilibili.controller;

import com.bilibili.domain.ResponseResult;
import com.bilibili.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @ClassName UploadController
 * @Description TODO
 * @Version 1.0
 */
@RestController
public class UploadController {

    @Resource
    private UploadService uploadService;
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){
        return uploadService.uploadImg(img);
    }
}
