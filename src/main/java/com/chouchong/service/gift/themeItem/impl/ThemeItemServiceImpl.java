package com.chouchong.service.gift.themeItem.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.themeItem.ThemeItemMapper;
import com.chouchong.entity.gift.themeItem.Theme;
import com.chouchong.entity.gift.themeItem.ThemeItem;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.themeItem.ThemeItemService;
import com.chouchong.service.gift.themeItem.vo.ThemeItemVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author yy
 * @date 2018/7/3
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ThemeItemServiceImpl implements ThemeItemService{
    @Autowired
    private ThemeItemMapper themeItemMapper;

    /**
     * 获得主题商品列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response getThemeItemList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("name",jsonObject.getString("name"));
        map.put("theme",jsonObject.getInteger("theme"));
        map.put("status",jsonObject.getInteger("status"));
        List<ThemeItemVo> themeItems = themeItemMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(themeItems);
        return ResponseFactory.page(themeItems, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 修改主题商品状态
     *
     * @param: [id 主题商品id , status 商品状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        ThemeItem themeItem = themeItemMapper.selectByPrimaryKey(id);
        if (themeItem == null) {
            return ResponseFactory.err("无此主题商品");
        }
        themeItem.setUpdated(new Date());
        themeItem.setStatus(status.byteValue());
        int count = themeItemMapper.updateByPrimaryKey(themeItem);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 主题商品排序
     *
     * @param: [themeItem 主题商品信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response setThemeItemSort(ThemeItem themeItem) {
        ThemeItem item = themeItemMapper.selectByPrimaryKey(themeItem.getId());
        if (item == null) {
            return ResponseFactory.err("无此主题商品");
        }
        item.setUpdated(new Date());
        item.setSort(themeItem.getSort());
        int count = themeItemMapper.updateByPrimaryKey(item);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 添加主题商品
     *
     * @param: [themeId 主题id, ids 商品id集合]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response addThemeItem(Integer themeId, String ids) {
        String[] arry = ids.split(",");
        List<ThemeItem> themeItems = new ArrayList<>();
        for(int i = 0; i < arry.length; i++) {
            int id = Integer.parseInt(arry[i]);
            ThemeItem themeItem = themeItemMapper.selectByThemeIdAndItemId(themeId, id);
            if (themeItem == null) {
                themeItem = new ThemeItem();
                themeItem.setStatus((byte)1);
                themeItem.setThemeId(themeId);
                themeItem.setItemId(id);
                themeItem.setCreated(new Date());
                themeItem.setUpdated(new Date());
                themeItems.add(themeItem);
                //themeItemMapper.insert(themeItem);
            } else {
                return ResponseFactory.err(id + "已经添加过，不能再重复添加");
            }
        }
        if (themeItems.size() > 0) {
            for(int i = 0; i < themeItems.size(); i++) {
                ThemeItem themeItem = themeItems.get(i);
                themeItemMapper.insert(themeItem);
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 删除主题商品
     *
     * @param: [id 主题商品id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response delThemeItem(Integer id) {
        int count = themeItemMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
