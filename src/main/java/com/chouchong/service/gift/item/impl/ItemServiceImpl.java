package com.chouchong.service.gift.item.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemMapper;
import com.chouchong.entity.gift.item.Item;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.item.ItemService;
import com.chouchong.service.gift.item.vo.ItemVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/6/26
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 根据查询条件获得商品列表
     *
     * @param: [page, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getItemList(PageQuery page, String search) {
       WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("title",jsonObject.getString("title"));
        map.put("category",jsonObject.getInteger("category"));
        map.put("status",jsonObject.getInteger("status"));
        map.put("brand",jsonObject.getInteger("brand"));
        map.put("hot",jsonObject.getInteger("hot"));
        map.put("choiceness",jsonObject.getInteger("choiceness"));
        map.put("price",jsonObject.getInteger("price"));
        map.put("sales",jsonObject.getInteger("sales"));
        map.put("date",jsonObject.getInteger("date"));
        if (webUserInfo.getRoleId() == 3) {
            map.put("adminId", webUserInfo.getSysAdmin().getId());
        }
        List<ItemVo> items = itemMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(items);
        return ResponseFactory.page(items,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 修改商品启用禁用状态
     *
     * @param: [id 商品id, status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return ResponseFactory.err("无此商品");
        }
        item.setStatus(status.byteValue());
        item.setUpdated(new Date());
        int count = itemMapper.updateByPrimaryKey(item);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 修改商品热门状态
     *
     * @param: [id 商品id, status 热门状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response changeHot(Integer id, Integer status) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return ResponseFactory.err("无此商品");
        }
        item.setHot(status.byteValue());
        item.setUpdated(new Date());
        int count = itemMapper.updateByPrimaryKey(item);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 修改商品精选状态
     *
     * @param: [id 商品id, status 热门状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response changeChoiceness(Integer id, Integer status) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return ResponseFactory.err("无此商品");
        }
        item.setChoiceness(status.byteValue());
        item.setUpdated(new Date());
        int count = itemMapper.updateByPrimaryKey(item);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 添加商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response addItem(ItemVo itemVo) {
        Item item = initItem(itemVo);
        int count = itemMapper.insert(item);
        if (count == 1) {
            count = itemMapper.insertItemDetail(item.getId(), itemVo.getDetail());
            if (count == 1) {
                return ResponseFactory.suc();
            }
            throw new ServiceException(ErrorCode.ERROR.getCode(),"添加失败" );
        }
        return ResponseFactory.err("添加失败!");
    }



    /**
     * 初始化添加商品
     *
     * @param: [itemVo]
     * @return: com.chouchong.entity.gift.item.Item
     * @author: yy
     * @Date: 2018/6/27
     */
    private Item initItem(ItemVo itemVo) {
       WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Item item = new Item();
        item.setTitle(itemVo.getTitle());
        item.setReMaxAge(itemVo.getReMaxAge());
        item.setReGender(itemVo.getReGender());
        item.setReAgeMin(itemVo.getReAgeMin());
        item.setPrice(itemVo.getPrice());
        item.setPictures(itemVo.getPictures());
        item.setDescription(itemVo.getDescription());
        item.setCreated(new Date());
        item.setCover(itemVo.getCover());
        item.setCategoryId(itemVo.getCategoryId());
        item.setWxCover(itemVo.getWxCover());
        item.setBrandId(itemVo.getBrandId());
        item.setStatus((byte)2);
        item.setHot((byte)0);
        item.setIsAudit((byte)1);
        item.setChoiceness((byte)0);
        item.setSales(0);
        item.setUpdated(new Date());
        item.setStock(0);
        item.setAdminId(webUserInfo.getSysAdmin().getId());
        return item;
    }


    /**
     * 获得商品详情
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getItemDetail(Integer id) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item != null) {
            ItemVo itemVo = initItemVo(item);
            String detail = itemMapper.selectDetailByItemId(id);
            Integer pid = itemMapper.selectPidByItemId(id);
            itemVo.setDetail(detail);
            itemVo.setPid(pid);
            return ResponseFactory.sucData(itemVo);
        }
        return ResponseFactory.err("无此商品");
    }

    /**
     * 修改商品
     *
     * @param: [itemVo 商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response updateItem(ItemVo itemVo) {
        Item item = itemMapper.selectByPrimaryKey(itemVo.getId());
        if (item != null) {
            item.setUpdated(new Date());
            item.setTitle(itemVo.getTitle());
            item.setReMaxAge(itemVo.getReMaxAge());
            item.setReGender(itemVo.getReGender());
            item.setReAgeMin(itemVo.getReAgeMin());
            item.setPrice(itemVo.getPrice());
            item.setPictures(itemVo.getPictures());
            item.setDescription(itemVo.getDescription());
            item.setCover(itemVo.getCover());
            item.setCategoryId(itemVo.getCategoryId());
            item.setBrandId(itemVo.getBrandId());
            item.setWxCover(itemVo.getWxCover());
            int count = itemMapper.updateByPrimaryKey(item);
            if (count != 1) {
                throw new ServiceException(ErrorCode.ERROR.getCode(),"修改失败" );
            }
            String detail = itemMapper.selectDetailByItemId(itemVo.getId());
            if (detail == null) {
                itemMapper.insertItemDetail(itemVo.getId(), itemVo.getDetail());
                return ResponseFactory.suc();
            } else {
                itemMapper.updateItemDetail(itemVo.getId(), itemVo.getDetail());
                return ResponseFactory.suc();
            }
        }
        return ResponseFactory.err("修改失败!");
    }

    /**
     * 删除商品
     *
     * @param: [id 商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response delItem(Integer id) {
        int count = itemMapper.deleteByItemId(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }



    /**
     * 初始化修改商品
     *
     * @param: [itemVo]
     * @return: com.chouchong.service.gift.item.vo.ItemVo
     * @author: yy
     * @Date: 2018/6/27
     */
    private ItemVo initItemVo(Item itemVo) {
        ItemVo item = new ItemVo();
        item.setTitle(itemVo.getTitle());
        item.setReMaxAge(itemVo.getReMaxAge());
        item.setReGender(itemVo.getReGender());
        item.setReAgeMin(itemVo.getReAgeMin());
        item.setPrice(itemVo.getPrice());
        item.setPictures(itemVo.getPictures());
        item.setDescription(itemVo.getDescription());
        item.setCover(itemVo.getCover());
        item.setCategoryId(itemVo.getCategoryId());
        item.setBrandId(itemVo.getBrandId());
        item.setWxCover(itemVo.getWxCover());
        item.setId(itemVo.getId());
        return item;
    }



    /**
     * 设置商品排序值
     * @param id 商品id
     * @param sort 排序值
     * @return
     */
    @Override
    public Response setSort(Integer id, Integer sort) {
        Item item = itemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return ResponseFactory.err("无此商品");
        }
        item.setSort(sort);
        int count = itemMapper.updateByPrimaryKeySelective(item);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("设置失败");
    }



}
