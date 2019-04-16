package com.chouchong.service.gift.discount.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.bpItem.BpItemMapper;
import com.chouchong.dao.gift.discount.DiscountingMapper;
import com.chouchong.dao.home.message.AppMessageMapper;
import com.chouchong.dao.iwant.withdraw.WalletMapper;
import com.chouchong.dao.iwant.withdraw.WalletRecordMapper;
import com.chouchong.entity.home.message.AppMessage;
import com.chouchong.entity.iwant.withdraw.Wallet;
import com.chouchong.entity.iwant.withdraw.WalletRecord;
import com.chouchong.entity.webUser.Discounting;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.discount.DiscountService;
import com.chouchong.service.gift.discount.vo.DiscountVo;
import com.chouchong.utils.BigDecimalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author linqin
 * @date 2018/7/24
 */
@Service
@Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountingMapper discountingMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private BpItemMapper bpItemMapper;

    /**
     * 背包物品折现记录列表
     *
     * @param nickname 用户昵称
     * @param title    商品标题
     * @param type     1-物品，2-虚拟物品
     * @param status   折现状态，1-等待折现，2-折现完成，3-折现失败,4-删除记录
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @Override
    public Response discountList(PageQuery pageQuery, String nickname, String title, Byte type, Byte status) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        List<Map> maps = discountingMapper.selectAll(nickname,title,type,status);
        PageInfo pageInfo = new PageInfo<>(maps);
        return ResponseFactory.page(maps, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除折现记录
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @Override
    public Response delDiscount(Integer id) {
        int count = discountingMapper.updateStatusById(id);
        if (count<1){
            return ResponseFactory.err("状态更新失败");
        }
        return ResponseFactory.suc();
    }


    /**
     * 确认折现
     *
     * @param id 折现记录Id
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @Override
    public Response confirmDiscount(Integer id) {
        //查询订单
        DiscountVo discounting = discountingMapper.selectById(id);
        if (discounting == null){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"折现记录不存在或已确认折现");
        }
        BigDecimal price = discounting.getDiscountPrice();
        //确认折现后把金额添加到余额，添加收益记录，添加系统消息,更新状态
        //把折后金额添加到余额里
        Wallet wallet = walletMapper.selectByPrimaryKey(discounting.getUserId());
        if (wallet == null){
            wallet = new Wallet();
           wallet.setUserId(discounting.getUserId());
           wallet.setBalance(price);
           wallet.setTotalAmount(price);
           wallet.setConsumeAmount(new BigDecimal(0));
            int insert = walletMapper.insert(wallet);
            if (insert<0){
                throw new ServiceException(ErrorCode.ERROR.getCode(),"余额添加失败");
            }
        }
        wallet.setBalance(BigDecimalUtil.add(price.doubleValue(),wallet.getBalance().doubleValue()));
        wallet.setTotalAmount(BigDecimalUtil.add(price.doubleValue(),wallet.getTotalAmount().doubleValue()));
        int update = walletMapper.updateByPrimaryKeySelective(wallet);
        if (update<0){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"余额添加失败!");
        }
        //添加系统消息
        AppMessage message = new AppMessage();
        message.setTitle("系统通知");
        message.setSummary("您的折现已成功到达账户，请前往余额查看");
        message.setContent("您好，你的"+discounting.getTitle()+"物品折现金额"+discounting.getDiscountPrice()+"元，原价格"
                +discounting.getItemPrice()+"元，您的折现已成功到达账户，请前往余额查看");
        message.setTargetId(discounting.getBpId().intValue());
        message.setTargetType((byte) 21);
        message.setMessageType((byte)2);
        int insert = appMessageMapper.insert(message);
        if (insert<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"添加系统消息失败");
        }
        //添加收益记录
        WalletRecord record = new WalletRecord();
        record.setUserId(discounting.getUserId());
        Byte type = discounting.getType();
        if (type == 1){
            record.setExplain("商品折现");
            record.setType((byte)2);
        }else {
            record.setExplain("虚拟商品折现");
            record.setType((byte)3);
        }
        record.setAmount(discounting.getDiscountPrice());
        record.setTargetId(discounting.getBpId().intValue());
        int insert1 = walletRecordMapper.insert(record);
        if (insert1<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"添加系统消息失败");
        }
        //更新折现记录
        Discounting discount = new Discounting();
        discount.setId(discounting.getId());
        discount.setStatus((byte)2);
        discount.setExplain("折现成功");
        int update1 = discountingMapper.updateByPrimaryKeySelective(discount);
        if (update1<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"更新折现记录失败");
        }
        //减少背包物品
        int i = bpItemMapper.updateQuantity(discounting.getBpId());
        if (i<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"更新背包物品数量失败");
        }
        return ResponseFactory.suc();
    }


    /**
     * 拒绝折现
     *
     * @param id 折现记录Id
     * @param explain 折现说明
     * @return
     * @author linqin
     * @date 2018/7/24
     */
    @Override
    public Response refuseDiscount(Integer id, String explain) {
        Discounting discounting = discountingMapper.selectByPrimaryKey(id);
        if (discounting == null){
            return ResponseFactory.err("折现记录不存在或已确认折现");
        }
        //更新折现记录
        discounting.setExplain(explain);
        discounting.setStatus((byte)3);
        int update = discountingMapper.updateByPrimaryKeySelective(discounting);
        if (update<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"更新折现记录失败");
        }
        return ResponseFactory.suc();
    }
}
