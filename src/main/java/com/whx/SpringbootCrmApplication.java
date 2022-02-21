package com.whx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.whx.settings.dao","com.whx.workbench.dao"})
public class SpringbootCrmApplication {


    public static void main(String[] args) {
        SpringApplication.run(SpringbootCrmApplication.class, args);
    }

}
