package com.lucky.controller;

import com.lucky.core.exception.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @FileName: HelloController.java
 * @description: c
 * @author: 欧阳小广
 * @Date: 2021-01-29
 **/
@RestController
public class HelloController implements Serializable {
    @GetMapping(value = "test")
    public String test(String test) {
        return test;
    }


    @GetMapping(value = "e1")
    public String test1(String test) {
        throw new BusinessException("测试自定义异常");
    }

    @GetMapping(value = "e2")
    public String test2(String test) {
        throw new BusinessException(1021, "测试自定义异常");
    }

}
