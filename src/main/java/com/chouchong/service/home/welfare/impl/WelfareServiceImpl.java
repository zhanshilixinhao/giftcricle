package com.chouchong.service.home.welfare.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.coupon.CouponMapper;
import com.chouchong.dao.gift.item.ItemMapper;
import com.chouchong.dao.gift.item.ItemSkuMapper;
import com.chouchong.dao.gift.virItem.VirtualItemMapper;
import com.chouchong.dao.home.WelfareMapper;
import com.chouchong.entity.gift.item.ItemSku;
import com.chouchong.entity.home.Welfare;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.home.welfare.WelfareService;
import com.chouchong.service.home.welfare.vo.ItemListVo;
import com.chouchong.service.home.welfare.vo.WelfareVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2019/2/22 15:08
 */
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class WelfareServiceImpl implements WelfareService {

    @Autowired
    private WelfareMapper welfareMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private VirtualItemMapper virtualItemMapper;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private ItemSkuMapper itemSkuMapper;

    /**
     * 获取福利列表
     *
     * @param page
     * @param search
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response getWelfareList(PageQuery page, Welfare welfare) {
        // 分页
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<WelfareVo> welfareVos = welfareMapper.selectAllBySearch(welfare);
        PageInfo pageInfo = new PageInfo<>(welfareVos);
        return ResponseFactory.page(welfareVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加福利
     *
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response addWelfare(Welfare welfare) {
        if (welfare.getStartTime().getTime() > welfare.getEndTime().getTime()) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "结束时间必须大于开始时间");
        }
        // 福利生效时间必须在福利展示之间
        if (welfare.getTargetDate().getTime() < welfare.getStartTime().getTime() || welfare.getTargetDate().getTime() > welfare.getEndTime().getTime()) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "福利生效时间必须在福利展示之间");
        }
        // 查询所有福利
        List<Welfare> welfareList = welfareMapper.selectAll();
        if (!CollectionUtils.isEmpty(welfareList)) {
            for (Welfare welfare1 : welfareList) {
                if (welfare.getStartTime().getTime() >= welfare1.getStartTime().getTime() && welfare.getStartTime().getTime() < welfare1.getEndTime().getTime()) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该时间段已经添加过福利");
                }
                if (welfare.getEndTime().getTime() > welfare1.getStartTime().getTime() && welfare.getEndTime().getTime() <= welfare1.getEndTime().getTime()) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该时间段已经添加过福利");
                }
            }
        }
        //
        welfare.setCount(welfare.getQuantity());
        int insert = welfareMapper.insert(welfare);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改福利
     *
     * @param welfare
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response updateWelfare(Welfare welfare) {
        Welfare fe = welfareMapper.selectByPrimaryKey(welfare.getId());
        if (fe == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "福利不存在");
        }
        if (welfare.getStartTime().getTime() > welfare.getEndTime().getTime()) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "结束时间必须大于开始时间");
        }
        // 福利生效时间必须在福利展示之间
        if (welfare.getTargetDate().getTime() < welfare.getStartTime().getTime() || welfare.getTargetDate().getTime() > welfare.getEndTime().getTime()) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "福利生效时间必须在福利展示之间");
        }
        // 查询所有福利
        List<Welfare> welfareList = welfareMapper.selectAll();
        if (!CollectionUtils.isEmpty(welfareList)) {
            for (Welfare welfare1 : welfareList) {
                if (welfare.getStartTime().getTime() >= welfare1.getStartTime().getTime() && welfare.getStartTime().getTime() < welfare1.getEndTime().getTime()) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该时间段已经添加过福利");
                }
                if (welfare.getEndTime().getTime() > welfare1.getStartTime().getTime() && welfare.getEndTime().getTime() <= welfare1.getEndTime().getTime()) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该时间段已经添加过福利");
                }
            }
        }
        int i = welfareMapper.updateByPrimaryKeySelective(welfare);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除福利
     *
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response deleteWelfare(Integer welfareId) {
        Welfare fe = welfareMapper.selectByPrimaryKey(welfareId);
        if (fe == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "福利不存在");
        }
        int i = welfareMapper.deleteByPrimaryKey(welfareId);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 福利物品列表
     *
     * @param type
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response getAllItemList(Byte type, PageQuery page, String title) {
        // 分页
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ItemListVo> listVos;
        if (type == 1) {
            // 商品
            listVos = itemMapper.selectByTitle(title);
        } else if (type == 2) {
            // 虚拟商品
            listVos = virtualItemMapper.selectByTitle(title);
        } else {
            // 优惠券
            WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
            listVos = couponMapper.selectByTitleAdmin(title, webUserInfo.getSysAdmin().getId());
        }
        PageInfo pageInfo = new PageInfo<>(listVos);
        return ResponseFactory.page(listVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 商品sku列表
     *
     * @param itemId 商品id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response getSkuList(Integer itemId) {
        List<ItemSku> list = itemSkuMapper.selectByItemId(itemId);
        return ResponseFactory.sucData(list);
    }

    /**
     * 详情福利
     *
     * @param welfareId 福利id
     * @return
     * @author linqin
     * @date 2019/2/22
     */
    @Override
    public Response detailWelfare(Integer welfareId) {
        Welfare fe = welfareMapper.selectByPrimaryKey(welfareId);
        if (fe == null) {
            throw new ServiceException(ErrorCode.ERROR.getCode(), "福利不存在");
        }
        return ResponseFactory.sucData(fe);
    }
}
