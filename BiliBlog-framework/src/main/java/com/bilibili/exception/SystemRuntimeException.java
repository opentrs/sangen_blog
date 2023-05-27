package com.bilibili.exception;

import com.bilibili.enums.AppHttpCodeEnum;

/**
 * @ClassName SystemRuntimeException
 * @Description TODO
 * @Version 1.0
 */
public class SystemRuntimeException extends RuntimeException{
    private Integer code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemRuntimeException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
}
