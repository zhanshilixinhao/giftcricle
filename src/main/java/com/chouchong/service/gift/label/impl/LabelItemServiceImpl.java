package com.chouchong.service.gift.label.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.label.LabelItemMapper;
import com.chouchong.entity.gift.label.LabelItem;
import com.chouchong.entity.gift.themeItem.ThemeItem;
import com.chouchong.service.gift.label.LabelItemService;
import com.chouchong.service.gift.label.vo.LabelItemVo;
import com.chouchong.service.gift.themeItem.vo.ThemeItemVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author linqin
 * @date 2019/1/15 11:27
 */
@Service
public class LabelItemServiceImpl  implements LabelItemService {


    @Autowired
    private LabelItemMapper labelItemMapper;

    /**
     * 获取标签商品列表
     * @param pageQuery
     * @param search
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getLabelItemList(PageQuery pageQuery, String search) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("name",jsonObject.getString("name"));
        map.put("labelId",jsonObject.getInteger("labelId"));
        map.put("status",jsonObject.getInteger("status"));
        List<LabelItemVo> labelItemVos = labelItemMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(labelItemVos);
        return ResponseFactory.page(labelItemVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 修改主题商品状态
     * @param id
     * @param status
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        LabelItem labelItem = labelItemMapper.selectByPrimaryKey(id);
        if (labelItem == null) {
            return ResponseFactory.err("无此标签商品");
        }
        labelItem.setStatus(status.byteValue());
        int count = labelItemMapper.updateByPrimaryKeySelective(labelItem);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }


    /**
     * 删除商品
     *
     * @param id 标签商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response delLabelItem(Integer id) {
        int count = labelItemMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 添加标签商品
     * @param labelId 标签id
     * @param ids 商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addLabelItem(Integer labelId, String ids) {
        String[] arry = ids.split(",");
        List<LabelItem> labelItems = new ArrayList<>();
        for(int i = 0; i < arry.length; i++) {
            int id = Integer.parseInt(arry[i]);
            // 通过标签id和商品id获得商品
            LabelItem labelItem = labelItemMapper.selectByThemeIdAndItemId(labelId, id);
            if (labelItem == null) {
                labelItem = new LabelItem();
                labelItem.setStatus((byte)1);
                labelItem.setLabelId(labelId);
                labelItem.setItemId(id);
                labelItem.setCreated(new Date());
                labelItem.setUpdated(new Date());
                labelItem.setSort(0);
                labelItems.add(labelItem);
                //themeItemMapper.insert(themeItem);
            } else {
                return ResponseFactory.err(id + "已经添加过，不能再重复添加");
            }
        }
        if (labelItems.size() > 0) {
            for(int i = 0; i < labelItems.size(); i++) {
                LabelItem labelItem = labelItems.get(i);
                labelItemMapper.insert(labelItem);
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 标签商品排序
     *
     * @param
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response setThemeItemSort(LabelItem labelItem) {
        LabelItem item = labelItemMapper.selectByPrimaryKey(labelItem.getId());
        if (item == null) {
            return ResponseFactory.err("无此标签商品");
        }
        item.setSort(labelItem.getSort());
        int count = labelItemMapper.updateByPrimaryKeySelective(item);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }
}
