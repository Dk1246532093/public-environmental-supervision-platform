package com.cqupt.nepmserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.cqupt.nepmserver.mapper")
@SpringBootApplication
public class NepmserverApplication {
    public static void main(String[] args) {
        SpringApplication.run(NepmserverApplication.class, args);
    }
}
