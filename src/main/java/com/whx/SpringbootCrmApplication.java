package com.whx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@EnableTransactionManagement
@SpringBootApplication
@MapperScan(basePackages = {"com.whx.settings.dao","com.whx.workbench.dao"})
public class SpringbootCrmApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootCrmApplication.class, args);
/*        String[] names = run.getBeanDefinitionNames();
        for(String name:names){
            System.out.println(name);
        }*/

    }

}
