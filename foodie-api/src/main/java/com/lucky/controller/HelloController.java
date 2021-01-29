package com.lucky.controller;

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
}
