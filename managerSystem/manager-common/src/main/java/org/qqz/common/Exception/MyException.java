package org.qqz.common.Exception;

public class MyException extends RuntimeException {
    private String errCode;
    private String errMsg;

    public MyException() {
        super();
    }

    public MyException(String errMsg) {
        super(errMsg);
        this.errMsg = errMsg;
    }

    public MyException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
}
