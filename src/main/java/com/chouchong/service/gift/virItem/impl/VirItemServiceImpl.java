package com.chouchong.service.gift.virItem.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.virItem.VirtualItemMapper;
import com.chouchong.entity.gift.virItem.VirtualItem;
import com.chouchong.service.gift.virItem.VirItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/2
 **/
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class VirItemServiceImpl implements VirItemService {
    @Autowired
    private VirtualItemMapper virtualItemMapper;

    /**
     * 获得虚拟商品列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response getVirItemList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("name",jsonObject.getString("name"));
        map.put("category",jsonObject.getInteger("category"));
        map.put("status",jsonObject.getInteger("status"));
        map.put("brand",jsonObject.getInteger("brand"));
        map.put("price",jsonObject.getInteger("price"));
        map.put("sales",jsonObject.getInteger("sales"));
        map.put("date",jsonObject.getInteger("date"));
        List<VirtualItem> virtualItems = virtualItemMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(virtualItems);
        return ResponseFactory.page(virtualItems, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 修改虚拟商品状态
     *
     * @param: [id 虚拟商品id, status 虚拟商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        VirtualItem virtualItem = virtualItemMapper.selectByPrimaryKey(id);
        if (virtualItem == null) {
            return ResponseFactory.err("无此虚拟商品");
        }
        virtualItem.setUpdated(new Date());
        virtualItem.setStatus(status.byteValue());
        int count = virtualItemMapper.updateByPrimaryKey(virtualItem);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }


    /**
     * 删除虚拟商品
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response delVirItemList(Integer id) {
        int count = virtualItemMapper.deleteByVirItemId(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 添加虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response addVirItem(VirtualItem virtualItem) {
        virtualItem.setStatus((byte)2);
        virtualItem.setUpdated(new Date());
        virtualItem.setCreated(new Date());
        virtualItem.setSales(0);
        int count = virtualItemMapper.insert(virtualItem);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 获得虚拟商品详情
     *
     * @param: [id 虚拟商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response getVirItemDetail(Integer id) {
        VirtualItem virtualItem = virtualItemMapper.selectByPrimaryKey(id);
        if (virtualItem == null) {
            return ResponseFactory.err("无此虚拟商品");
        }
        return ResponseFactory.sucData(virtualItem);
    }

    /**
     * 修改虚拟商品
     *
     * @param: [virtualItem 虚拟商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response updateVirItem(VirtualItem virtualItem) {
        VirtualItem virtualItem1 = virtualItemMapper.selectByPrimaryKey(virtualItem.getId());
        if (virtualItem1 == null) {
            return ResponseFactory.err("无此商品");
        }
        virtualItem1.setPrice(virtualItem.getPrice());
        virtualItem1.setName(virtualItem.getName());
        virtualItem1.setDescription(virtualItem.getDescription());
        virtualItem1.setCover(virtualItem.getCover());
        virtualItem1.setCateId(virtualItem.getCateId());
        virtualItem1.setBrandId(virtualItem.getBrandId());
        virtualItem1.setSort(virtualItem.getSort());
        virtualItem1.setUpdated(new Date());
        int count = virtualItemMapper.updateByPrimaryKey(virtualItem1);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }
}
