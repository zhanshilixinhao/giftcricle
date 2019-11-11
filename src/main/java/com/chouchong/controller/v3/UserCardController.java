package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.v3.UserCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/11/11
 */
@RestController
@RequestMapping("manage/v3/userCard")
public class UserCardController {

    @Autowired
    private UserCardService userCardService;

    /**
     * 获取用户会员卡列表
     * @param page
     * @param cardNo 卡号
     * @param phone 用户电话
     * @return
     */
    @PostMapping("list")
    public Response getUserCardList(PageQuery page, String cardNo, String phone){
        return userCardService.getUserCardList(page,cardNo,phone);
    }

    /**
     * 用户会员卡充值记录和消费记录
     * @param userId 用户id
     * @return
     */
    @PostMapping("info")
    public Response cardInfo(Integer userId,Integer cardId){
        if ( userId == null || cardId == null){
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.cardInfo(userId,cardId);
    }


}
