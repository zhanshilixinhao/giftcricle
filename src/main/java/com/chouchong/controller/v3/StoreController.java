package com.chouchong.controller.v3;

import com.alibaba.druid.sql.visitor.functions.If;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.v3.Store;
import com.chouchong.service.v3.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linqin
 * @date 2019/11/5
 */
@RequestMapping("manage/v3/store")
@RestController
public class StoreController {

    @Autowired
    private StoreService storeService;

    /**
     * 获取门店列表
     *
     * @param name    名称
     * @param location 地址
     * @param page
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @PostMapping("list")
    public Response getStoreList(String name, String location, PageQuery page) {
        return storeService.getStoreList(name, location, page);
    }

    /**
     * 添加门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    public Response addStore(Store store) {
        if (StringUtils.isAnyBlank(store.getName(),store.getAddress(),
                store.getPhone(),store.getLinkman(),store.getArea())){

        }
    }
}
