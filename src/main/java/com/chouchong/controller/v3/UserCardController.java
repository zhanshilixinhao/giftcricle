package com.chouchong.controller.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.common.v3.ExcelUtils;
import com.chouchong.entity.v3.UserMemberCard;
import com.chouchong.service.v3.UserCardService;
import com.chouchong.service.v3.vo.TransferVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.v3.vo.UserCardVos;
import com.chouchong.utils.BigDecimalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.POST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param page
     * @param cardNo 卡号
     * @param phone  用户电话
     * @param title  卡标题
     * @return
     */
    @RequestMapping("list")
    public Response getUserCardList(PageQuery page, String cardNo, String phone, Byte type, String title,String storeName,
                                    Integer isExport, HttpServletRequest request, HttpServletResponse respon) throws IOException {
        Response response = userCardService.getUserCardList(page, cardNo, phone, type, title,storeName, isExport);
        if (response.getData() instanceof UserCardVos && ((UserCardVos) response.getData()).getUserCardVos() != null) {
            List<UserCardVo> userCardVo = ((UserCardVos) response.getData()).getUserCardVos();
            if (!CollectionUtils.isEmpty(userCardVo) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (UserCardVo vo : userCardVo) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("nickname", vo.getNickname());
                    map.put("phone", vo.getPhone());
                    map.put("title", vo.getTitle());
                    map.put("cardNo", vo.getCardNo());
                    map.put("type", vo.getType() == 1 ? "礼遇圈卡" : vo.getType() == 10 ? "企业储值卡" : "企业活动卡");
                    map.put("balance", vo.getBalance());
                    map.put("storeName", vo.getStoreName());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "会员卡信息");
                ExcelUtils.exportExcel2("会员卡信息",
                        new ExcelUtils.Header[]{
                                ExcelUtils.Header.h("index", "序号"),
                                ExcelUtils.Header.h("nickname", "用户昵称"),
                                ExcelUtils.Header.h("phone", "用户电话"),
                                ExcelUtils.Header.h("title", "卡标题"),
                                ExcelUtils.Header.h("cardNo", "卡号"),
                                ExcelUtils.Header.h("type", "会员卡类型"),
                                ExcelUtils.Header.h("balance", "余额"),
                                ExcelUtils.Header.h("storeName", "开卡门店"),
                                ExcelUtils.Header.h("created", "开卡时间"),
                        }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }

    /**
     * 用户会员卡充值记录和消费记录
     *
     * @param userId 用户id
     * @return
     */
    @PostMapping("info")
    public Response cardInfo(Integer userId, Integer cardId) {
        if (userId == null || cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.cardInfo(userId, cardId);
    }

    /**
     * 分店获取用户会员卡列表
     *
     * @param page
     * @param cardNo 卡号
     * @param phone  用户电话
     * @param title  卡标题
     * @return
     */
    @RequestMapping("list_store")
    public Response getUserCardList1(PageQuery page, String cardNo, String phone, String title,String storeName,
                                     Integer isExport, HttpServletRequest request, HttpServletResponse respon) throws IOException {
        Response response = userCardService.getUserCardList1(page, cardNo, phone, title,storeName, isExport);
        if (response.getData() instanceof UserCardVos && ((UserCardVos) response.getData()).getUserCardVos() != null) {
            List<UserCardVo> userCardVo = ((UserCardVos) response.getData()).getUserCardVos();
            if (!CollectionUtils.isEmpty(userCardVo) && isExport != null) {
                List<Map<String, Object>> list = new ArrayList<>();
                int i = 1;
                for (UserCardVo vo : userCardVo) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("index", i++);
                    map.put("nickname", vo.getNickname());
                    map.put("phone", vo.getPhone());
                    map.put("title", vo.getTitle());
                    map.put("cardNo", vo.getCardNo());
                    map.put("type", vo.getType() == 1 ? "礼遇圈卡" : vo.getType() == 10 ? "企业储值卡" : "企业活动卡");
                    map.put("balance", vo.getBalance());
                    map.put("storeName", vo.getStoreName());
                    map.put("created", vo.getCreated());
                    list.add(map);
                }

                ExcelUtils.preExport(request, respon, "会员卡信息");
                ExcelUtils.exportExcel2("会员卡信息",
                        new ExcelUtils.Header[]{
                                ExcelUtils.Header.h("index", "序号"),
                                ExcelUtils.Header.h("nickname", "用户昵称"),
                                ExcelUtils.Header.h("phone", "用户电话"),
                                ExcelUtils.Header.h("title", "卡标题"),
                                ExcelUtils.Header.h("cardNo", "卡号"),
                                ExcelUtils.Header.h("type", "会员卡类型"),
                                ExcelUtils.Header.h("balance", "余额"),
                                ExcelUtils.Header.h("storeName", "开卡门店"),
                                ExcelUtils.Header.h("created", "开卡时间"),
                        }, list, respon.getOutputStream(), "yyyy-MM-dd HH:mm:ss");
                return null;
            }
        }
        return response;
    }


    /**
     * 分店开卡
     *
     * @param card
     * @return
     */
    @PostMapping("add")
    public Response addUserCard(UserMemberCard card) throws IOException {
        if (card.getMembershipCardId() == null || card.getPhone() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.addUserCard(card);
    }


    /**
     * 分店会员卡充值
     *
     * @param userId   用户id
     * @param cardId   会员卡id
     * @param recharge 充值金额
     * @param explain  充值说明
     * @param send     赠送金额
     * @param eventId  活动id
     * @return
     */
    @PostMapping("charge")
    public Response chargeCard(Integer userId, String phone, Integer cardId, BigDecimal recharge, String explain,
                               BigDecimal send, Integer eventId,String image) throws IOException {
        if (cardId == null || recharge == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (recharge.equals(new BigDecimal(0)) && (send == null || send.compareTo(new BigDecimal(0)) <= 0)) {
            return ResponseFactory.err("充值金额为0,赠送金额必填并且大于0");
        }
        if (userId == null && StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.chargeCard(userId, phone, cardId, recharge, explain, send, eventId,image);
    }

    /**
     * 分店消费（线下消费）
     *
     * @param userId  用户id
     * @param cardId  会员卡id
     * @param expense 消费金额
     * @param explain 消费说明
     * @return
     */
    @PostMapping("expense")
    public Response expenseCard(Integer userId, String phone, Integer cardId, BigDecimal expense,
                                String explain, String password) throws IOException {
        if (cardId == null || expense == null || StringUtils.isBlank(password)) {
            return ResponseFactory.errMissingParameter();
        }
        if (userId == null && StringUtils.isBlank(phone)) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.expenseCard(userId, phone, cardId, expense, explain, password);
    }

    /**
     * 修改用户的会员卡等级
     *
     * @param userId  用户id
     * @param cardId  会员卡id
     * @param gradeId 会员卡等级id
     * @return
     */
    @PostMapping("update_grade")
    public Response updateUserGrade(Integer userId, Integer cardId, Integer gradeId) {
        if (userId == null || cardId == null || gradeId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.updateCardGrade(userId, cardId, gradeId);
    }

    /**
     * 查询活动卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("card_detail")
    public Response getEventCardDetail(Integer userId, Integer cardId) {
        if (userId == null || cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.getEventCardDetail(userId, cardId);
    }

    /**
     * 查询所有会员卡充值赠送金额的余额
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("all_card_detail")
    public Response getCardDetail(Integer userId, Integer cardId) {
        if (userId == null || cardId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.getCardDetail(userId, cardId);
    }


    /**
     * 退卡
     *
     * @param userId 用户id
     * @param cardId 会员卡id
     * @return
     */
    @PostMapping("back_card")
    public Response backCard(Integer userId, Integer cardId, BigDecimal capital,BigDecimal send) {
        if (userId == null || cardId == null || capital == null ||send == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.backCard(userId, cardId,capital,send);
    }


    /**
     * 改变活动卡充值赠送金额状态
     *
     * @param storeMemberEventId 会员卡id
     * @return
     */
    @PostMapping("card_status")
    public Response getEventCardStatus(Integer storeMemberEventId) {
        if (storeMemberEventId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.getEventCardStatus(storeMemberEventId);
    }


    /******************************************************小程序管理端*****************************************************/

    /**
     * 分店获取用户会员卡列表(小程序端)
     *
     * @param page
     * @param phone 用户电话
     * @return
     */
    @PostMapping("user_card")
    public Response getUserCardListManage(PageQuery page, String phone) {
        return userCardService.getUserCardListManage(page, phone);
    }

    /**
     * 小程序管理端会员详情
     *
     * @return
     */
    @PostMapping("user_card_detail")
    public Response userCardDetail(Integer userId) {
        if (userId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return userCardService.userCardDetail(userId);
    }



}
