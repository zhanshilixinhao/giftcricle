package com.chouchong.service.iwant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.*;
import com.chouchong.dao.home.message.AppMessageMapper;
import com.chouchong.dao.home.message.AppMessageUserMapper;
import com.chouchong.dao.iwant.withdraw.*;
import com.chouchong.entity.home.message.AppMessage;
import com.chouchong.entity.home.message.AppMessageUser;
import com.chouchong.entity.iwant.withdraw.BankDict;
import com.chouchong.entity.iwant.withdraw.UserWithdraw;
import com.chouchong.entity.iwant.withdraw.Wallet;
import com.chouchong.entity.iwant.withdraw.WalletRecord;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.iwant.WithdrawService;
import com.chouchong.service.iwant.vo.UserWithdrawVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author yy
 * @date 2018/7/24
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class WithdrawServiceImpl implements WithdrawService {
    @Autowired
    private BankDictMapper bankDictMapper;

    @Autowired
    private UserBankCardMapper userBankCardMapper;

    @Autowired
    private UserWithdrawMapper userWithdrawMapper;

    @Autowired
    private WalletRecordMapper walletRecordMapper;

    @Autowired
    private WalletMapper walletMapper;

    @Autowired
    private AppMessageMapper appMessageMapper;

    @Autowired
    private AppMessageUserMapper appMessageUserMapper;

    /**
     * 获取用户提现列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response getWithdrawList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("nickname",jsonObject.getString("nickname"));
        map.put("bank",jsonObject.getInteger("bank"));
        map.put("phone",jsonObject.getString("phone"));
        map.put("cardNo",jsonObject.getString("cardNo"));
        map.put("cardHolder",jsonObject.getString("cardHolder"));
        map.put("status",jsonObject.getInteger("status"));
        List<UserWithdrawVo> userWithdrawVos = userWithdrawMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(userWithdrawVos);
        return ResponseFactory.page(userWithdrawVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 获取所有的银行列表
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response getBankList() {
        List<BankDict> banks = bankDictMapper.selectAllBank();
        return ResponseFactory.sucData(banks);
    }

    /**
     * 处理提现状态
     *
     * @param: [userWithdraw]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response handleUserWithdraw(UserWithdraw userWithdraw) {
        UserWithdraw userWithdraw1 = userWithdrawMapper.selectByPrimaryKey(userWithdraw.getId());
        if (userWithdraw1 == null) {
            return ResponseFactory.err("无此提现记录");
        }
        if (userWithdraw.getStatus().intValue() == 4) {
            // 如果提现失败, 则需要返还用户额度
            Wallet wallet = walletMapper.selectByPrimaryKey(userWithdraw1.getUserId());
            if (wallet == null) {
                return ResponseFactory.err("用户钱包不存在");
            }
            // 用户额度 = 用户现有额度 + 用户提现额度
            wallet.setBalance(userWithdraw1.getAmount().add(wallet.getBalance()));
            wallet.setUpdated(new Date());
            int count = walletMapper.updateByPrimaryKey(wallet);
            if (count != 1) {
                return ResponseFactory.err("处理失败");
            }
            WalletRecord walletRecord = new WalletRecord();
            // 退款金额
            walletRecord.setAmount(userWithdraw1.getAmount());
            // 退款用户
            walletRecord.setUserId(userWithdraw1.getUserId());
            // 类型 7-提现失败返还额度
            walletRecord.setType((byte)7);
            walletRecord.setUpdated(new Date());
            // 用户提现记录id
            walletRecord.setTargetId(userWithdraw1.getId());
            walletRecord.setExplain("提现失败返还额度");
            walletRecord.setCreated(new Date());
            // 插入返还额度记录
            walletRecordMapper.insert(walletRecord);
            //添加系统消息
            AppMessage message = new AppMessage();
            message.setTitle("系统通知");
            message.setSummary("您的提现申请失败！");
            message.setContent("您好，你的"+userWithdraw1.getAmount()+"元提现申请失败，提现失败原因："+userWithdraw1.getDescribe());
            message.setTargetId(userWithdraw1.getId());
            message.setTargetType((byte) 23);
            message.setMessageType((byte)2);

            int in = addMessage(message, new ArrayList<Integer>() {
                {
                    add(userWithdraw1.getUserId());
                }
            });
//        int in = appMessageMapper.insert(message);
            if (in<1){
                throw new ServiceException(ErrorCode.ERROR.getCode(),"添加系统消息失败");
            }
        }
        userWithdraw1.setDescribe(userWithdraw.getDescribe());
        userWithdraw1.setStatus(userWithdraw.getStatus());
        userWithdraw1.setUpdated(new Date());
        int count = userWithdrawMapper.updateByPrimaryKey(userWithdraw1);
        if (count == 1) {
            return ResponseFactory.sucMsg("处理成功");
        }
        //添加系统消息
        AppMessage message = new AppMessage();
        message.setTitle("系统通知");
        message.setSummary("您的提现申请已成功！");
        message.setContent("您好，你的"+userWithdraw1.getAmount()+"元提现申请已成功处理，请您查看相关银行卡，确认是否到账！");
        message.setTargetId(userWithdraw1.getId());
        message.setTargetType((byte) 23);
        message.setMessageType((byte)2);

        int in = addMessage(message, new ArrayList<Integer>() {
            {
                add(userWithdraw1.getUserId());
            }
        });
//        int in = appMessageMapper.insert(message);
        if (in<1){
            throw new ServiceException(ErrorCode.ERROR.getCode(),"添加系统消息失败");
        }
        return ResponseFactory.err("处理失败");
    }



    /**
     * 添加一条消息
     *
     * @param message 消息内容
     * @param userIds 用户id集合
     * @return
     * @author yichenshanren
     * @date 2018/7/3
     */
    public int addMessage(AppMessage message, List<Integer> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            throw new ServiceException(ErrorCode.ERROR);
        }
        message.setId(null);
        // 添加消息内容
        int count = appMessageMapper.insert(message);
        if (count < 1) {
            throw new ServiceException(ErrorCode.ERROR);
        }
        // 添加消息关联
        List<AppMessageUser> users = new ArrayList<>();
        for (Integer userId : userIds) {
            users.add(assembleAppMessageUser(userId, message));
        }
        // 批量插入
        count = appMessageUserMapper.insertBatch(users);
        if (count < 1) {
            throw new ServiceException(ErrorCode.ERROR);
        }
        return count;
    }


    /**
     * 组装
     *
     * @param userId  用户id
     * @param message 消息内容
     * @return
     * @author yichenshanren
     * @date 2018/7/3
     */
    private AppMessageUser assembleAppMessageUser(Integer userId, AppMessage message) {
        AppMessageUser user = new AppMessageUser();
        user.setAppMessageId(message.getId());
        user.setIsRead((byte)2);
        user.setUserId(userId);
        return user;
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
        UserWithdraw userWithdraw = userWithdrawMapper.selectByPrimaryKey(id);
        if (userWithdraw == null){
            return ResponseFactory.errMissingParameter();
        }
        if (userWithdraw.getStatus() == 1){
            userWithdraw.setStatus((byte)2);
            userWithdrawMapper.updateByPrimaryKeySelective(userWithdraw);
            return ResponseFactory.sucMsg("已开始处理");
        }
        return ResponseFactory.err("开始处理失败");
    }
}
