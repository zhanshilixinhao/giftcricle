package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.ElCouponUseLog;

public interface ElCouponUseLogService {
    Response getList(ElCouponUseLog log, PageQuery page);

}
