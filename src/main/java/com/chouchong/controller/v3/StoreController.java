package com.chouchong.controller.v3;

import com.alibaba.druid.sql.visitor.functions.If;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
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
     * @param name     名称
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
    @PostMapping("add")
    public Response addStore(Store store) {
        if (StringUtils.isAnyBlank(store.getName(), store.getAddress(),
                store.getPhone(), store.getLinkman(), store.getArea())) {
            return ResponseFactory.errMissingParameter();
        }
        return storeService.addStore(store);
    }

    /**
     * 修改门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @PostMapping("update")
    public Response updateStore(Store store) {
        if (StringUtils.isAnyBlank(store.getName(), store.getAddress(),
                store.getPhone(), store.getLinkman(), store.getArea())) {
            return ResponseFactory.errMissingParameter();
        }
        if (store.getId() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return storeService.updateStore(store);
    }

    /**
     * 门店详情
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @PostMapping("detail")
    public Response detailStore(Integer storeId) {
        if (storeId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return storeService.detailStore(storeId);
    }

    /**
     * 删除门店
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @PostMapping("delete")
    public Response deleteStore(Integer storeId) {
        if (storeId == null) {
            return ResponseFactory.errMissingParameter();
        }
        return storeService.deleteStore(storeId);
    }

    /**
     * 获取行政区列表
     *
     * @return
     * @author yichenshanren
     * @date 2018/6/6
     */
    @PostMapping("district")
    public Response getDistrictList() {
        return storeService.getDistrictList();
    }

    /**
     * 门店绑定后台用户
     * @param storeId 门店id
     * @param username 后台用户名
     * @return
     */
    @PostMapping("bind")
    public Response bindStore(Integer storeId,String username){
        if (StringUtils.isBlank(username) || storeId == null){
            return ResponseFactory.errMissingParameter();
        }
        return storeService.bindStore(storeId,username);
    }

}
