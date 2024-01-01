package com.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 消费者测试服务
 *
 * @author wangsen_a
 * @date 2023/12/18
 */
@EnableFeignClients
@SpringBootApplication
public class ConsumerOneApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerOneApplication.class, args);
    }
}
