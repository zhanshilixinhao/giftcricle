package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.InvoiceRecord;
import com.chouchong.service.v3.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * @author linqin
 * @date 2020/2/13 17:16
 */
@RestController
@RequestMapping("manage/v3/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    /**
     * 添加发票记录
     * @param invoice
     * @return
     */
    @PostMapping("add")
    public Response addInvoice(InvoiceRecord invoice){
        if (invoice.getCardId() == null ||invoice.getUserId() == null||
                invoice.getAmount() == null){
            return ResponseFactory.errMissingParameter();
        }
        if (invoice.getAmount().compareTo(new BigDecimal("0")) == 0){
            return ResponseFactory.err("开票金额必须大于0");
        }
        return invoiceService.addInvoice(invoice);
    }

    /**
     * 获取发票列表
     * @return
     */
    @PostMapping("list")
    public Response getInvoice(Integer cardId,Integer userId){
        if (cardId == null ||userId == null){
            return ResponseFactory.errMissingParameter();
        }
        return invoiceService.getInvoice(cardId,userId);
    }


    /**
     * 发票记录列表查询
     * @param page
     * @param phone 号码
     * @param startTime
     * @param endTime
     * @return
     */
    @RequestMapping("all_list")
    public Response getChargeRecord(PageQuery page, String phone,Long startTime,
                                    Long endTime) throws ParseException {
       return invoiceService.getInvoiceList(page,phone,startTime,endTime);
    }

}
