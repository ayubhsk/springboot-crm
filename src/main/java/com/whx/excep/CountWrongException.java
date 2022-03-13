package com.whx.excep;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//自定义的运行时异常
@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "操作出错")
public class CountWrongException extends RuntimeException {
    public CountWrongException() {
        super();
    }

    public CountWrongException(String message) {
        super(message);
    }
}
