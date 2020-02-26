package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.MembershipCard;
import com.chouchong.service.v3.CardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/11/6
 */
@RequestMapping("manage/v3/card")
@RestController
public class CardController {

    @Autowired
    private CardService cardService;

    /**
     * 获取会员卡列表
     *
     * @param cardNo 卡号
     * @param title  标题
     * @param page
     * @return
     */
    @PostMapping("list")
    public Response getCardList(Long cardNo, String title, PageQuery page) {
        return cardService.getCardList(cardNo, title, page);
    }
//
//    /**
//     * 扫码获取会员卡详情
//     *
//     * @param qrcode 二维码
//     * @return
//     */
//    @PostMapping("detailByQrcode")
//    public Response detailByQrcode(String qrcode) {
//        return cardService.detailByQrcode(qrcode);
//    }

    /**
     * 获取会员卡列表(分店)
     *
     * @param cardNo 卡号
     * @param title  标题
     * @param type   1 不分页
     * @param page
     * @return
     */
    @PostMapping("store_card")
    public Response getCardList1(Long cardNo, String title, PageQuery page, Integer type) {
        return cardService.getCardList1(cardNo, title, page, type);
    }


    /**
     * 添加会员卡
     *
     * @param card
     * @return
     */
    @PostMapping("add")
    public Response addCard(MembershipCard card, String eventIds) {
        if (StringUtils.isAnyBlank(card.getTitle(), card.getColour(), card.getLogo(), card.getStoreIds(), eventIds)) {
            return ResponseFactory.errMissingParameter();
        }
        if (card.getSummary() == null || card.getType() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardService.addCard(card, eventIds);
    }


    /**
     * 修改会员卡
     *
     * @param card
     * @return
     */
    @PostMapping("update")
    public Response updateCard(MembershipCard card, String eventIds) {
        if (StringUtils.isAnyBlank(card.getTitle(), card.getColour(), card.getLogo(), card.getStoreIds(), eventIds)) {
            return ResponseFactory.errMissingParameter();
        }
        if (card.getSummary() == null || card.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardService.updateCard(card, eventIds);
    }

    /**
     * 删除会员卡
     *
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("delete")
    public Response deleteCard(Integer cardId) {
        if (cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardService.deleteCard(cardId);
    }


    /**
     * 会员卡详情
     *
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("detail")
    public Response detailCard(Integer cardId) {
        if (cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardService.detailCard(cardId);
    }

    /**
     * 用户会员卡详情
     *
     * @param cardId 会员卡id
     * @param userId 用户id
     * @return
     */
    @PostMapping("card_detail")
    public Response detailUserCard(Integer cardId,Integer userId) {
        if (cardId == null || userId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardService.detailUserCard(cardId,userId);
    }

    /**
     * 获取所有可选门店
     *
     * @return
     */
    @PostMapping("all_store")
    public Response allStoreList() {
        return cardService.allStoreList();
    }


    /**
     * 获取自己创建的所有活动
     *
     * @return
     */
    @PostMapping("all_event")
    public Response allEventList() {
        return cardService.allEventList();
    }

}
