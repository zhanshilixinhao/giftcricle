package com.chouchong.service.home.festival.impl;

import com.chouchong.common.*;
import com.chouchong.dao.home.FestivalMapper;
import com.chouchong.entity.home.Festival;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.home.festival.FestivalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author linqin
 * @date 2019/1/18 11:02
 */
@Service
@Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
public class FestivalServiceImpl implements FestivalService {

    @Autowired
    private FestivalMapper festivalMapper;

    /**
     * 获取节日列表
     *
     * @param name 节日名称
     * @param time 节日日期
     * @return
     * @author linqin
     * * @date 2019/1/18 10:59
     */
    @Override
    public Response getFestivalList(PageQuery page, String name, Long time) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        // 后台获取节日列表
        List<Festival> festivals = festivalMapper.selectByName(name, time);
        PageInfo pageInfo = new PageInfo<>(festivals);
        return ResponseFactory.page(festivals, pageInfo.getTotal(), pageInfo.getPages(),
                pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 添加节日
     *
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @Override
    public Response addFestival(Festival festival) {
        //查询所有节日
        List<Festival> list = festivalMapper.selectAll();
        if (!CollectionUtils.isEmpty(list)){
            for (Festival festival1 : list) {
                if (festival1.getTargetDate().getTime() == festival.getTargetDate().getTime()){
                    throw new ServiceException(ErrorCode.ERROR.getCode(),"该日期已经添加过节日");
                }
            }
        }
        Festival fe = new Festival();
        fe.setFestival(festival.getFestival());
        fe.setTargetDate(festival.getTargetDate());
        int insert = festivalMapper.insert(fe);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改节日
     *
     * @param festival
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @Override
    public Response updateFestival(Festival festival) {
        Festival festival1 = new Festival();
        festival1.setId(festival.getId());
        if (StringUtils.isNotBlank(festival.getFestival())) {
            festival1.setFestival(festival.getFestival());
        }
        int i = festivalMapper.updateByPrimaryKeySelective(festival1);
        if (i < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除节日
     *
     * @param id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @Override
    public Response delFestival(Integer id) {
        Festival festival = festivalMapper.selectByPrimaryKey(id);
        if (festival == null){
            return ResponseFactory.err("该节日不存在");
        }
        int i = festivalMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * 节日详情
     *
     * @param id 节日id
     * @return
     * @author linqin
     * @date 2019/1/18 10:59
     */
    @Override
    public Response festivalDetail(Integer id) {
        Festival festival = festivalMapper.selectByPrimaryKey(id);
        if (festival == null){
            return ResponseFactory.err("该节日不存在");
        }
        return ResponseFactory.sucData(festival);
    }
}
