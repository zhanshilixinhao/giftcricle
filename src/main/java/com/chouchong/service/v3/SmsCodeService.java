package com.chouchong.service.v3;

import com.chouchong.common.Response;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author linqin
 * @date 2019/12/24
 */

public interface SmsCodeService {
    /**
     * 短信验证
     * @return
     */
    Response sendSmsCode() throws IOException;

}
