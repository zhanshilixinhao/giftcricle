package com.chouchong.service.gift.article.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.article.ArticleFestivalMapper;
import com.chouchong.entity.gift.article.ArticleFestival;
import com.chouchong.service.gift.article.ArFestivalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author linqin
 * @date 2019/7/30
 */
@Service
public class ArFestivalServiceImpl implements ArFestivalService {

    @Autowired
    private ArticleFestivalMapper articleFestivalMapper;

    /**
     * 获取文章节日列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getArticleFestival(PageQuery page, String title) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ArticleFestival> articles = articleFestivalMapper.selectBySearch(title);
        PageInfo pageInfo = new PageInfo<>(articles);
        return ResponseFactory.page(articles, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }



    /**
     * 添加文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addArFestival(String title) {
        ArticleFestival Festival = articleFestivalMapper.selectByTitle(title);
        if (Festival != null){
            return ResponseFactory.err("该节日已经存在，不能再添加");
        }
        Festival = new ArticleFestival();
        Festival.setTitle(title);
        int insert = articleFestivalMapper.insert(Festival);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response updateArFestival(Integer id, String title) {
        ArticleFestival Festival = articleFestivalMapper.selectByPrimaryKey(id);
        if ( Festival == null){
            return ResponseFactory.err("该节日不存在");
        }
        ArticleFestival arFestival = articleFestivalMapper.selectByTitle(title);
        if (arFestival != null && !arFestival.getId().equals(id)){
            return ResponseFactory.err("该节日已经存在，不能再添加");
        }
        Festival.setTitle(title);
        int update = articleFestivalMapper.updateByPrimaryKeySelective(Festival);
        if (update < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response delArFestival(Integer id) {
        int i = articleFestivalMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }
}
