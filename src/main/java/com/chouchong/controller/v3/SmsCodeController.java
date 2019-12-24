package com.chouchong.controller.v3;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.v3.SmsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author linqin
 * @date 2019/12/24
 */
@RequestMapping("manage")
@RestController
public class SmsCodeController {

    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 短信验证
     * @param phone
     * @return
     */
    @PostMapping("ask/code")
    public Response createSmsCode(String phone) throws IOException {
        if (StringUtils.isBlank(phone) ) {
            return ResponseFactory.errMissingParameter();
        }
        return smsCodeService.sendSmsCode(phone);
    }
}
