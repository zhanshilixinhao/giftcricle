package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.ElCouponUseLog;
import com.chouchong.service.v3.ElCouponUseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("manage/v3/coupon/log")
public class ElCouponUseLogController {

    @Autowired
    private ElCouponUseLogService elCouponUseLogService;

    /**
     * 核銷記錄
     *
     * @param log
     * @param page
     * @return
     */
    @RequestMapping("list")
    public Response getList(ElCouponUseLog log, PageQuery page) {
        return elCouponUseLogService.getList(log, page);
    }
}
