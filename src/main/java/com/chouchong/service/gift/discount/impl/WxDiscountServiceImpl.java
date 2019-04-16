package com.chouchong.service.gift.discount.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.bpItem.BpItemMapper;
import com.chouchong.dao.home.WxDiscountMapper;
import com.chouchong.entity.gift.bpItem.BpItem;
import com.chouchong.entity.home.WxDiscount;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.discount.WxDiscountService;
import com.chouchong.service.gift.discount.vo.WxDiscountVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linqin
 * @date 2019/3/19 10:46
 */
@Service
@Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
public class WxDiscountServiceImpl implements WxDiscountService {


    @Autowired
    private WxDiscountMapper wxDiscountMapper;

    @Autowired
    private BpItemMapper bpItemMapper;

    /**
     * 小程序背包物品提现记录列表
     *
     * @param page
     * @param cardHolder 收款人姓名
     * @param type       1支付宝 2银行卡
     * @param status     1 申请折现 2 成功 3 失败，-1删除
     * @return
     * @author linqin
     * @date 2019/3/19 10:44
     */
    @Override
    public Response getWxDiscountList(PageQuery page, String cardHolder, Byte type, Byte status) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<WxDiscountVo> wxDiscountVos = wxDiscountMapper.selectAllBySearch(cardHolder,type,status);
        PageInfo pageInfo = new PageInfo<>(wxDiscountVos);
        return ResponseFactory.page(wxDiscountVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 删除小程序背包物品提现记录
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    @Override
    public Response deleteWxDiscount(Integer id) {
        WxDiscount wxDiscount = wxDiscountMapper.selectById(id);
        if (wxDiscount == null){
            return ResponseFactory.err("该记录不存在");
        }
        wxDiscount.setStatus((byte)-1);
        int i = wxDiscountMapper.updateByPrimaryKeySelective(wxDiscount);
        if (i < 1){
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * 小程序折现处理成功
     *
     * @param id 折现记录id
     * @param status 2成功 3 失败
     * @return
     * @author linqin
     * @date 2019/3/19
     */
    @Override
    public Response handleWXDiscount(Integer id, Byte status) {
        WxDiscount wxDiscount = wxDiscountMapper.selectById(id);
        if (wxDiscount == null){
            return ResponseFactory.err("改记录不存在");
        }
        // 提现失败
        if (status == 3){
            //物品返还背包
            BpItem bpItem = bpItemMapper.selectByPrimaryKey(wxDiscount.getBpId());
            if (bpItem == null){
                throw new ServiceException(ErrorCode.ERROR.getCode(),"背包物品不存在");
            }
            bpItem.setQuantity(bpItem.getQuantity()+ 1);
            int i = bpItemMapper.updateByPrimaryKeySelective(bpItem);
            if (i< 1){
                throw new ServiceException(ErrorCode.ERROR.getCode(),"背包物品返回失败");
            }
        }
        //改变小程序折现记录状态
        wxDiscount.setStatus(status);
        int i1 = wxDiscountMapper.updateByPrimaryKeySelective(wxDiscount);
        if (i1 < 1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"改变小程序折现记录状态失败");
        }
        return ResponseFactory.sucMsg("处理成功");
    }


}
