package com.bilibili;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @ClassName BiliBlogApplication
 * @Description TODO
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan(basePackages = "com.bilibili.mapper")
@EnableScheduling
public class BiliBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BiliBlogApplication.class, args);
    }
}