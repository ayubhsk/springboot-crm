package com.whx.excep;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 处理整个web controller的异常
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
/*
    @ExceptionHandler({ArithmeticException.class,NullPointerException.class,CountWrongException.class})  //处理异常
    public String handleArithException(Exception e){

        log.error("异常是：{}",e.getMessage());
        System.out.println(e.getMessage()+"++++++++++++++++++++++++");
        return "login"; //视图地址
    }*/
}
