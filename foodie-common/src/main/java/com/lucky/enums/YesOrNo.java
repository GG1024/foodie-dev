package com.lucky.enums;

/**
 * @author lank
 * @date 2020/1/15 22:37
 * @desc
 */
public enum YesOrNo {
    YES(1,"yes"),
    NO(0,"no");

    public Integer code;
    public String desc;

    YesOrNo(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
