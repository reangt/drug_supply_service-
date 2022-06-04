package com.xz.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProjectExeptionAdvice {
    @ExceptionHandler
    public R doException(Exception exception){
        exception.printStackTrace();
        return new R(false,"请求错误");
    }
}
