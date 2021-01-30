package com.lucky.core.exception;

import com.lucky.core.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @FileName: GlobalExceptionHandler.java
 * @description: 全局异常处理器
 * @author: 欧阳小广
 * @Date: 2021-01-30
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{
    //上传文件超过500k,捕获异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JsonResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        return JsonResult.error("文件上传大小不能超过500K");
    }

    @ExceptionHandler({BusinessException.class,RuntimeException.class})
    public JsonResult runtimeException(BusinessException e) {
        return JsonResult.error(e.getCode(),e.getMessage());
    }

}
