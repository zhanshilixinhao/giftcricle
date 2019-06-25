package com.chouchong.service.gift.item.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.RecommendedTodayMapper;
import com.chouchong.entity.gift.item.RecommendedToday;
import com.chouchong.entity.gift.label.LabelItem;
import com.chouchong.service.gift.item.RecommendService;
import com.chouchong.service.gift.item.vo.RecommendVo;
import com.chouchong.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/6/24
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private RecommendedTodayMapper recommendedTodayMapper;


    /**
     * 获取今日推荐商品列表
     *
     * @param pageQuery
     * @param name      商品名称
     * @param day       日期
     * @return
     * @author linqin
     * @date 2019/6/24
     */
    @Override
    public Response getRecommendList(PageQuery pageQuery, String name, Long day) throws ParseException {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        Long start = null;
        Long endTime = null;
        if (day != null){
            start = TimeUtils.time(day);
             endTime = TimeUtils.timeEnd(day);
        }
        List<RecommendVo> labelItemVos = recommendedTodayMapper.selectBySearch(name,start,endTime);
        PageInfo pageInfo = new PageInfo<>(labelItemVos);
        return ResponseFactory.page(labelItemVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }



    /**
     * 添加今日推荐商品
     *
     * @param day 日期
     * @param ids 商品id s
     * @return
     * @author linqin
     * @date 2019/6/24
     */
    @Override
    public Response addRecommendItem(Long day, String ids) throws ParseException {
        String[] itemIds = ids.split(",");
        List<RecommendedToday> todays = new ArrayList<>();
        Long start = TimeUtils.time(day);
        Long endTime = TimeUtils.timeEnd(day);
        for (String id : itemIds) {
            Integer itemId = Integer.parseInt(id);
            RecommendedToday today = recommendedTodayMapper.selectByDayAndItemId(itemId,start,endTime);
            if (today == null){
                today = new RecommendedToday();
                today.setItemId(itemId);
                today.setSort(0);
                today.setStatus((byte)1);
                today.setDay(new Date(day));
                todays.add(today);
            }else {
                return ResponseFactory.err(id + "已经添加过，不能再重复添加");
            }
        }
        if (todays.size() > 0) {
            for (RecommendedToday today : todays) {
                recommendedTodayMapper.insert(today);
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 删除今日推荐商品
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response delRecommendItem(Integer id) {
        RecommendedToday today = recommendedTodayMapper.selectByPrimaryKey(id);
        if (today == null){
            return ResponseFactory.err("商品信息不存在");
        }
        today.setStatus((byte)-1);
        int update = recommendedTodayMapper.updateByPrimaryKeySelective(today);
        if (update < 1){
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 今日推荐商品排序
     *
     * @param id 今日推荐商品id
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response sortRecommendItem(Integer id,Integer sort) {
        RecommendedToday today = recommendedTodayMapper.selectByPrimaryKey(id);
        if (today == null){
            return ResponseFactory.err("商品信息不存在");
        }
        today.setSort(sort);
        int update = recommendedTodayMapper.updateByPrimaryKeySelective(today);
        if (update < 1){
            return ResponseFactory.err("设置失败");
        }
        return ResponseFactory.sucMsg("设置成功");
    }




}
