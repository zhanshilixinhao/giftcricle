package com.chouchong.dao.home.memo;

import com.chouchong.entity.home.memo.MemoAffair;

public interface MemoAffairMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MemoAffair record);

    int insertSelective(MemoAffair record);

    MemoAffair selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MemoAffair record);

    int updateByPrimaryKey(MemoAffair record);

}
