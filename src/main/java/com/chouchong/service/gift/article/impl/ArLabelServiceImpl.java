package com.chouchong.service.gift.article.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.article.ArticleLabelMapper;
import com.chouchong.entity.gift.article.ArticleLabel;
import com.chouchong.entity.gift.article.ArticleScene;
import com.chouchong.service.gift.article.ArLabelService;
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
public class ArLabelServiceImpl implements ArLabelService {

    @Autowired
    private ArticleLabelMapper articleLabelMapper;

    /**
     * 获取文章对象列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getArticleLabel(PageQuery page, String title) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ArticleLabel> articles = articleLabelMapper.selectBySearch(title);
        PageInfo pageInfo = new PageInfo<>(articles);
        return ResponseFactory.page(articles, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }



    /**
     * 添加文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addArLabel(String title) {
        ArticleLabel label = articleLabelMapper.selectByTitle(title);
        if (label != null){
            return ResponseFactory.err("该对象已经存在，不能再添加");
        }
        label = new ArticleLabel();
        label.setTitle(title);
        int insert = articleLabelMapper.insert(label);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response updateArLabel(Integer id, String title) {
        ArticleLabel label = articleLabelMapper.selectByPrimaryKey(id);
        if ( label == null){
            return ResponseFactory.err("该对象不存在");
        }
        ArticleLabel arlabel = articleLabelMapper.selectByTitle(title);
        if (arlabel != null && !arlabel.getId().equals(id)){
            return ResponseFactory.err("该对象已经存在，不能再添加");
        }
        label.setTitle(title);
        int update = articleLabelMapper.updateByPrimaryKeySelective(label);
        if (update < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response delArLabel(Integer id) {
        int i = articleLabelMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }
}
