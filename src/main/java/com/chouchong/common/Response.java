package com.chouchong.common;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @author yichenshanren
 * @date 2017/9/28
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties({"successful"})
public interface Response<T> extends Serializable {

    boolean isSuccessful();

    T getData();

    String getMsg();

    int getErrCode();

    int getResult();

}
