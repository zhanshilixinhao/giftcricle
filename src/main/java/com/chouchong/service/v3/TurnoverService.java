package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

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
    Response getTurnoverList(PageQuery page, Integer eventId, String title,  Long startTime, Long endTime,String phone,String storeName) throws ParseException;

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
    Response getChargeRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException;

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
    Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException;
}
