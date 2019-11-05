package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;

/**
 * @author linqin
 * @date 2019/11/5
 */

public interface StoreService {

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
    Response getStoreList(String name, String address, PageQuery page);
}
