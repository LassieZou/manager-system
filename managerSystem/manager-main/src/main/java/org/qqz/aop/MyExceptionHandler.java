package org.qqz.aop;

import org.qqz.common.Exception.MyException;
import org.qqz.common.vo.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleMyExceptions(Exception ex) {
        return Resp.fail(ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Resp handleExceptions(Exception ex) {
        return Resp.fail("An error occurred,please check the logs.");
    }

}