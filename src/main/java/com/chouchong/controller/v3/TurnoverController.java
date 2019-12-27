package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.common.v3.ExcelUtils;
import com.chouchong.entity.v3.CardRebate;
import com.chouchong.service.v3.TurnoverService;
import com.chouchong.service.v3.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param page
     * @param eventId   活动id
     * @param title     卡标题
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @RequestMapping("list")
    public Response getTurnoverList(PageQuery page, Integer eventId, String title, Long startTime,
                                    Long endTime, String phone, String storeName, Integer isExport, HttpServletRequest request,
                                    HttpServletResponse respon) throws ParseException, IOException {
        Response response = turnoverService.getTurnoverList(page, eventId, title, startTime, endTime, phone, storeName, isExport);
        if (response.getData() instanceof TurnoverVos && ((TurnoverVos) response.getData()).getTurnoverVo() != null) {
            List<TurnoverVo> vos = ((TurnoverVos) response.getData()).getTurnoverVo();
            if (!CollectionUtils.isEmpty(vos) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (TurnoverVo vo : vos) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("totalMoney", vo.getTotalMoney());
                    map.put("blagMoney", vo.getBlagMoney());
                    map.put("turnoverMoney", vo.getTurnoverMoney());
                    map.put("orderNo", vo.getOrderNo());
                    map.put("eventName", vo.getEventName());
                    map.put("storeName", vo.getStoreName());
                    map.put("title", vo.getTitle());
                    map.put("nickname", vo.getNickname());
                    map.put("phone", vo.getPhone());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "收入及营销费用");
                ExcelUtils.exportExcel2("收入及营销费用", new ExcelUtils.Header[]{
                        ExcelUtils.Header.h("index", "序号"),
                        ExcelUtils.Header.h("totalMoney", "营业额"),
                        ExcelUtils.Header.h("blagMoney", "本金收入"),
                        ExcelUtils.Header.h("turnoverMoney", "营销费用"),
                        ExcelUtils.Header.h("orderNo", "订单号"),
                        ExcelUtils.Header.h("eventName", "活动"),
                        ExcelUtils.Header.h("storeName", "消费门店"),
                        ExcelUtils.Header.h("title", "会员卡"),
                        ExcelUtils.Header.h("nickname", "用户昵称"),
                        ExcelUtils.Header.h("phone", "用户电话"),
                        ExcelUtils.Header.h("created", "创建时间"),
                }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }

    /**
     * 充值记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @RequestMapping("record_list")
    public Response getChargeRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime,
                                    Long endTime, Integer isExport, HttpServletRequest request, HttpServletResponse respon) throws ParseException, IOException {
        Response response = turnoverService.getChargeRecord(page, phone, storeName, cardNo, startTime, endTime, isExport);
        if (response.getData() instanceof ChargeReVos && ((ChargeReVos) response.getData()).getChargeReVo() != null) {
            List<ChargeReVo> vos = ((ChargeReVos) response.getData()).getChargeReVo();
            if (!CollectionUtils.isEmpty(vos) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (ChargeReVo vo : vos) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("nickname", vo.getNickname());
                    map.put("cardNo", vo.getCardNo());
                    map.put("phone", vo.getPhone());
                    map.put("rechargeMoney", vo.getRechargeMoney());
                    map.put("sendMoney", vo.getSendMoney());
                    map.put("orderNo", vo.getOrderNo());
                    map.put("storeName", vo.getStoreName());
                    map.put("title", vo.getTitle());
                    map.put("eventName", vo.getEventName());
                    map.put("explain", vo.getExplain());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "充值记录");
                ExcelUtils.exportExcel2("充值记录", new ExcelUtils.Header[]{
                        ExcelUtils.Header.h("index", "序号"),
                        ExcelUtils.Header.h("nickname", "用户昵称"),
                        ExcelUtils.Header.h("cardNo", "卡号"),
                        ExcelUtils.Header.h("phone", "用户电话"),
                        ExcelUtils.Header.h("rechargeMoney", "充值金额"),
                        ExcelUtils.Header.h("sendMoney", "赠送金额"),
                        ExcelUtils.Header.h("orderNo", "订单号"),
                        ExcelUtils.Header.h("storeName", "充值门店"),
                        ExcelUtils.Header.h("title", "会员卡"),
                        ExcelUtils.Header.h("eventName", "充值所选活动"),
                        ExcelUtils.Header.h("explain", "充值说明"),
                        ExcelUtils.Header.h("created", "充值时间"),
                }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }


    /**
     * 扣款记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @RequestMapping("expense_list")
    public Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long orderNo, Long startTime,
                                     Long endTime, Integer isExport, HttpServletRequest request, HttpServletResponse respon) throws ParseException, IOException {
        Response response = turnoverService.getExpenseRecord(page, phone, storeName, cardNo, orderNo, startTime, endTime, isExport);
        if (response.getData() instanceof ExpenseReVos && ((ExpenseReVos) response.getData()).getExpenseReVo() != null) {
            List<ExpenseReVo> vos = ((ExpenseReVos) response.getData()).getExpenseReVo();
            if (!CollectionUtils.isEmpty(vos) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (ExpenseReVo vo : vos) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("nickname", vo.getNickname());
                    map.put("cardNo", vo.getCardNo());
                    map.put("phone", vo.getPhone());
                    map.put("expenseMoney", vo.getExpenseMoney());
                    map.put("orderNo", vo.getOrderNo());
                    map.put("storeName", vo.getStoreName());
                    map.put("title", vo.getTitle());
                    map.put("explain", vo.getExplain());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "消费记录");
                ExcelUtils.exportExcel2("消费记录", new ExcelUtils.Header[]{
                        ExcelUtils.Header.h("index", "序号"),
                        ExcelUtils.Header.h("nickname", "用户昵称"),
                        ExcelUtils.Header.h("cardNo", "卡号"),
                        ExcelUtils.Header.h("phone", "用户电话"),
                        ExcelUtils.Header.h("expenseMoney", "消费金额"),
                        ExcelUtils.Header.h("orderNo", "订单号"),
                        ExcelUtils.Header.h("storeName", "消费门店"),
                        ExcelUtils.Header.h("title", "会员卡"),
                        ExcelUtils.Header.h("explain", "消费说明"),
                        ExcelUtils.Header.h("created", "消费时间"),
                }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }

    /**
     * 退款记录
     *
     * @param page
     * @param phone     电话号码
     * @param storeName 门店名称
     * @param cardNo    卡号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param isExport  是否导出
     * @return
     */
    @RequestMapping("refund_list")
    public Response getRefundExpense(PageQuery page, String phone, String storeName, Long cardNo, Long startTime,
                                     Long endTime, Integer isExport, HttpServletRequest request, HttpServletResponse respon) throws ParseException, IOException {
        Response response = turnoverService.getRefundExpense(page, phone, storeName, cardNo, startTime, endTime, isExport);
        if (response.getData() != null) {
            List<RefundVo> refundVos = (List<RefundVo>) response.getData();
            if (!CollectionUtils.isEmpty(refundVos) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (RefundVo vo : refundVos) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("nickname", vo.getNickname());
                    map.put("cardNo", vo.getCardNo());
                    map.put("phone", vo.getPhone());
                    map.put("money", vo.getMoney());
                    map.put("storeName", vo.getStoreName());
                    map.put("title", vo.getTitle());
                    map.put("explain", vo.getExplain());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "退款记录");
                ExcelUtils.exportExcel2("退款记录",
                        new ExcelUtils.Header[]{
                                ExcelUtils.Header.h("index", "序号"),
                                ExcelUtils.Header.h("nickname", "用户昵称"),
                                ExcelUtils.Header.h("cardNo", "卡号"),
                                ExcelUtils.Header.h("phone", "用户电话"),
                                ExcelUtils.Header.h("money", "退款金额"),
                                ExcelUtils.Header.h("storeName", "退款门店"),
                                ExcelUtils.Header.h("title", "会员卡"),
                                ExcelUtils.Header.h("explain", "退款说明"),
                                ExcelUtils.Header.h("created", "退款时间"),
                        }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }


    /**
     * 退回扣款
     *
     * @param orderNo
     * @param phone
     * @return
     */
    @PostMapping("refund")
    public Response refundExpense(Long orderNo, String phone, String code,String explain) {
        if (orderNo == null || StringUtils.isBlank(code)) {
            return ResponseFactory.errMissingParameter();
        }
        return turnoverService.refundExpense(orderNo, phone ,code,explain);
    }

//    public Response refundExpense(CardRebate rebate, String password) {
//        if (rebate.getUserId() == null || rebate.getMembershipCardId() == null || rebate.getExpenseRecordId() == null ||
//                rebate.getMoney() == null || rebate.getOrderNo() == null || StringUtils.isAnyBlank(rebate.getExplain(), password)) {
//            return ResponseFactory.errMissingParameter();
//        }
//        return turnoverService.refundExpense(rebate, password);
//    }


}
