package com.chouchong.service.v3;

import com.chouchong.common.Response;

import java.io.IOException;

/**
 * @author linqin
 * @date 2019/12/24
 */

public interface SmsCodeService {
    /**
     * 短信验证
     * @param phone
     * @param type
     * @return
     */
    Response sendSmsCode(String phone) throws IOException;
}
