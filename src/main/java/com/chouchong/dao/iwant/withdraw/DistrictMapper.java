package com.chouchong.dao.iwant.withdraw;

import com.chouchong.entity.iwant.withdraw.District;

public interface DistrictMapper {
    int insert(District record);

    int insertSelective(District record);
}