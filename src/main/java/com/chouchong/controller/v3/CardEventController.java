package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.v3.CardGrade;
import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.service.v3.CardEventService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/11/8
 */
@RestController
@RequestMapping("manage/v3/cardEvent")
public class CardEventController {

    @Autowired
    private CardEventService cardEventService;


    /**
     * 获取活动列表
     *
     * @param title 标题
     * @param type  类型
     * @param page
     * @return
     */
    @PostMapping("list")
    public Response getCardEventList(String title, Byte type, PageQuery page) {
        return cardEventService.getCardEventList(title, type, page);
    }

    /**
     * 添加活动
     *
     * @param event
     * @return
     */
    @PostMapping("add")
    public Response addCardEvent(MemberEvent event) {
        if (StringUtils.isAnyBlank(event.getTitle(), event.getSummary()) || event.getRechargeMoney() == null || event.getType() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (event.getType() == 1 && event.getSendMoney() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.addCardEvent(event);
    }


    /**
     * 修改活动
     *
     * @param event
     * @return
     */
    @PostMapping("update")
    public Response updateCardEvent(MemberEvent event) {
        if (StringUtils.isAnyBlank(event.getTitle(), event.getSummary()) || event.getRechargeMoney() == null
                || event.getType() == null || event.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (event.getType() == 1 && event.getSendMoney() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.updateCardEvent(event);
    }

    /**
     * 删除活动
     * @param eventId 活动id
     * @return
     */
    @PostMapping("delete")
    public Response deleteCardEvent(Integer eventId){
        if (eventId == null){
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.deleteCardEvent(eventId);
    }

    /**
     * 活动详情
     * @param eventId 活动id
     * @return
     */
    @PostMapping("detail")
    public Response detailCardEvent(Integer eventId){
        if (eventId == null){
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.detailCardEvent(eventId);
    }


    /**
     * 会员卡活动列表
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("all_event")
    public Response cardEvent(Integer cardId){
        if (cardId == null){
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.cardEvent(cardId);
    }

 //****************************************会员卡等级***********************************************************/
    /**
     * 获取会员卡等级列表
     *
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("grade_list")
    public Response getCardGradeList(Integer cardId) {
        if (cardId == null){
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.getCardGradeList(cardId);
    }

    /**
     * 添加会员卡等级
     *
     * @param cardId 会员卡id
     * @param grade
     * @return
     */
    @PostMapping("grade_add")
    public Response addCardGrade(CardGrade grade,Integer cardId) {
        if (StringUtils.isAnyBlank(grade.getTitle(), grade.getSummary()) || grade.getGrade() == null ||cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.addCardGrade(grade,cardId);
    }


    /**
     * 修改会员卡等级
     *
     * @param grade
     * @return
     */
    @PostMapping("grade_update")
    public Response updateCardGrade(CardGrade grade) {
        if (StringUtils.isAnyBlank(grade.getTitle(), grade.getSummary()) || grade.getGrade() == null ||grade.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.updateCardGrade(grade);
    }

    /**
     * 删除会员卡等级
     * @param gradeId 等级id
     * @return
     */
    @PostMapping("grade_delete")
    public Response deleteCardGrade(Integer gradeId){
        if (gradeId == null){
            return ResponseFactory.errMissingParameter();
        }
        return cardEventService.deleteCardGrade(gradeId);
    }


}
