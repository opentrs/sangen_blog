package com.bilibili.exception;

import com.bilibili.domain.ResponseResult;
import com.bilibili.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @ClassName GlobalExceptionHandler
 * @Description 统一异常处理器
 * @Version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = SystemRuntimeException.class)
    public ResponseResult systemException(SystemRuntimeException e){
        // 1.打印异常信息
        log.error("出现异常 SystemRuntimeException",e);
        // 2.获取异常massage 封装成 ResponseResult
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseResult globalExceptionHandler(Exception e){
        // 1.打印异常信息
        log.error("出现异常 SystemRuntimeException",e);
        // 2.获取异常massage 封装成 ResponseResult
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}
