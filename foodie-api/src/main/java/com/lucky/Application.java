package com.lucky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @FileName: Application.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-16
 **/
@SpringBootApplication
@ComponentScan(basePackages = {"org.n3r.idworker","com.lucky"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
