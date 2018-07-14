package com.spring4all.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

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
