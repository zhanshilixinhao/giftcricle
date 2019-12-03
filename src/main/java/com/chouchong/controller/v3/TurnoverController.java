package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.CardRebate;
import com.chouchong.service.v3.TurnoverService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * @author linqin
 * @date 2019/11/18
 */
@RestController
@RequestMapping("manage/v3/turnover")
public class TurnoverController {

    @Autowired
    private TurnoverService turnoverService;

    /**
     * 获取营业额统计列表
     * @param page
     * @param eventId 活动id
     * @param title 卡标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @PostMapping("list")
    public Response getTurnoverList(PageQuery page, Integer eventId, String title,  Long startTime,
                                    Long endTime,String phone,String storeName) throws ParseException {
        return turnoverService.getTurnoverList(page,eventId,title,startTime,endTime,phone,storeName);
    }

    /**
     * 充值记录
     * @param page
     * @param phone 电话号码
     * @param storeName 门店名称
     * @param cardNo 卡号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @PostMapping("record_list")
    public Response getChargeRecord(PageQuery page, String phone, String storeName,  Long cardNo,Long startTime,
                                    Long endTime) throws ParseException {
        return turnoverService.getChargeRecord(page,phone,storeName,cardNo,startTime,endTime);
    }


    /**
     * 扣款记录
     * @param page
     * @param phone 电话号码
     * @param storeName 门店名称
     * @param cardNo 卡号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @PostMapping("expense_list")
    public Response getExpenseRecord(PageQuery page, String phone, String storeName,  Long cardNo,Long startTime,
                                    Long endTime) throws ParseException {
        return turnoverService.getExpenseRecord(page,phone,storeName,cardNo,startTime,endTime);
    }

    /**
     * 退回扣款
     * @param rebate
     * @return
     */
    @PostMapping("refund")
    public Response refundExpense(CardRebate rebate,String password){
        if (rebate.getUserId() == null || rebate.getMembershipCardId() == null || rebate.getExpenseRecordId() == null||
        rebate.getMoney() == null ||rebate.getOrderNo() == null|| StringUtils.isAnyBlank(rebate.getExplain(),password)){
            return ResponseFactory.errMissingParameter();
        }
        return turnoverService.refundExpense(rebate,password);
    }


}
