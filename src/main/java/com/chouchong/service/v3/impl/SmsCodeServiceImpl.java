package com.chouchong.service.v3.impl;

import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.entity.v3.Store;
import com.chouchong.service.v3.SmsCodeService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.sms.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 短信验证
     *
     * @return
     */
    @Override
    public Response sendSmsCode() throws IOException {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        if (store == null) {
            return ResponseFactory.err("发送失败");
        }
        // 生成验证码 有效期3分钟
        String code = RandomStringUtils.randomNumeric(6);
        // 发送验证码
        SendUtil.smsSend(store.getPhone(), "【礼遇圈】您的验证码是" + code + "如非本人操作，请忽略本信息。 ");
        verifyCodeRepository.save(code, store.getPhone());
        return ResponseFactory.sucMsg("发送成功!");
    }

}
