package com.chouchong.service.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.iwant.withdraw.UserWithdraw;

public interface WithdrawService {
    /**
     * 获取用户提现列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response getWithdrawList(PageQuery page, String search);

    /**
     * 获取所有的银行列表
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response getBankList();

    /**
     * 处理提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    Response handleUserWithdraw(UserWithdraw userWithdraw);

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
