package com.ws;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ws.dao")
public class TagTreeApplication {
    public static void main(String[] args) {
        SpringApplication.run(TagTreeApplication.class, args);
    }
}
