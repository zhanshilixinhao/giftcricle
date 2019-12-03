package com.chouchong.dao.v3;


import com.chouchong.entity.v3.CardRebate;

public interface CardRebateMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CardRebate record);

    int insertSelective(CardRebate record);

    CardRebate selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CardRebate record);

    int updateByPrimaryKey(CardRebate record);
}
