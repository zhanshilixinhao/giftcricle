package com.chouchong.service.v3.impl;

import com.chouchong.common.OrderHelper;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.v3.MemberCardMapper;
import com.chouchong.dao.v3.MemberEventMapper;
import com.chouchong.dao.v3.MembershipCardMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminRoleMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.MemberCard;
import com.chouchong.entity.v3.MemberEvent;
import com.chouchong.entity.v3.MembershipCard;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.webUser.SysAdminRole;
import com.chouchong.service.v3.CardService;
import com.chouchong.service.v3.vo.CardVo;
import com.chouchong.service.v3.vo.EventVo;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/6
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private OrderHelper orderHelper;

    @Autowired
    private MemberEventMapper memberEventMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 获取会员卡列表
     *
     * @param cardNo 卡号
     * @param title  标题
     * @param page
     * @return
     */
    @Override
    public Response getCardList(Long cardNo, String title, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        Integer cardId = null;
//        if (webUserInfo.getRoleId() == 2) {
//            cardId = 0;
//        } else
        if (webUserInfo.getRoleId() == 3 || webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<CardVo> cardVos = membershipCardMapper.selectBySearch(adminId, cardId, cardNo, title);
        if (!CollectionUtils.isEmpty(cardVos)) {
            for (CardVo cardVo : cardVos) {
                List<StoreVo> stores = new ArrayList<>();
                if (!StringUtils.isEmpty(cardVo.getStoreIds())) {
                    String[] split = cardVo.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store);
                    }
                }
                cardVo.setStoreVos(stores);
                // 会员卡活动
                List<EventVo> eventVos = memberEventMapper.selectByCardId(cardVo.getId());
                cardVo.setEventVos(eventVos);
            }
        }
        PageInfo pageInfo = new PageInfo<>(cardVos);
        return ResponseFactory.page(cardVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 获取会员卡列表(分店)
     *
     * @param cardNo 卡号
     * @param title  标题
     * @param type   1 不分页
     * @param page
     * @return
     */
    @Override
    public Response getCardList1(Long cardNo, String title, PageQuery page, Integer type) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
//        分店adminId
        Integer adminId = webUserInfo.getSysAdmin().getId();
        // 创建者adminId(商家adminId)
        Integer createdAdminId = webUserInfo.getSysAdmin().getCreateAdminId();
        // 查询门店id
        Store store = storeMapper.selectByAdminId(adminId);
        if (store == null) {
            return ResponseFactory.suc();
        }
        List<Integer> list = new ArrayList<>();
        List<MembershipCard> cardList = membershipCardMapper.selectByAdminId(createdAdminId);
        if (!CollectionUtils.isEmpty(cardList)) {
            for (MembershipCard card : cardList) {
                // 分店卡
                String[] strings = card.getStoreIds().split(",");
                for (String string : strings) {
                    if (string.equals(store.getId().toString())) {
                        list.add(card.getId());
                    }
                }
            }
        }
        if (type == null || type != 1) {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
        }
        if (list.size() == 0) {
            return ResponseFactory.suc();
        }
        List<CardVo> cardVos = membershipCardMapper.selectBySearchStore(list, cardNo, title);
        if (!CollectionUtils.isEmpty(cardVos)) {
            for (CardVo cardVo : cardVos) {
                List<StoreVo> stores = new ArrayList<>();
                if (!StringUtils.isEmpty(cardVo.getStoreIds())) {
                    String[] split = cardVo.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store1 = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store1);
                    }
                }
                cardVo.setStoreVos(stores);
                // 会员卡活动
                List<EventVo> eventVos = memberEventMapper.selectByCardId(cardVo.getId());
                cardVo.setEventVos(eventVos);
            }
        }
        if (type == null || type != 1) {
            PageInfo pageInfo = new PageInfo<>(cardVos);
            return ResponseFactory.page(cardVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());

        } else {
            return ResponseFactory.sucData(cardVos);
        }
    }

    /**
     * 添加会员卡
     *
     * @param card
     * @return
     */
    @Override
    public Response addCard(MembershipCard card, String eventIds) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        MembershipCard ca = new MembershipCard();
        ca.setCardNo(orderHelper.genOrderNo(7, 9));
        ca.setTitle(card.getTitle());
        ca.setSummary(card.getSummary());
        ca.setColour(card.getColour());
        ca.setLogo(card.getLogo());
        ca.setStoreIds(card.getStoreIds());
        ca.setAdminId(webUserInfo.getSysAdmin().getId());
        ca.setType((byte) 10);
        ca.setStatus((byte) 1);
        int insert = membershipCardMapper.insert(ca);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        // 添加会员卡活动
        String[] split = eventIds.split(",");
        for (String s : split) {
            MemberCard card1 = new MemberCard();
            card1.setMembersEventId(Integer.parseInt(s));
            card1.setMembershipCardId(ca.getId());
            int insert1 = memberCardMapper.insert(card1);
            if (insert1 < 1) {
                return ResponseFactory.err("失败！");
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改会员卡
     *
     * @param card
     * @return
     */
    @Override
    public Response updateCard(MembershipCard card, String eventIds) {
        MembershipCard ca = membershipCardMapper.selectByPrimaryKey(card.getId());
        if (ca == null) {
            return ResponseFactory.err("该会员卡不存在");
        }
        // 礼遇圈卡必须选礼遇圈门店
        if (card.getId() == 0) {
            boolean contains = card.getStoreIds().contains("0");
            if (!contains) {
              return  ResponseFactory.err("礼遇圈卡必须选礼遇圈门店");
            }
//            String[] strings = card.getStoreIds().split(",");
//            for (String string : strings) {
//                if ("0".equals(string)) {
//                    break;
//                } else {
//                    return ResponseFactory.err("礼遇圈卡必须选礼遇圈门店");
//                }
//            }
        }
        ca.setTitle(card.getTitle());
        ca.setSummary(card.getSummary());
        ca.setColour(card.getColour());
        ca.setLogo(card.getLogo());
        ca.setStoreIds(card.getStoreIds());
        int i = membershipCardMapper.updateByPrimaryKeySelective(ca);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        // 添加会员卡活动
        memberCardMapper.deleteByCardId(card.getId());
        String[] split = eventIds.split(",");
        for (String s : split) {
            MemberCard card1 = new MemberCard();
            card1.setMembersEventId(Integer.parseInt(s));
            card1.setMembershipCardId(ca.getId());
            int insert1 = memberCardMapper.insert(card1);
            if (insert1 < 1) {
                return ResponseFactory.err("失败！");
            }
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * 删除会员卡
     *
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response deleteCard(Integer cardId) {
        MembershipCard ca = membershipCardMapper.selectByPrimaryKey(cardId);
        if (ca == null) {
            return ResponseFactory.err("操作失败");
        }
        ca.setStatus((byte) 10);
        int i = membershipCardMapper.updateByPrimaryKeySelective(ca);
        if (i < 1) {
            return ResponseFactory.err("操作失败");
        }
        memberCardMapper.deleteByCardId(cardId);
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 会员卡详情
     *
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response detailCard(Integer cardId) {
        CardVo vo = membershipCardMapper.selectById(cardId);
        if (vo != null) {
            // 会员卡活动
            List<EventVo> eventVos = memberEventMapper.selectByCardId(vo.getId());
            if (!StringUtils.isEmpty(eventVos)) {
                StringBuilder eventIds = new StringBuilder();
                String substring = null;
                for (EventVo eventVo : eventVos) {
                    eventIds.append(eventVo.getId()).append(",");
                    if ((",".equals(eventIds.substring(eventIds.length() - 1)))) {
                        substring = eventIds.substring(0, eventIds.length() - 1);
                    }
                }
                vo.setEventIds(substring);
            }
        }
        return ResponseFactory.sucData(vo);
    }


    /**
     * 获取所有可选门店
     *
     * @return
     */
    @Override
    public Response allStoreList() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer merchantId = null;
        if (webUserInfo.getRoleId() == 3) {
            Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
            if (merchant != null) {
                merchantId = merchant.getId();
            }
        }
        List<StoreVo> storeVos = storeMapper.selectByAll(merchantId);
        return ResponseFactory.sucData(storeVos);
    }

    /**
     * 获取自己创建的所有活动
     *
     * @return
     */
    @Override
    public Response allEventList() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            List<EventVo> eventVos = memberEventMapper.selectByAll(adminId, null, null);
            return ResponseFactory.sucData(eventVos);
        } else {
            // 礼遇圈（所有礼遇圈添加的可看）
            List<Integer> adminIds = sysAdminRoleMapper.selectIdByRoleId(webUserInfo.getRoleId());
            List<EventVo> eventVos = memberEventMapper.selectAllByAdminIds(adminIds, null, null);
            return ResponseFactory.sucData(eventVos);
        }
    }


}
