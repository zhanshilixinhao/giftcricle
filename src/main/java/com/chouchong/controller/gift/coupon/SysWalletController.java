package com.chouchong.controller.gift.coupon;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.iwant.withdraw.UserWithdraw;
import com.chouchong.entity.webUser.SysAdminWithdraw;
import com.chouchong.service.gift.coupon.SysWalletService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/4/8 21:08
 */
@RestController
@RequestMapping("manage/sys/wallet")
public class SysWalletController {

    @Autowired
    private SysWalletService sysWalletService;

    /**
     * 后台用户钱包详情
     *
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @PostMapping("detail")
    public Response getAdminWalletDetail(String token) {
        return sysWalletService.getAdminWalletDetail(token);
    }


    /**
     * 后台商户提现记录
     *
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @PostMapping("record")
    public Response sysWithdrawRecord(PageQuery page, String token) {
        return sysWalletService.sysWithdrawRecord(page, token);
    }


    /**
     * 商户添加提现
     *
     * @param token
     * @param withdraw
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @PostMapping("add")
    public Response addWithRecord(String token, SysAdminWithdraw withdraw) {
        if (StringUtils.isAnyBlank(withdraw.getBankName(), withdraw.getDepositBank(), withdraw.getCardHolder(), withdraw.getCardNo()) ||
                withdraw.getAmount() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return sysWalletService.addWithRecord(token, withdraw);
    }

    /**********************************************************商家提现处理***************************************************************/


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
    @PostMapping("withdraw_list")
    public Response getMerchantsWithdrawList(PageQuery page, String username, Byte status) {
        return sysWalletService.getMerchantsWithdrawList(page, username, status);
    }




    /**
     * 处理商家提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("handle")
    public Response handleUserWithdraw(SysAdminWithdraw withdraw) {
        if (withdraw == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (withdraw.getStatus() == null || withdraw.getDescribe() == null || withdraw.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return sysWalletService.handleUserWithdraw(withdraw);
    }

    /**
     * 处理提现状态(处理中)
     *
     * @param: [id 提现记录id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("status")
    public Response handleBeginWithdraw(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return sysWalletService.handleBeginWithdraw(id);
    }

}
