package com.chouchong.service.gift.virItem.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.virItem.VirtualItemCateMapper;
import com.chouchong.dao.gift.virItem.VirtualItemMapper;
import com.chouchong.entity.gift.virItem.VirtualItemCate;
import com.chouchong.service.gift.virItem.VirItemCateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/2
 **/
@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class VirItemCateServiceImpl implements VirItemCateService {
    @Autowired
    private VirtualItemCateMapper virtualItemCateMapper;

    /**
     * 获得虚拟商品分类列表
     *
     * @param: [page 分页信息, name 虚拟商品分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response getVirItemCateList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<VirtualItemCate> virtualItemCates = virtualItemCateMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(virtualItemCates);
        return ResponseFactory.page(virtualItemCates,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除虚拟商品分类
     *
     * @param: [id 虚拟商品分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response delVirItemCate(Integer id) {
        int count = virtualItemCateMapper.deleteByItemCateId(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 修改虚拟商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        int count = virtualItemCateMapper.updateItemCateStatus(id, status);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置失败");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 添加虚拟商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response addVirItemCate(String name) {
        VirtualItemCate virtualItemCate = new VirtualItemCate();
        virtualItemCate.setUpdated(new Date());
        virtualItemCate.setStatus((byte)1);
        virtualItemCate.setSort(null);
        virtualItemCate.setName(name);
        virtualItemCate.setCreated(new Date());
        int count = virtualItemCateMapper.insert(virtualItemCate);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改虚拟商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response updateVirItemCate(Integer id, String name, Integer sort) {
        VirtualItemCate itemCategory = virtualItemCateMapper.selectByPrimaryKey(id);
        if (itemCategory == null) {
            return ResponseFactory.err("虚拟商品分类不存在!");
        }
        itemCategory.setUpdated(new Date());
        itemCategory.setName(name);
        itemCategory.setSort(sort);
        int count = virtualItemCateMapper.updateByPrimaryKey(itemCategory);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 查询全部的虚拟商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getAllVirItemCate() {
        List<VirtualItemCate> virtualItemCates = virtualItemCateMapper.selectAllItemCate();
        return ResponseFactory.sucData(virtualItemCates);
    }
}
