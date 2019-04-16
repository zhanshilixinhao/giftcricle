package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.WalletRecord;

public interface WalletRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WalletRecord record);

    int insertSelective(WalletRecord record);

    WalletRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WalletRecord record);

    int updateByPrimaryKey(WalletRecord record);
}