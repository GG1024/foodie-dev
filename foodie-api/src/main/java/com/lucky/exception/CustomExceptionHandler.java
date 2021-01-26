package com.lucky.exception;

import com.lucky.core.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @FileName: CustomExceptionHandler.java
 * @description:
 * @author: 欧阳小广
 * @Date: 2021-01-26
 **/
@RestControllerAdvice
public class CustomExceptionHandler {

    private JsonResult result = new JsonResult();

    //上传文件超过500k,捕获异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JsonResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        return result.error("文件上传大小不能超过500K");
    }
}
