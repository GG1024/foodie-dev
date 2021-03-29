package com.lucky.core.exception;

import com.lucky.core.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
public class GlobalExceptionHandler {
    //上传文件超过500k,捕获异常
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public JsonResult handlerMaxUploadFile(MaxUploadSizeExceededException e) {
        log.error("上传异常：{}", e.getMessage());
        return JsonResult.error("文件上传大小不能超过500K");
    }

    @ExceptionHandler({BusinessException.class})
    public JsonResult runtimeException(BusinessException e) {
        log.error("业务异常：{}", e.getMessage());
        return JsonResult.error(e.getCode(), e.getMessage());
    }


    @ExceptionHandler({RuntimeException.class,
            NullPointerException.class,
            ClassCastException.class,
            NoSuchMethodException.class,
            IndexOutOfBoundsException.class,
            HttpMessageNotReadableException.class,
            TypeMismatchException.class

    })
    public JsonResult runtimeExceptionHandler(Exception e) {
        log.error("运行时异常：{}", e.getMessage());
        return JsonResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

    @ExceptionHandler({Exception.class})
    public JsonResult exception(Exception e) {
        log.error("全局异常：{}", e.getMessage());
        return JsonResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }

}
