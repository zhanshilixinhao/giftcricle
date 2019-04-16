package com.chouchong.service.gift.item.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemCategoryMapper;
import com.chouchong.entity.gift.item.ItemCategory;
import com.chouchong.service.gift.item.ItemCateService;
import com.chouchong.service.gift.item.vo.ItemCategoryVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/6/25
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ItemCateServiceImpl implements ItemCateService{
    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    /**
     * 根据商品分类名称获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getItemCateList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ItemCategory> itemCategoryList = itemCategoryMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(itemCategoryList);
        return ResponseFactory.page(itemCategoryList,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 根据父级id获得商品分类列表
     *
     * @param: [page, name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getItemCateListByPid(PageQuery page, Integer pid) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ItemCategory> itemCategoryList = itemCategoryMapper.selectByPid(pid);
        PageInfo pageInfo = new PageInfo<>(itemCategoryList);
        return ResponseFactory.page(itemCategoryList,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除商品分类
     *
     * @param: [id 分类id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response delItemCate(Integer id) {
        int count = itemCategoryMapper.deleteByItemCateId(id);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 修改商品分类状态
     *
     * @param: [id 分类id, status 分类状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        int count = itemCategoryMapper.updateItemCateStatus(id, status);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response addItemCate(String name) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setUpdated(new Date());
        itemCategory.setStatus((byte)1);
        itemCategory.setSort(0);
        itemCategory.setPid(0);
        itemCategory.setName(name);
        itemCategory.setIcon(null);
        itemCategory.setCreated(new Date());
        int count = itemCategoryMapper.insert(itemCategory);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 根据父级id添加商品分类
     *
     * @param: [name 分类名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response addItemCateByPid(String name, Integer pid) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setUpdated(new Date());
        itemCategory.setStatus((byte)1);
        itemCategory.setSort(0);
        itemCategory.setPid(pid);
        itemCategory.setName(name);
        itemCategory.setIcon(null);
        itemCategory.setCreated(new Date());
        int count = itemCategoryMapper.insert(itemCategory);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改商品分类
     *
     * @param: [id 分类id, name 分类名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response updateItemCate(Integer id, String name, Integer sort) {
        ItemCategory itemCategory = itemCategoryMapper.selectByPrimaryKey(id);
        if (itemCategory == null) {
            return ResponseFactory.err("商品分类不存在!");
        }
        itemCategory.setUpdated(new Date());
        itemCategory.setName(name);
        itemCategory.setSort(sort);
        int count = itemCategoryMapper.updateByPrimaryKey(itemCategory);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 查询全部的商品分类
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getAllItemCate() {
        List<ItemCategoryVo> itemCategoryList = itemCategoryMapper.selectAllItemCate();
        for (ItemCategoryVo itemCategoryVo : itemCategoryList) {
            List<ItemCategory> categories = itemCategoryMapper.selectByPid(itemCategoryVo.getId());
            if (!CollectionUtils.isEmpty(categories)){
                itemCategoryVo.children = categories;
            }
        }
        return ResponseFactory.sucData(itemCategoryList);
    }
}
