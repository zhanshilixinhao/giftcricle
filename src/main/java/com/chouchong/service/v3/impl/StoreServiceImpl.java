package com.chouchong.service.v3.impl;

import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.merchant.MerchantMapper;
import com.chouchong.dao.iwant.withdraw.DistrictMapper;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.webUser.SysAdminMapper;
import com.chouchong.entity.iwant.merchant.Merchant;
import com.chouchong.entity.v3.Store;
import com.chouchong.entity.webUser.SysAdmin;
import com.chouchong.redis.MRedisTemplate;
import com.chouchong.service.v3.StoreService;
import com.chouchong.service.v3.vo.DistrictVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private MerchantMapper merchantMapper;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private SysAdminMapper sysAdminMapper;

    @Autowired
    private MRedisTemplate mRedisTemplate;

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
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        Integer merchantId = null;
        if (webUserInfo.getRoleId() == 2) {
            merchantId = 0;
        } else if (webUserInfo.getRoleId() == 3 ) {
            Merchant merchant = merchantMapper.selectByAdminId(webUserInfo.getSysAdmin().getId());
            if (merchant != null){
                merchantId = merchant.getId();
            }
        }
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Store> stores = storeMapper.selectBySearch(adminId, merchantId, name, address);
        PageInfo pageInfo = new PageInfo<>(stores);
        return ResponseFactory.page(stores, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 添加门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @Override
    public Response addStore(Store store) {
        // 查询商家id和adminId
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Integer adminId = webUserInfo.getSysAdmin().getId();
        Merchant merchant = merchantMapper.selectByAdminId(adminId);
        Store store1 = new Store();
        store1.setName(store.getName());
        if (merchant != null) {
            store1.setMerchantId(merchant.getId());
        }
        store1.setAddress(store.getAddress());
        store1.setPhone(store.getPhone());
        store1.setArea(store.getArea());
        store1.setLinkman(store.getLinkman());
        int insert = storeMapper.insert(store1);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改门店
     *
     * @param store
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @Override
    public Response updateStore(Store store) {
        Store store1 = storeMapper.selectByPrimaryKey(store.getId());
        if (store1 == null){
            return ResponseFactory.err("改门店不存在");
        }
        store1.setName(store.getName());
        store1.setAddress(store.getAddress());
        store1.setPhone(store.getPhone());
        store1.setArea(store.getArea());
        store1.setLinkman(store.getLinkman());
        int i = storeMapper.updateByPrimaryKeySelective(store1);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除门店
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @Override
    public Response deleteStore(Integer storeId) {
        int i = storeMapper.deleteByPrimaryKey(storeId);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     *获取行政区列表
     * @return
     * @author yichenshanren
     * @date 2018/6/6
     */
    @Override
    public Response getDistrictList() {
        List<DistrictVo> list = mRedisTemplate.get(
                "gc-di-li",
                300, TimeUnit.DAYS,
                new TypeReference<List<DistrictVo>>(){},
                this::getAllDistrict);
        return ResponseFactory.sucData(list);
    }

    private List<DistrictVo> getAllDistrict() {
        List<DistrictVo> province = districtMapper.selectSimple("province", 0);
        for (DistrictVo districtVo : province) {
            List<DistrictVo> list = districtMapper.selectSimple("city", districtVo.id);
            districtVo.name = districtVo.name.replaceAll("省", "");
            districtVo.children = list;
            for (DistrictVo vo : list) {
                vo.children = districtMapper.selectSimple("district", vo.id);
            }
        }
        return province;
    }

    /**
     * 门店详情
     *
     * @param storeId 门店id
     * @return
     * @author linqin
     * @date 2019/11/5
     */
    @Override
    public Response detailStore(Integer storeId) {
        Store store = storeMapper.selectByPrimaryKey(storeId);
        return ResponseFactory.sucData(store);
    }

    /**
     * 门店绑定后台用户
     * @param storeId 门店id
     * @param username 后台用户名
     * @return
     */
    @Override
    public Response bindStore(Integer storeId, String username) {
        Store store = storeMapper.selectByPrimaryKey(storeId);
        if (store == null) {
            return ResponseFactory.err("该门店不存在");
        }
        // 为商家绑定后台用户
        if (store.getAdminId() != null) {
            return ResponseFactory.err("该门店已绑定后台用户");
        }
        SysAdmin sysAdmin = sysAdminMapper.selectByUserName(username);
        if (sysAdmin == null) {
            return ResponseFactory.err("用户名不存在");
        }
        store.setAdminId(sysAdmin.getId());
        int i = storeMapper.updateByPrimaryKeySelective(store);
        if (i < 1) {
            return ResponseFactory.err("操作失败!");
        }
        // 改变商家的后台用户为启用
        sysAdmin.setActive((byte) 1);
        sysAdminMapper.updateByPrimaryKeySelective(sysAdmin);
        return ResponseFactory.sucMsg("操作成功");
    }

}
