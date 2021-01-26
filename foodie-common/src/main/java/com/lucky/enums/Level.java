package com.lucky.enums;

/**
 * @author lank
 * @date 2020/1/17 9:38
 * @desc
 */
public enum Level {

    GOOD(1,"好评"),
    NORMAL(2,"中评"),
    BAD(3,"差评");


    public Integer code;
    public String desc;

    Level(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
