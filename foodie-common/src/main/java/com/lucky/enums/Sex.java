package com.lucky.enums;

/**
 * @author lank
 * @date 2020/1/14 16:29
 * @desc 性别枚举
 */
public enum Sex {

    woman(0,"女"),
    man(1,"男"),
    secret(2,"保密");

    public final int code;
    public final String desc;

    Sex(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
