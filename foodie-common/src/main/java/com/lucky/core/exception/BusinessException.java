package com.lucky.core.exception;

import com.lucky.enums.ResponseEnum;

/**
 * @FileName: BusinessException.java
 * @description: 业务自定义异常
 * @author: 欧阳小广
 * @Date: 2021-01-30
 **/
public class BusinessException extends RuntimeException {
    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(String message) {
        this.code = ResponseEnum.BUSINESSEXCEPTION.code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
