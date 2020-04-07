package com.chouchong.service.v3;


import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.ElCouponUseLogMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.v3.ElCouponUseLog;
import com.chouchong.service.v3.vo.ForUserVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class ElCouponUseLogServiceImpl implements ElCouponUseLogService {

    @Autowired
    private ElCouponUseLogMapper elCouponUseLogMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private StoreMapper storeMapper;

    @Override
    public Response getList(ElCouponUseLog log, PageQuery page) {
        // 角色
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = null;
        // 商家
        if (webUserInfo.getRoleId() == 3) {
            Integer cAdminId = webUserInfo.getSysAdmin().getId();
            List<Integer> list = sysAdminMapper.selectIdByCreatedId(cAdminId);
            if (list.size() == 0) {
                return ResponseFactory.suc();
            }
            PageHelper.startPage(page.getPageNum(), page.getPageSize());
            List<ElCouponUseLog> li = elCouponUseLogMapper.selectList1(log,list);
            PageInfo pageInfo = new PageInfo<>(li);
            return ResponseFactory.page(li, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        } else if (webUserInfo.getRoleId() == 5) {
            adminId = webUserInfo.getSysAdmin().getId();
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ElCouponUseLog> li = elCouponUseLogMapper.selectList(log,adminId);
        PageInfo pageInfo = new PageInfo<>(li);
        return ResponseFactory.page(li, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }
}
