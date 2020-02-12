package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.*;
import com.chouchong.dao.webUser.SysAdminRoleMapper;
import com.chouchong.entity.v3.*;
import com.chouchong.service.v3.CardEventService;
import com.chouchong.service.v3.vo.EventVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/8
 */
@Service
public class CardEventServiceImpl implements CardEventService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private MemberEventMapper memberEventMapper;

    @Autowired
    private SysAdminRoleMapper sysAdminRoleMapper;

    @Autowired
    private CardGradeMapper cardGradeMapper;

    @Autowired
    private MemberCardGradeMapper memberCardGradeMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private MembershipCardMapper membershipCardMapper;

    @Autowired
    private MemberCardMapper memberCardMapper;

    /**
     * 获取活动列表
     *
     * @param title 标题
     * @param type  类型
     * @param page
     * @return
     */
    @Override
    public Response getCardEventList(String title, Byte type, PageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            List<EventVo> eventVos = memberEventMapper.selectByAll(adminId, title, type);
            PageInfo pageInfo = new PageInfo<>(eventVos);
            return ResponseFactory.page(eventVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else {
            // 礼遇圈（所有礼遇圈添加的可看）
            List<Integer> adminIds = sysAdminRoleMapper.selectIdByRoleId(webUserInfo.getRoleId());
            List<EventVo> eventVos = memberEventMapper.selectAllByAdminIds(adminIds, title, type);
            PageInfo pageInfo = new PageInfo<>(eventVos);
            return ResponseFactory.page(eventVos, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }
    }


    /**
     * 添加活动
     *
     * @param event
     * @return
     */
    @Override
    public Response addCardEvent(MemberEvent event) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        MemberEvent ev = new MemberEvent();
        ev.setTitle(event.getTitle());
        if (StringUtils.isEmpty(event.getSummary())) {
            ev.setSummary(event.getTitle());
        } else {
            ev.setSummary(event.getSummary());
        }
        ev.setRechargeMoney(event.getRechargeMoney());
        if (event.getSendMoney() == null){
            event.setSendMoney(new BigDecimal("0"));
        }
        ev.setSendMoney(event.getSendMoney());
        ev.setTargetId(event.getTargetId());
        ev.setAdminId(webUserInfo.getSysAdmin().getId());
        ev.setType(event.getType());
        ev.setStatus(event.getStatus());
        if (event.getScale() != null) {
            float num = (float) (Math.round(event.getScale() * 0.01 * 1000)) / 1000;
            ev.setScale(num);
        }
        int insert = memberEventMapper.insert(ev);
        if (insert < 1) {
            return ResponseFactory.err("添加失败！");
        }
        return ResponseFactory.sucData(ev.getId());
    }


    /**
     * 修改活动
     *
     * @param event
     * @return
     */
    @Override
    public Response updateCardEvent(MemberEvent event) {
        MemberEvent ev = memberEventMapper.selectByPrimaryKey(event.getId());
        if (ev == null) {
            return ResponseFactory.err("该活动不存在");
        }
        ev.setTitle(event.getTitle());
        ev.setSummary(event.getSummary());
        ev.setRechargeMoney(event.getRechargeMoney());
        if (event.getSendMoney() == null){
            event.setSendMoney(new BigDecimal("0"));
        }
        ev.setSendMoney(event.getSendMoney());
        ev.setTargetId(event.getTargetId());
        ev.setType(event.getType());
        int i = memberEventMapper.updateByPrimaryKeySelective(ev);
        if (i < 1) {
            return ResponseFactory.err("修改失败！");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除活动
     *
     * @param eventId 活动id
     * @return
     */
    @Override
    public Response deleteCardEvent(Integer eventId) {
        int i = memberEventMapper.deleteByPrimaryKey(eventId);
        if (i < 1) {
            return ResponseFactory.err("删除失败！");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 活动详情
     *
     * @param eventId 活动id
     * @return
     */
    @Override
    public Response detailCardEvent(Integer eventId) {
        MemberEvent ev = memberEventMapper.selectByPrimaryKey(eventId);
        return ResponseFactory.sucData(ev);
    }


    /**
     * 会员卡活动列表
     *
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response cardEvent(Integer cardId) {
        List<EventVo> eventVos = memberEventMapper.selectByCardId(cardId);
        return ResponseFactory.sucData(eventVos);
    }

    /**
     * 门店会员卡活动列表
     *
     * @return
     */
    @Override
    public Response storeCardEvent() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 分店adminId
        Integer adminId = null;
        // 礼遇圈后台登录，所有活动
        if (webUserInfo.getRoleId() == 2 || webUserInfo.getRoleId() == 1) {
            List<MemberEvent> list1 = memberCardMapper.selectByCardId();
            return ResponseFactory.sucData(list1);
        }
        // 公司和门店（公司创建和礼遇圈活动）
        if (webUserInfo.getRoleId() == 3) {
            adminId = webUserInfo.getSysAdmin().getId();
        } else {
            adminId = webUserInfo.getSysAdmin().getCreateAdminId();
        }
        List<Integer> list = new ArrayList<>();
        List<MembershipCard> cardList = membershipCardMapper.selectByAdminId(adminId);
        if (!CollectionUtils.isEmpty(cardList)) {
            for (MembershipCard card : cardList) {
                list.add(card.getId());
            }
        }
        if (list.size() == 0) {
            return ResponseFactory.suc();
        }
        List<MemberEvent> list1 = memberCardMapper.selectByCardIds(list);
        return ResponseFactory.sucData(list1);
    }


    //****************************************会员卡等级***********************************************************/

    /**
     * 获取会员卡等级列表
     *
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response getCardGradeList(Integer cardId) {
        List<CardGrade> grades = memberCardGradeMapper.selectByCardId(cardId);
        return ResponseFactory.sucData(grades);
    }

    /**
     * 添加会员卡等级
     *
     * @param grade
     * @return
     */
    @Override
    public Response addCardGrade(CardGrade grade, Integer cardId) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        CardGrade cardGrade = new CardGrade();
        cardGrade.setTitle(grade.getTitle());
        cardGrade.setSummary(grade.getSummary());
        cardGrade.setGrade(grade.getGrade());
        cardGrade.setAdminId(webUserInfo.getSysAdmin().getId());
        int insert = cardGradeMapper.insert(cardGrade);
        if (insert < 1) {
            return ResponseFactory.err("添加失败！");
        }
        MemberCardGrade memberCardGrade = new MemberCardGrade();
        memberCardGrade.setMembershipCardId(cardId);
        memberCardGrade.setGradeId(cardGrade.getId());
        int insert1 = memberCardGradeMapper.insert(memberCardGrade);
        if (insert1 < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改会员卡等级
     *
     * @param grade
     * @return
     */
    @Override
    public Response updateCardGrade(CardGrade grade) {
        CardGrade cardGrade = cardGradeMapper.selectByPrimaryKey(grade.getId());
        if (cardGrade == null) {
            return ResponseFactory.err("该等级不存在");
        }
        cardGrade.setTitle(grade.getTitle());
        cardGrade.setSummary(grade.getSummary());
        cardGrade.setGrade(grade.getGrade());
        int i = cardGradeMapper.updateByPrimaryKeySelective(cardGrade);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除会员卡等级
     *
     * @param gradeId 等级id
     * @return
     */
    @Override
    public Response deleteCardGrade(Integer gradeId) {
        int i = cardGradeMapper.deleteByPrimaryKey(gradeId);
        if (i < 1) {
            return ResponseFactory.err("删除失败！");
        }
        int i1 = memberCardGradeMapper.deleteByGradeId(gradeId);
        if (i1 < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


}
