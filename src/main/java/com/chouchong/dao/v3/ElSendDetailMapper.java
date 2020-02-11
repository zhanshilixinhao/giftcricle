package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElSendDetail;

public interface ElSendDetailMapper {
    int insert(ElSendDetail record);

    int insertSelective(ElSendDetail record);
}