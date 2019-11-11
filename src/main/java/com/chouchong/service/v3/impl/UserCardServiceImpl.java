package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.MemberChargeRecordMapper;
import com.chouchong.dao.v3.MemberExpenseRecordMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v3.UserMemberCardMapper;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.UserCardService;
import com.chouchong.service.v3.vo.ChargeVo;
import com.chouchong.service.v3.vo.ExpenseVo;
import com.chouchong.service.v3.vo.RecordVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/11
 */
@Service
public class UserCardServiceImpl implements UserCardService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private UserMemberCardMapper userMemberCardMapper;

    @Autowired
    private MemberChargeRecordMapper memberChargeRecordMapper;

    @Autowired
    private MemberExpenseRecordMapper memberExpenseRecordMapper;

    /**
     * 获取用户会员卡列表
     *
     * @param page
     * @param cardNo
     * @param phone  用户电话
     * @return
     */
    @Override
    public Response getUserCardList(PageQuery page, String cardNo, String phone) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<UserCardVo> list = userMemberCardMapper.selectBySearch(cardNo, phone, adminId);
        PageInfo pageInfo = new PageInfo<>(list);
        return ResponseFactory.page(list, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 用户会员卡充值记录
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Response cardInfo(Integer userId, Integer cardId) {
        RecordVo record = new RecordVo();
        List<ChargeVo> chargeVos = memberChargeRecordMapper.selectByUserIdCardId(userId, cardId);
        List<ExpenseVo> expenseVos = memberExpenseRecordMapper.selectByUserIdCardId(userId, cardId);
        record.setCharges(chargeVos);
        record.setExpenses(expenseVos);
        return ResponseFactory.sucData(record);
    }


}
