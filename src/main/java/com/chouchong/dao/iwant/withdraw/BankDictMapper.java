package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.BankDict;

import java.util.List;

public interface BankDictMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BankDict record);

    int insertSelective(BankDict record);

    BankDict selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BankDict record);

    int updateByPrimaryKey(BankDict record);

    /**
     * 获得所有的银行
     *
     * @param: []
     * @return: java.util.List<com.chouchong.entity.iwant.withdraw.BankDict>
     * @author: yy
     * @Date: 2018/7/24
     */
    List<BankDict> selectAllBank();
}