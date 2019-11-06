package com.chouchong.service.v3.impl;

import com.chouchong.common.OrderHelper;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.MembershipCardMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.entity.v3.MembershipCard;
import com.chouchong.service.v3.CardService;
import com.chouchong.service.v3.vo.CardVo;
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

    /**
     * 获取会员卡列表
     * @param cardNo 卡号
     * @param title 标题
     * @param page
     * @return
     */
    @Override
    public Response getCardList(Long cardNo, String title, PageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        Integer cardId = null;
        if (webUserInfo.getRoleId() == 2) {
            cardId = 0;
        } else if (webUserInfo.getRoleId() == 3 || webUserInfo.getRoleId() == 4) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<CardVo> cardVos = membershipCardMapper.selectBySearch(adminId, cardId, cardNo, title);
        if (!CollectionUtils.isEmpty(cardVos)){
            for (CardVo cardVo : cardVos) {
                List<StoreVo> stores = new ArrayList<>();
               if (!StringUtils.isEmpty(cardVo.getStoreIds())){
                   String[] split = cardVo.getStoreIds().split(",");
                   for (String s : split) {
                       StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                       stores.add(store);
                   }
               }
               cardVo.setStoreVos(stores);
            }
        }
        PageInfo pageInfo = new PageInfo<>(cardVos);
        return ResponseFactory.page(cardVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 添加会员卡
     * @param card
     * @return
     */
    @Override
    public Response addCard(MembershipCard card) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        MembershipCard ca = new MembershipCard();
        ca.setCardNo(orderHelper.genOrderNo(7, 9));
        ca.setTitle(card.getTitle());
        ca.setSummary(card.getSummary());
        ca.setColour(card.getColour());
        ca.setLogo(card.getLogo());
        ca.setStoreIds(card.getStoreIds());
        ca.setAdminId(webUserInfo.getSysAdmin().getId());
        ca.setType((byte)10);
        ca.setStatus((byte)1);
        int insert = membershipCardMapper.insert(ca);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改会员卡
     * @param card
     * @return
     */
    @Override
    public Response updateCard(MembershipCard card) {
        MembershipCard ca = membershipCardMapper.selectByPrimaryKey(card.getId());
        if (ca == null){
            return ResponseFactory.err("该会员卡不存在");
        }
        // 礼遇圈卡必须选礼遇圈门店
        if (card.getId() == 0 && card.getStoreIds().indexOf(0) == -1){
            return ResponseFactory.err("礼遇圈卡必须选礼遇圈门店");
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
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * 删除会员卡
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response deleteCard(Integer cardId) {
        MembershipCard ca = membershipCardMapper.selectByPrimaryKey(cardId);
        if (ca == null){
            return ResponseFactory.err("操作失败");
        }
        ca.setStatus((byte)10);
        int i = membershipCardMapper.updateByPrimaryKeySelective(ca);
        if (i < 1) {
            return ResponseFactory.err("操作失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }



    /**
     * 会员卡详情
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response detailCard(Integer cardId) {
       CardVo vo = membershipCardMapper.selectById(cardId);
       if (vo != null){
           List<StoreVo> stores = new ArrayList<>();
           if (!StringUtils.isEmpty(vo.getStoreIds())){
               String[] split = vo.getStoreIds().split(",");
               for (String s : split) {
                   StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                   stores.add(store);
               }
           }
           vo.setStoreVos(stores);
       }
       return ResponseFactory.sucData(vo);
    }


    /**
     * 获取所有可选门店
     * @return
     */
    @Override
    public Response allStoreList() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<StoreVo> storeVos = storeMapper.selectByAll(adminId);
        return ResponseFactory.sucData(storeVos);
    }

}
