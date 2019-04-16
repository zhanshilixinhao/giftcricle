package com.chouchong.exception;

import com.chouchong.common.ErrCodeException;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author yichenshanren
 * @date 2017/11/24
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response exception(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof ErrCodeException) {
            return ResponseFactory.errMsg(((ErrCodeException) ex).getErrCode(), ex.getMessage());
        }
        // return ResponseFactory.err(String.format("请求异常%s:错误信息--%s", ex.getClass().getName(), ex.getMessage()));
        return ResponseFactory.err("请求数据失败!");
    }

}
