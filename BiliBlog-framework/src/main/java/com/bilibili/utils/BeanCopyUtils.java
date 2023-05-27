package com.bilibili.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BeanCopyUtils
 * @Description bean转换工具类 Entity -> VO
 * @Version 1.0
 */
public class BeanCopyUtils {

    private BeanCopyUtils(){

    }

    public static <T> T copyBean(Object source ,Class<T> targetClass){
        // 目标对象
        T result;
        try {
            result = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        // 拷贝
        BeanUtils.copyProperties(source, result);
        // 返回
        return result;
    }

    public static <O,T> List<T> copyBean(List<O> sourceList , Class<T> resultList) {
        return sourceList.stream()
                .map(source -> copyBean(source, resultList))
                .collect(Collectors.toList());
    }

}
