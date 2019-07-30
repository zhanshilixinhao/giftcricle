package com.chouchong.controller.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.entity.gift.article.Article;
import com.chouchong.service.gift.article.ArticleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/4
 **/

@RestController
@RequestMapping("manage/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 通过文章标题获得文章列表
     *
     * @param: [page 分页信息, name 文章标题 type 文章类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("list")
    public Response getArticleList(PageQuery page, String name, Byte type) {
        return articleService.getArticleList(page, name, type);
    }

    /**
     * 添加文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("add")
    public Response addArticle(Article article) {
        if (article == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (article.getCover() == null || article.getDetail() == null || article.getSummary() == null
                || article.getTitle() == null || article.getType() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.addArticle(article);
    }

    /**
     * 获得文章详情
     *
     * @param: [id 文章id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("detail")
    public Response getArticleDetail(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.getArticleDetail(id);
    }

    /**
     * 修改文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("update")
    public Response updateArticle(Article article) {
        if (article == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (article.getCover() == null || article.getDetail() == null || article.getSummary() == null
                || article.getTitle() == null || article.getId() == null || article.getType() == null) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.updateArticle(article);
    }

    /**
     * 修改文章状态
     *
     * @param: [id 文章id, status 文章状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("status")
    public Response updateArticleStatus(Integer id, Integer status) {
        if (id == null || status == null) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.updateArticleStatus(id, status);
    }

    /**
     * 删除文章
     *
     * @param: [id 文章id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    @PostMapping("del")
    public Response delArticle(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.delArticle(id);
    }

    /*---------------------------------文章商品管理-------------------------------------*/

    /**
     * 获取文章商品列表
     * @param id  文章id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @PostMapping("item_list")
    public Response getItemList(Integer id) {
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return articleService.getItemList(id);
    }

    /**
     * 删除商品
     * @param id  文章商品id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    @PostMapping("del_item")
    public Response delArticleItem( Integer id){
        if (id == null){
            return ResponseFactory.errMissingParameter();
        }
        return articleService.delArticleItem(id);
    }



    /**
     * 添加文章商品
     *
     * @param articleId 文章id
     * @param ids     商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("add_item")
    public Response addArticleItem(Integer articleId, String ids) {
        if (articleId == null || ids == null) {
            return ResponseFactory.errMissingParameter();
        }
        if (ids.trim().equals("")) {
            return ResponseFactory.errMissingParameter();
        }
        return articleService.addArticleItem(articleId, ids);
    }


    /*---------------------------------文章场景管理-------------------------------------*/

    /**
     * 获取文章场景列表
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("scene_list")
    public Response getSceneList(PageQuery page,String title) {

        return articleService.getSceneList(page, title);
    }


    /**
     * 添加文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("scene_add")
    public Response addScene(String title) {
        if (StringUtils.isBlank(title)){
            return ResponseFactory.errMissingParameter();
        }
        return articleService.addScene( title);
    }


    /**
     * 修改文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("scene_update")
    public Response updateScene(Integer id,String title) {
        if (StringUtils.isBlank(title) || id == null){
            return ResponseFactory.errMissingParameter();
        }
        return articleService.updateScene(id, title);
    }



    /**
     * 删除文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("scene_del")
    public Response delScene(Integer id) {
        if ( id == null){
            return ResponseFactory.errMissingParameter();
        }
        return articleService.delScene(id);
    }

    /**
     * 获取所有文章场景
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("scene_list_all")
    public Response getSceneListAll() {
        return articleService.getSceneListAll();
    }

    /**
     * 获取所有文章节日
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("festival_list")
    public Response getFestivalListAll() {
        return articleService.getFestivalListAll();
    }

     /** 获取所有文章对象
     *
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    @PostMapping("label_list")
    public Response getArLabelListAll() {
        return articleService.getArLabelListAll();
    }


}
