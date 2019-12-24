package com.chouchong.service.v3.impl;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.v3.SmsCodeService;
import com.chouchong.utils.sms.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

/**
 * @author linqin
 * @date 2019/12/24
 */
@Service
public class SmsCodeServiceImpl implements SmsCodeService {


    @Autowired
    private VerifyCodeRepository verifyCodeRepository;


    /**
     * 短信验证
     *
     * @param phone
     * @return
     */
    @Override
    public Response sendSmsCode(String phone) throws IOException {
        // 生成验证码 有效期3分钟
        String code = RandomStringUtils.randomNumeric(6);
        // 发送验证码
         SendUtil.smsSend(phone,"【礼遇圈】您的验证码是"+code+"如非本人操作，请忽略本信息。 ");
        verifyCodeRepository.save(code, phone);
        return ResponseFactory.sucMsg("发送成功!");
    }

}
