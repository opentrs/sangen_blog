package com.bilibili.service.impl;

import com.bilibili.domain.ResponseResult;
import com.bilibili.domain.entity.User;
import com.bilibili.enums.AppHttpCodeEnum;
import com.bilibili.exception.SystemRuntimeException;
import com.bilibili.service.UploadService;
import com.bilibili.service.UserService;
import com.bilibili.utils.PathUtils;
import com.bilibili.utils.QiNiuOSSUtil;
import com.bilibili.utils.SecurityUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @ClassName UploadServiceImpl
 * @Description TODO
 * @Version 1.0
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Value("${oss.domain}")
    String domain;
    @Resource
    private QiNiuOSSUtil qiNiuOSSUtil;

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // 判断文件类型大小合法
        if (img.getSize() > 3 * 1024 * 1024) {
            throw new SystemRuntimeException(AppHttpCodeEnum.FILE_SIZE_TOO_BIG);
        }
        // 文件命名
        String imgName = img.getOriginalFilename();
        if (!imgName.endsWith(".jpg") && !imgName.endsWith(".png")) {
            throw new SystemRuntimeException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 获取时间，存入key作为路径
        String dateFormat = PathUtils.generateFilePath(imgName);
        // 上传图片，获取地址

        Response response = qiNiuOSSUtil.upToQiNiuOSS(img, dateFormat);

        DefaultPutRet putRet;
        try {
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
        // 获取存储地址
        String url = domain+putRet.key;

        return ResponseResult.okResult(url);
    }

}
