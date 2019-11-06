package com.chouchong.service.v3;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.Store;

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

    /**
     * 添加门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    Response addStore(Store store);


    /**
     * 修改门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    Response updateStore(Store store);

    /**
     * 删除门店
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    Response deleteStore(Integer storeId);

    /**
     *获取行政区列表
     * @return
     * @author yichenshanren
     * @date 2018/6/6
     */
    Response getDistrictList();

    /**
     * 门店详情
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    Response detailStore(Integer storeId);
}
