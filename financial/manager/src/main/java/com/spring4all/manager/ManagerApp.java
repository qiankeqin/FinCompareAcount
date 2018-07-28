package com.spring4all.manager;

import com.spring4all.swagger.EnableMySwagger;
import com.spring4all.swagger.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

/**
 * @program: financial
 * @description: Manager启动类
 * @author: qiankeqin
 * @create: 2018-07-14 09:54
 **/
@SpringBootApplication
@EntityScan(basePackages = {"com.spring4all.entity"})
public class ManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApp.class);
    }
}
