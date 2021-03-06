package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.CardRebate;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/11/18
 */

public interface TurnoverService {
    /**
     * 获取营业额统计列表
     * @param page
     * @param eventId 活动名称
     * @param title 卡标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    Response getTurnoverList(PageQuery page, Integer eventId, String title,  Long startTime, Long endTime,String phone,String storeName,Integer isExport) throws ParseException;

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
    Response getChargeRecord(PageQuery page,String keywords, String phone, String storeName, Long cardNo, Long startTime, Long endTime,Integer isExport) throws ParseException;

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
    Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long orderNo, Long startTime, Long endTime,Integer isExport) throws ParseException;

    /**
     * 退回扣款
     * @param orderNo
     * @return
     */
    Response refundExpense(Long orderNo, String phone, String code,String explain,Integer type);
//    Response refundExpense(CardRebate rebate, String password);

    /**
     * 退款记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    Response getRefundExpense(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime,Integer isExport) throws ParseException;

    /**
     * 转赠记录
     * @param page
     * @param nickname 昵称
     * @param title 卡标题
     * @param status 状态
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param isExport 是否导出
     * @return
     * @throws ParseException
     * @throws IOException
     */
    Response getTransferSend(PageQuery page, String nickname, String title, Byte status, Long startTime, Long endTime, Integer isExport) throws ParseException;

    /**
     * 充值退回扣款
     *
     * @param orderNo
     * @param phone
     * @return
     */
    Response refundCharge(Long orderNo, String phone, String code, String explain);
}
