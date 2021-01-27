package com.chouchong.controller.v3;


import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.v3.CardService;
import com.chouchong.service.v3.ElCouponService;
import com.chouchong.service.v4.RebateCouponService;
import com.chouchong.service.v4.ShareCouponService;
import com.chouchong.utils.AESUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("manage/tool")
public class QrcodeController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ElCouponService elCouponService;

    @Resource
    private ShareCouponService shareCouponService;

    @Resource
    private RebateCouponService rebateCouponService;


    @RequestMapping("scan")
    public Response scanQrcode(String qrcode) {
        if (StringUtils.isBlank(qrcode)) {
            return ResponseFactory.errMissingParameter();
        }
        String decrypt = AESUtils.decrypt("zheshishenmemima", qrcode);
        if (decrypt == null) {
            return ResponseFactory.err("二維碼無效!");
        }
        String[] split = decrypt.split(",");
        if (StringUtils.equals(split[0], "1")) {
            Integer id = Integer.parseInt(split[1]);
            return cardService.detailByQrcode(id);
        } else if (StringUtils.equals(split[0], "2")){
            Long id = Long.parseLong(split[1]);
            return elCouponService.detailByQrcode(id);
        } else if (StringUtils.equals(split[0], "3")){
            long id = Long.parseLong(split[1]);
            return shareCouponService.detailByQrcode(id);
        } else if (StringUtils.equals(split[0], "4")) {
            long id = Long.parseLong(split[1]);
            return rebateCouponService.detailByQrcode(id);
        }
        return null;
    }
}
