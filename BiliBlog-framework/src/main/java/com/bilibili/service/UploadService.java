package com.bilibili.service;

import com.bilibili.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UploadService
 * @Description TODO
 * @Version 1.0
 */
public interface UploadService {

    ResponseResult uploadImg(MultipartFile img);
}
