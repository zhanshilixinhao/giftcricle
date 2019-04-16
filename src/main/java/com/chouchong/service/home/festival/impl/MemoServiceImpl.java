package com.chouchong.service.home.festival.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.home.memo.MemoFestivalItemMapper;
import com.chouchong.dao.home.memo.MemoFestivalMapper;
import com.chouchong.entity.gift.article.ArticleItem;
import com.chouchong.entity.home.Festival;
import com.chouchong.entity.home.memo.MemoFestival;
import com.chouchong.entity.home.memo.MemoFestivalItem;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.article.ArticleVo;
import com.chouchong.service.home.festival.MemoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/3/15 15:31
 */
@Service
public class MemoServiceImpl implements MemoService {

    @Autowired
    private MemoFestivalMapper memoFestivalMapper;

    @Autowired
    private MemoFestivalItemMapper memoFestivalItemMapper;

    /**
     * 获取备忘录节日事件列表
     *
     * @param page
     * @param name
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @Override
    public Response getMemoFestivalList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<MemoFestival> festivals = memoFestivalMapper.selectAllByName(name);
        PageInfo info = new PageInfo<>(festivals);
        return ResponseFactory.page(festivals, info.getTotal(), info.getPages(),
                info.getPageNum(), info.getPageSize());
    }

    /**
     * 添加备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @Override
    public Response addMemoFestival(MemoFestival memoFestival) {
        //查询所有节日事件
        List<MemoFestival> memos = memoFestivalMapper.selectAll();
        if (!CollectionUtils.isEmpty(memos)) {
            for (MemoFestival festival : memos) {
                if (festival.getTargetTime().getTime() == memoFestival.getTargetTime().getTime()) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该日期已经添加过节日事件");
                }
            }
        }
        int insert = memoFestivalMapper.insert(memoFestival);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }

    /**
     * 修改备忘录节日事件
     *
     * @param memoFestival
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @Override
    public Response updateMemoFestival(MemoFestival memoFestival) {
        //查询所有节日事件
        List<MemoFestival> memos = memoFestivalMapper.selectAll();
        if (!CollectionUtils.isEmpty(memos)) {
            for (MemoFestival festival : memos) {
                if (festival.getTargetTime().getTime() == memoFestival.getTargetTime().getTime() && !memoFestival.getId().equals(festival.getId())) {
                    throw new ServiceException(ErrorCode.ERROR.getCode(), "该日期已经添加过节日事件");
                }
            }
        }
        int i = memoFestivalMapper.updateByPrimaryKeySelective(memoFestival);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * 删除节日事件
     *
     * @param id 节日事件列表
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @Override
    public Response delMemoFestival(Integer id) {
        MemoFestival memoFestival = memoFestivalMapper.selectByPrimaryKey(id);
        if (memoFestival == null) {
            return ResponseFactory.err("节日事件不存在");
        }
        int i = memoFestivalMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * 节日事件详情
     *
     * @param id 节日事件列表id
     * @return
     * @author linqin
     * @date 2019/3/15
     */
    @Override
    public Response detailMemoFestival(Integer id) {
        MemoFestival memoFestival = memoFestivalMapper.selectByPrimaryKey(id);
        if (memoFestival == null) {
            return ResponseFactory.err("节日事件信息有误");
        }
        return ResponseFactory.sucData(memoFestival);
    }


    /*-----------------------------------------商品管理-------------------------------------------------*/

    /**
     * 获取节日商品列表
     *
     * @param id 节日事件id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @Override
    public Response getItemList(Integer id) {
        // 根据节日事件id查询商品
        List<ArticleVo> memoItem = memoFestivalItemMapper.selectByMemoFestivalId(id);
        return ResponseFactory.sucData(memoItem);
    }


    /**
     * 删除商品
     *
     * @param id 节日事件id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @Override
    public Response delFestivalItem(Integer id) {
        MemoFestivalItem item = memoFestivalItemMapper.selectByPrimaryKey(id);
        if (item == null) {
            return ResponseFactory.err("该商品不存在");
        }
        int count = memoFestivalItemMapper.deleteByPrimaryKey(id);
        if (count < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 添加节日事件商品
     *
     * @param festivalId 节日事件id
     * @param ids        商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addFestivalItem(Integer festivalId, String ids) {
        String[] arry = ids.split(",");
        List<MemoFestivalItem> items = new ArrayList<>();
        for (int i = 0; i < arry.length; i++) {
            int id = Integer.parseInt(arry[i]);
            // 通过节日事件id和商品id获得商品
            MemoFestivalItem festivalItem = memoFestivalItemMapper.selectByFestivalIdAndItemId(festivalId, id);
            if (festivalItem == null) {
                festivalItem = new MemoFestivalItem();
                festivalItem.setMemoFestivalId(festivalId);
                festivalItem.setItemId(id);
                festivalItem.setCreated(new Date());
                festivalItem.setUpdated(new Date());
                items.add(festivalItem);
                //themeItemMapper.insert(themeItem);
            } else {
                return ResponseFactory.err(id + "已经添加过，不能再重复添加");
            }
        }
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                MemoFestivalItem item = items.get(i);
                memoFestivalItemMapper.insert(item);
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }


}
