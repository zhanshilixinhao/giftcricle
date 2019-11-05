package com.chouchong.service.v3.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.entity.v3.Store;
import com.chouchong.service.v3.StoreService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author linqin
 * @date 2019/11/5
 */
@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private StoreMapper storeMapper;

    /**
     * 获取门店列表
     *
     * @param name    名称
     * @param address 地址
     * @param page
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @Override
    public Response getStoreList(String name, String address, PageQuery page) {
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        Integer merchantId = null;
        if (webUserInfo.getRoleId() == 2) {
            merchantId = 0;
        }else if (webUserInfo.getRoleId() == 3 || webUserInfo.getRoleId() == 4){
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<Store> stores = storeMapper.selectBySearch(adminId,merchantId,name,address);
        PageInfo pageInfo = new PageInfo<>(stores);
        return ResponseFactory.page(stores,pageInfo.getTotal(),pageInfo.getPages(),
                pageInfo.getPageNum(),pageInfo.getPageSize());
    }

}
