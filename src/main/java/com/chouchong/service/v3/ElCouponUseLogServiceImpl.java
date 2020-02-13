package com.chouchong.service.v3;


import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.ElCouponUseLogMapper;
import com.chouchong.entity.v3.ElCouponUseLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElCouponUseLogServiceImpl implements ElCouponUseLogService {

    @Autowired
    private ElCouponUseLogMapper elCouponUseLogMapper;

    @Override
    public Response getList(ElCouponUseLog log, PageQuery page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ElCouponUseLog> li = elCouponUseLogMapper.selectList(log);
        PageInfo pageInfo = new PageInfo<>(li);
        return ResponseFactory.page(li, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
