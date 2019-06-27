package com.chouchong.service.gift.article.impl;

import com.chouchong.common.ErrorCode;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.article.ArticleFestivalMapper;
import com.chouchong.dao.gift.article.ArticleItemMapper;
import com.chouchong.dao.gift.article.ArticleMapper;
import com.chouchong.dao.gift.article.ArticleSceneMapper;
import com.chouchong.entity.gift.article.Article;
import com.chouchong.entity.gift.article.ArticleFestival;
import com.chouchong.entity.gift.article.ArticleItem;
import com.chouchong.entity.gift.article.ArticleScene;
import com.chouchong.entity.gift.label.LabelItem;
import com.chouchong.entity.gift.themeItem.ThemeItem;
import com.chouchong.exception.ServiceException;
import com.chouchong.service.gift.article.ArticleService;
import com.chouchong.service.gift.article.ArticleVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/4
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleItemMapper articleItemMapper;

    @Autowired
    private ArticleSceneMapper articleSceneMapper;

    @Autowired
    private ArticleFestivalMapper articleFestivalMapper;


    /**
     * 通过文章标题获得文章列表
     *
     * @param: [page 分页信息, name 文章标题]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response getArticleList(PageQuery page, String name, Byte type) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Article> articles = articleMapper.selectByName(name, type);
        PageInfo pageInfo = new PageInfo<>(articles);
        return ResponseFactory.page(articles, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加文章（只可添加一篇banner文章）
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response addArticle(Article article) {
        if (article.getType() != null && article.getType() == 1) {
            //查询所有banner文章
            List<Article> articleList = articleMapper.selectAllBanner();
            if (!CollectionUtils.isEmpty(articleList)) {
                for (Article article1 : articleList) {
                    if (article1.getShowTime().getTime() == article.getShowTime().getTime()) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "该日期已经添加过banner文章");
                    }
                }
            }
        }
        article.setAdminId(1);
        article.setStatus((byte) 2);
        article.setShowTime(article.getShowTime());
        int count = articleMapper.insert(article);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }


    /**
     * 获取文章详情
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response getArticleDetail(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return ResponseFactory.err("无此文章");
        }
        return ResponseFactory.sucData(article);
    }

    /**
     * 修改文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/4
     */
    @Override
    public Response updateArticle(Article article) {
        Article article1 = articleMapper.selectByPrimaryKey(article.getId());
        if (article1 == null) {
            return ResponseFactory.err("无此文章");
        }
        if (article.getType() == 1) {
            //查询所有banner文章
            List<Article> articleList = articleMapper.selectAllBanner();
            if (!CollectionUtils.isEmpty(articleList)) {
                for (Article articles : articleList) {
                    if (articles.getShowTime().getTime() == article.getShowTime().getTime()) {
                        throw new ServiceException(ErrorCode.ERROR.getCode(), "该日期有对应的banner文章，请选择其他日期");
                    }
                }
            }
        }
        article1.setTitle(article.getTitle());
        article1.setSort(article.getSort());
        article1.setSummary(article.getSummary());
        article1.setCover(article.getCover());
        article1.setDetail(article.getDetail());
        article1.setType(article.getType());
        article1.setShowTime(article.getShowTime());
        article1.setSceneId(article.getSceneId());
        article1.setLabelId(article.getLabelId());
        article1.setFestivalId(article.getFestivalId());
        int count = articleMapper.updateByPrimaryKeySelective(article1);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 修改文章状态
     *
     * @param: [id 文章id, status 文章状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response updateArticleStatus(Integer id, Integer status) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return ResponseFactory.err("无此文章");
        }
        article.setUpdated(new Date());
        article.setStatus(status.byteValue());
        int count = articleMapper.updateByPrimaryKeySelective(article);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 删除文章
     *
     * @param: [id 文章id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @Override
    public Response delArticle(Integer id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            return ResponseFactory.err("无此文章");
        }
        article.setUpdated(new Date());
        article.setStatus((byte) 3);
        int count = articleMapper.updateByPrimaryKeySelective(article);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /*---------------------------------文章商品管理-------------------------------------*/

    /**
     * 获取文章商品列表
     *
     * @param id 文章id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @Override
    public Response getItemList(Integer id) {
        // 根据文章id查询商品
        List<ArticleVo> articleVos = articleItemMapper.selectByArticleId(id);
        return ResponseFactory.sucData(articleVos);
    }


    /**
     * 删除商品
     *
     * @param id 文章商品id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @Override
    public Response delArticleItem(Integer id) {
        ArticleItem articleItem = articleItemMapper.selectByPrimaryKey(id);
        if (articleItem == null) {
            return ResponseFactory.err("该文章商品不存在");
        }
        int count = articleItemMapper.deleteByPrimaryKey(id);
        if (count < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 添加文章商品
     *
     * @param articleId 文章id
     * @param ids       商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addArticleItem(Integer articleId, String ids) {
        String[] arry = ids.split(",");
        List<ArticleItem> articleItems = new ArrayList<>();
        for (int i = 0; i < arry.length; i++) {
            int id = Integer.parseInt(arry[i]);
            // 通过文章id和商品id获得商品
            ArticleItem articleItem = articleItemMapper.selectByThemeIdAndItemId(articleId, id);
            if (articleItem == null) {
                articleItem = new ArticleItem();
                articleItem.setArticleId(articleId);
                articleItem.setItemId(id);
                articleItem.setCreated(new Date());
                articleItem.setUpdated(new Date());
                articleItems.add(articleItem);
                //themeItemMapper.insert(themeItem);
            } else {
                return ResponseFactory.err(id + "已经添加过，不能再重复添加");
            }
        }
        if (articleItems.size() > 0) {
            for (int i = 0; i < articleItems.size(); i++) {
                ArticleItem item = articleItems.get(i);
                articleItemMapper.insert(item);
            }
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /*---------------------------------文章场景管理-------------------------------------*/

    /**
     * 获取文章场景列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getSceneList(PageQuery page, String title) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<ArticleScene> articles = articleSceneMapper.selectBySearch(title);
        PageInfo pageInfo = new PageInfo<>(articles);
        return ResponseFactory.page(articles, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response addScene(String title) {
        ArticleScene scene = articleSceneMapper.selectByTitle(title);
        if (scene != null) {
            return ResponseFactory.err("该场景已经存在，不能再添加");
        }
        scene = new ArticleScene();
        scene.setTitle(title);
        int insert = articleSceneMapper.insert(scene);
        if (insert < 1) {
            return ResponseFactory.err("添加失败");
        }
        return ResponseFactory.sucMsg("添加成功");
    }


    /**
     * 修改文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response updateScene(Integer id, String title) {
        ArticleScene scene = articleSceneMapper.selectByPrimaryKey(id);
        if (scene == null) {
            return ResponseFactory.err("该场景不存在");
        }
        ArticleScene articleScene = articleSceneMapper.selectByTitle(title);
        if (!articleScene.getId().equals(id)) {
            return ResponseFactory.err("该场景已经存在，不能再添加");
        }
        scene.setTitle(title);
        int update = articleSceneMapper.updateByPrimaryKeySelective(scene);
        if (update < 1) {
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 删除文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response delScene(Integer id) {
        int i = articleSceneMapper.deleteByPrimaryKey(id);
        if (i < 1) {
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }


    /**
     * 获取所有文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getSceneListAll() {
        List<ArticleScene> articles = articleSceneMapper.selectByAll();
        return ResponseFactory.sucData(articles);
    }


    /**
     * 获取所有文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @Override
    public Response getFestivalListAll() {
        List<ArticleFestival> festivals = articleFestivalMapper.selectByAll();
        return ResponseFactory.sucData(festivals);
    }
}
