package com.chouchong.service.gift.coupon.impl;

import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.Constants;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.dao.webUser.SysAdminWalletMapper;
import com.chouchong.dao.webUser.SysAdminWalletRecordMapper;
import com.chouchong.dao.webUser.SysAdminWithdrawMapper;
import com.chouchong.entity.iwant.withdraw.UserWithdraw;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.entity.webUser.SysAdminWallet;
import com.chouchong.entity.webUser.SysAdminWalletRecord;
import com.chouchong.entity.webUser.SysAdminWithdraw;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.gift.coupon.SysWalletService;
import com.chouchong.service.gift.coupon.vo.SysWalletVo;
import com.chouchong.service.gift.coupon.vo.SysWithdrawVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.chouchong.utils.BigDecimalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/4/8 21:10
 */
@Service
public class SysWalletServiceImpl implements SysWalletService {

    @Autowired
    private MRedisTemplate mRedisTemplate;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private SysAdminWithdrawMapper sysAdminWithdrawMapper;

    @Autowired
    private SysAdminWalletMapper sysAdminWalletMapper;

    @Autowired
    private SysAdminWalletRecordMapper sysAdminWalletRecordMapper;

    /**
     * 后台用户钱包详情
     *
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @Override
    public Response getAdminWalletDetail(String token) {
        //根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        // 查询商户钱包详情
        SysWalletVo vo = sysAdminMapper.selectDetailByAdminId(sysAdmin.getId());
        return ResponseFactory.sucData(vo);
    }


    /**
     * 后台商户提现记录
     *
     * @param token
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @Override
    public Response sysWithdrawRecord(PageQuery page, String token) {
        //分页
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        //根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        //查询商户提现记录
        List<SysAdminWithdraw> lists = sysAdminWithdrawMapper.selectByAdminId(sysAdmin.getId());
        PageInfo pageInfo = new PageInfo<>(lists);
        return ResponseFactory.page(lists, pageInfo.getTotal(), pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());

    }


    /**
     * 商户添加提现
     *
     * @param token
     * @param withdraw
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @Override
    public Response addWithRecord(String token, SysAdminWithdraw withdraw) {
        //根据token取出用户信息
        WebUserInfo webUserInfo = mRedisTemplate.get(token, new TypeReference<WebUserInfo>() {
        });
        if (webUserInfo == null) {
            return ResponseFactory.errNeedLogin(Constants.USER_TOKEN_ERROR);
        }
        SysAdmin sysAdmin = webUserInfo.getSysAdmin();
        SysAdminWallet wallet = sysAdminWalletMapper.selectByAdminId(sysAdmin.getId());
        if (wallet == null ||wallet.getTotalAmount().compareTo(withdraw.getAmount()) < 0) {
            return ResponseFactory.err("账户余额小余提现金额，无法提现");
        }
        // 添加一条提现信息
        SysAdminWithdraw add = new SysAdminWithdraw();
        add.setAdminId(sysAdmin.getId());
        add.setAmount(withdraw.getAmount());
        add.setBankName(withdraw.getBankName());
        add.setDepositBank(withdraw.getDepositBank());
        add.setCardHolder(withdraw.getCardHolder());
        add.setCardNo(withdraw.getCardNo());
        add.setStatus((byte) 1);
        int insert = sysAdminWithdrawMapper.insert(add);
        if (insert < 1) {
            return ResponseFactory.err("提现申请失败");
        }
        // 扣减钱包鱼儿.跟新鱼儿
        wallet.setTotalAmount(BigDecimalUtil.sub(wallet.getTotalAmount().doubleValue(), withdraw.getAmount().doubleValue()));
        int i = sysAdminWalletMapper.updateByPrimaryKeySelective(wallet);
        if (i < 1) {
            return ResponseFactory.err("账户余额扣减失败");
        }
        //添加钱包记录
        SysAdminWalletRecord record = new SysAdminWalletRecord();
        record.setAdminId(sysAdmin.getId());
        record.setExplain("商家提现");
        record.setAmount(withdraw.getAmount());
        record.setTargetId(add.getId().longValue());
        record.setType((byte)2);
        int insert1 = sysAdminWalletRecordMapper.insert(record);
        if (insert1 < 1) {
            return ResponseFactory.err("添加钱包记录失败");
        }
        return ResponseFactory.sucMsg("提现申请成功");
    }


    /**********************************************************商家提现处理***************************************************************/


    /**
     * 商家提现列表
     *
     * @param page
     * @param username
     * @param status
     * @return
     * @author linqin
     * @date 2019/4/8
     */
    @Override
    public Response getMerchantsWithdrawList(PageQuery page, String username, Byte status) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<SysWithdrawVo> withdrawVos = sysAdminWithdrawMapper.selectBySearch(username, status);
        PageInfo pageInfo = new PageInfo<>(withdrawVos);
        return ResponseFactory.page(withdrawVos, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 处理商家提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response handleUserWithdraw(SysAdminWithdraw withdraw) {
        SysAdminWithdraw adminWithdraw = sysAdminWithdrawMapper.selectById(withdraw.getId());
        if (adminWithdraw == null) {
            return ResponseFactory.err("无此提现记录");
        }
        if (withdraw.getStatus().intValue() == 4) {
            //如果提现失败则返回钱包
            SysAdminWallet wallet = sysAdminWalletMapper.selectByAdminId(adminWithdraw.getAdminId());
            if (wallet == null) {
                return ResponseFactory.err("用户钱包不存在");
            }
            wallet.setTotalAmount(adminWithdraw.getAmount().add(wallet.getTotalAmount()));
            int count = sysAdminWalletMapper.updateByPrimaryKeySelective(wallet);
            if (count != 1) {
                return ResponseFactory.err("处理失败");
            }
            // 添加钱包使用记录
            //添加钱包记录
            SysAdminWalletRecord record = new SysAdminWalletRecord();
            record.setAdminId(adminWithdraw.getAdminId());
            record.setExplain("商家提现失败返回");
            record.setAmount(adminWithdraw.getAmount());
            record.setTargetId(adminWithdraw.getId().longValue());
            record.setType((byte)3);
            int insert1 = sysAdminWalletRecordMapper.insert(record);
            if (insert1 < 1) {
                return ResponseFactory.err("添加钱包记录失败");
            }
        }
        adminWithdraw.setDescribe(withdraw.getDescribe());
        adminWithdraw.setStatus(withdraw.getStatus());
        int i = sysAdminWithdrawMapper.updateByPrimaryKeySelective(adminWithdraw);
        if (i < 1) {
            return ResponseFactory.err("处理失败");
        }
        return ResponseFactory.sucMsg("处理成功");
    }


    /**
     * 处理提现状态(处理中)
     *
     * @param: [id 提现记录id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response handleBeginWithdraw(Integer id) {
        SysAdminWithdraw adminWithdraw = sysAdminWithdrawMapper.selectById(id);
        if (adminWithdraw == null) {
            return ResponseFactory.err("无此提现记录");
        }
        if (adminWithdraw.getStatus() == 1){
            adminWithdraw.setStatus((byte)2);
            sysAdminWithdrawMapper.updateByPrimaryKeySelective(adminWithdraw);
            return ResponseFactory.sucMsg("已开始处理");
        }
        return ResponseFactory.err("开始处理失败");
    }
}
