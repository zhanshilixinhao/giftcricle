package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.iwant.withdraw.UserWithdraw;
import com.chouchong.service.iwant.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/24
 **/

@RestController
@RequestMapping("manage/withdraw")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    /**
     * 获取用户提现列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("list")
    public Response getWithdrawList(PageQuery page, String search) {
        return withdrawService.getWithdrawList(page, search);
    }

    /**
     * 获取所有的银行列表
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("bank")
    public Response getBankList() {
        return withdrawService.getBankList();
    }

    /**
     * 处理提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @PostMapping("handle")
    public Response handleUserWithdraw(UserWithdraw userWithdraw) {
        if (userWithdraw == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (userWithdraw.getStatus() == null || userWithdraw.getDescribe() == null || userWithdraw.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return withdrawService.handleUserWithdraw(userWithdraw);
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
        return withdrawService.handleBeginWithdraw(id);
    }
}
