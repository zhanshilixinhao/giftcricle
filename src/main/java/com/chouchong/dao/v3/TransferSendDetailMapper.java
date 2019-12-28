package com.chouchong.dao.v3;

import com.chouchong.entity.v3.TransferSendDetail;

public interface TransferSendDetailMapper {
    int insert(TransferSendDetail record);

    int insertSelective(TransferSendDetail record);
}