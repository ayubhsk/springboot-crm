package com.whx.excep;

//自定义的运行时异常
public class CountWrongException extends RuntimeException {
    public CountWrongException() {
        super();
    }

    public CountWrongException(String message) {
        super(message);
    }
}
