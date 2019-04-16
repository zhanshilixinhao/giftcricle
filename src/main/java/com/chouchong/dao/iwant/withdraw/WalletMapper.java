package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
}