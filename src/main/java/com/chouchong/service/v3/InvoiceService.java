package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.InvoiceRecord;

import java.text.ParseException;

/**
 * @author linqin
 * @date 2020/2/13 17:17
 */

public interface InvoiceService {

    /**
     * 添加发票记录
     * @param invoice
     * @return
     */
    Response addInvoice(InvoiceRecord invoice);

    /**
     * 获取发票列表
     * @param page
     * @return
     */
    Response getInvoice(Integer cardId,Integer userId);

    /**
     * 发票记录列表查询
     * @param page
     * @param phone 号码
     * @param startTime
     * @param endTime
     * @return
     */
    Response getInvoiceList(PageQuery page, String phone, Long startTime, Long endTime) throws ParseException;
}
