package com.chouchong.service.gift.coupon;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.webUser.SysAdminWithdraw;

/**
 * @author linqin
 * @date 2019/4/8 21:09
 */

public interface SysWalletService {

    /**
     * 后台用户钱包详情
     *
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    Response getAdminWalletDetail(String token);



    /**
     * 后台商户提现记录
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    Response sysWithdrawRecord(PageQuery page, String token);

    /**
     * 商户添加提现
     * @param token
     * @param withdraw
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    Response addWithRecord(String token, SysAdminWithdraw withdraw);


    /**
     * 商家提现列表
     *
     * @param page
     * @param username
     * @param status
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    Response getMerchantsWithdrawList(PageQuery page, String username, Byte status);



    /**
     * 处理商家提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response handleUserWithdraw(SysAdminWithdraw withdraw);


    /**
     * 处理提现状态(处理中)
     *
     * @param: [id 提现记录id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response handleBeginWithdraw(Integer id);
}
