package com.chouchong.dao.v3;

import com.chouchong.entity.v3.ElCouponUseLog;

import java.util.List;

public interface ElCouponUseLogMapper {
    List<ElCouponUseLog> selectList(ElCouponUseLog log);
}
