package com.lucky.enums;

public enum ResponseEnum {


    SUCCESS(200, "SUCCESS"),
    FAILED(500, "FAILED"),
    BUSINESSEXCEPTION(555, "BUSINESSEXCEPTION"),
    ;


    public Integer code;
    public String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
