package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.MemberChargeRecordMapper;
import com.chouchong.dao.v3.MemberExpenseRecordMapper;
import com.chouchong.dao.v3.StoreTurnoverMapper;
import com.chouchong.entity.v3.MemberChargeRecord;
import com.chouchong.service.v3.TurnoverService;
import com.chouchong.service.v3.vo.ChargeReVo;
import com.chouchong.service.v3.vo.ExpenseReVo;
import com.chouchong.service.v3.vo.TurnoverVo;
import com.chouchong.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/18
 */
@Service
public class TurnoverServiceImpl implements TurnoverService {

    @Autowired
    private StoreTurnoverMapper storeTurnoverMapper;

    @Autowired
    private MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    private MemberExpenseRecordMapper memberExpenseRecordMapper;

    /**
     * 获取营业额统计列表
     * @param page
     * @param eventName 活动名称
     * @param title 卡标题
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public Response getTurnoverList(PageQuery page, String eventName, String title, Long startTime, Long endTime) throws ParseException {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        if (startTime != null){
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null){
            endTime = TimeUtils.timeEnd(endTime);
        }
        List<TurnoverVo> turnoverVos =storeTurnoverMapper.selectBySearch(eventName,title,startTime,endTime);
        PageInfo pageInfo = new PageInfo<>(turnoverVos);
        return ResponseFactory.page(turnoverVos,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
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
    @Override
    public Response getChargeRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        if (startTime != null){
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null){
            endTime = TimeUtils.timeEnd(endTime);
        }
        List<ChargeReVo> chargeRes =memberChargeRecordMapper.selectBySearch(phone,storeName,cardNo,startTime,endTime);
        PageInfo pageInfo = new PageInfo<>(chargeRes);
        return ResponseFactory.page(chargeRes,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
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
    @Override
    public Response getExpenseRecord(PageQuery page, String phone, String storeName, Long cardNo, Long startTime, Long endTime) throws ParseException {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        if (startTime != null){
            startTime = TimeUtils.time(startTime);
        }
        if (endTime != null){
            endTime = TimeUtils.timeEnd(endTime);
        }
        List<ExpenseReVo> chargeRes =memberExpenseRecordMapper.selectBySearch(phone,storeName,cardNo,startTime,endTime);
        PageInfo pageInfo = new PageInfo<>(chargeRes);
        return ResponseFactory.page(chargeRes,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }
}
