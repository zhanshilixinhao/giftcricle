package com.chouchong.exception;


import com.chouchong.common.ErrCodeException;
import com.chouchong.common.ErrorCode;

/**
 * @author linqin
 * @date 2018/5/22
 */
public class ServiceException extends RuntimeException implements ErrCodeException {
    private int errCode;

    public ServiceException(ErrorCode msg) {
        super(msg.getMsg());
        this.errCode = ErrorCode.ERROR.getCode();
    }

    public ServiceException(int errCode) {
        this.errCode = errCode;
    }

    public ServiceException(int errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode() {
        return errCode;
    }
}
