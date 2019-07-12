package com.weijianlin.webmagic.bossdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.weijianlin.webmagic.bossdemo.dao")
public class BossdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BossdemoApplication.class, args);
    }

}
