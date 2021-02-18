package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.*;
import com.chouchong.dao.v4.ActivityMapper;
import com.chouchong.dao.v4.PrivilegeCouponsMapper;
import com.chouchong.dao.webUser.SysAdminRoleMapper;
import com.chouchong.entity.v3.*;
import com.chouchong.entity.v4.PrivilegeCoupons;
import com.chouchong.service.v3.CardEventService;
import com.chouchong.service.v3.vo.EventVo;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v3.vo.UserCardVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private ActivityMapper activityMapper;

    @Autowired
    private ElectronicCouponsMapper electronicCouponsMapper;

    @Resource
    private PrivilegeCouponsMapper privilegeCouponsMapper;

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
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        // 超级管理员和商家
        if (webUserInfo.getRoleId() != 2) {
            if (webUserInfo.getRoleId() == 3) {
                adminId = webUserInfo.getSysAdmin().getId();
            }
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            Example example = new Example(MemberEvent.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("adminId", adminId);
            if (title!=null&&title!="") {
                criteria.andLike("title", "%"+title+"%");
            }
            if (type!=null) {
                criteria.andEqualTo("type", type);
            }
            List<MemberEvent> memberEvents = activityMapper.selectByExample(example);
            ArrayList<EventVo> list = new ArrayList<>();
            for (MemberEvent memberEvent : memberEvents) {
                EventVo eventVo = new EventVo();
                eventVo.setId(memberEvent.getId());
                eventVo.setTitle(memberEvent.getTitle());
                eventVo.setType(memberEvent.getType());
                eventVo.setAdminId(memberEvent.getAdminId());
                eventVo.setStatus(memberEvent.getStatus());
                eventVo.setRechargeMoney(memberEvent.getRechargeMoney());
                eventVo.setSendMoney(memberEvent.getSendMoney());
                eventVo.setSummary(memberEvent.getSummary());
                if (memberEvent.getTargetId()!=null) {
                    PrivilegeCoupons privilegeCoupons = privilegeCouponsMapper.selectByPrimaryKey(memberEvent.getTargetId());
                    if (privilegeCoupons!=null) {
                        eventVo.setTargetId(privilegeCoupons.getId());
                        eventVo.setTargetName(privilegeCoupons.getTitle());
                        eventVo.setQuantity(memberEvent.getQuantity());
                    }
                }
                List<StoreVo> stores = new ArrayList<>();
                if (memberEvent.getStoreIds()!=null) {
                    String[] split = memberEvent.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store);
                    }
                    eventVo.setStores(stores);
                }
                list.add(eventVo);
            }


            /*List<EventVo> eventVos = memberEventMapper.selectByAll(adminId, title, type);
            for (EventVo eventVo : eventVos) {
                List<StoreVo> stores = new ArrayList<>();
                if (eventVo.getStoreIds()!=null) {
                    String[] split = eventVo.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store);
                    }
                    eventVo.setStores(stores);
                }
            }*/
            int i = activityMapper.selectCountByExample(example);
            PageInfo pageInfo = new PageInfo<>(list);
            return ResponseFactory.page(list, i, pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else {
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            // 礼遇圈（所有礼遇圈添加的可看）
            List<Integer> adminIds = sysAdminRoleMapper.selectIdByRoleId(webUserInfo.getRoleId());
            List<EventVo> eventVos = memberEventMapper.selectAllByAdminIds(adminIds, title, type);
            for (EventVo eventVo : eventVos) {
                List<StoreVo> stores = new ArrayList<>();
                if (eventVo.getStoreIds()!=null) {
                    String[] split = eventVo.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo store = storeMapper.selectById(Integer.parseInt(s));
                        stores.add(store);
                    }
                    eventVo.setStores(stores);
                }
            }

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
            ev.setSendMoney(new BigDecimal("0"));
        }else {
            ev.setSendMoney(event.getSendMoney());
        }
        ev.setTargetId(event.getTargetId());
        ev.setAdminId(webUserInfo.getSysAdmin().getId());
        ev.setType(event.getType());
        ev.setStatus(event.getStatus());
        ev.setQuantity(event.getQuantity());
        ev.setStoreIds(event.getStoreIds());
        ev.setCreated(new Date());
        ev.setUpdated(ev.getCreated());
        if (event.getScale() != null) {
            float num = (float) (Math.round(event.getScale() * 0.01 * 1000)) / 1000;
            ev.setScale(num);
        }
        //int insert = memberEventMapper.insert(ev);
        int insert = activityMapper.insertSelective(ev);
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
        //MemberEvent ev = memberEventMapper.selectByPrimaryKey(event.getId());
        MemberEvent ev = activityMapper.selectByPrimaryKey(event.getId());
        if (ev == null) {
            return ResponseFactory.err("该活动不存在");
        }
        ev.setId(ev.getId());
        ev.setTitle(event.getTitle());
        ev.setSummary(event.getSummary());
        ev.setRechargeMoney(event.getRechargeMoney());
        if (event.getSendMoney() == null){
            ev.setSendMoney(new BigDecimal("0"));
        }else {
            ev.setSendMoney(event.getSendMoney());
        }
        ev.setTargetId(event.getTargetId());
        ev.setAdminId(ev.getAdminId());
        ev.setType(event.getType());
        ev.setStatus(ev.getStatus());
        ev.setCreated(ev.getCreated());
        ev.setScale(ev.getScale());
        ev.setQuantity(event.getQuantity());
        ev.setStoreIds(event.getStoreIds());
        ev.setUpdated(new Date());
        //int i = memberEventMapper.updateByPrimaryKey(ev);
        int i = activityMapper.updateByPrimaryKeySelective(ev);
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
        //MemberEvent ev = memberEventMapper.selectByPrimaryKey(eventId);
        MemberEvent ev = activityMapper.selectByPrimaryKey(eventId);
        if (ev != null ){
            if ((ev.getType() == 2 || ev.getType() == 5) && ev.getTargetId() != null){
                ElectronicCoupons coupons = electronicCouponsMapper.selectByKey(ev.getTargetId());
                if (coupons != null){
                    ev.setCouponTitle(coupons.getTitle());
                }
            }
        }
        return ResponseFactory.sucData(ev);
    }


    /**
     * 会员卡活动列表
     *
     * @param cardId 会员卡id
     * @return
     */
    @Override
    public Response cardEvent() {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Example example = new Example(MemberEvent.class);
        example.createCriteria().andEqualTo("adminId", webUserInfo.getSysAdmin().getCreateAdminId());
        List<MemberEvent> memberEvents = activityMapper.selectByExample(example);
        Store store = storeMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
        ArrayList<MemberEvent> list = new ArrayList<>();
        for (MemberEvent memberEvent : memberEvents) {
            if (memberEvent.getStoreIds()!=null) {
                String[] split = memberEvent.getStoreIds().split(",");
                for (String s : split) {
                    if (s.equals(store.getId().toString())) {
                        list.add(memberEvent);
                    }
                }
            }
        }
        return ResponseFactory.sucData(list);
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
