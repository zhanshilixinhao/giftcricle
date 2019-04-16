package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.UserBankCard;

public interface UserBankCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBankCard record);

    int insertSelective(UserBankCard record);

    UserBankCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBankCard record);

    int updateByPrimaryKey(UserBankCard record);
}