package com.chouchong.service.gift.article;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.entity.gift.article.Article;

public interface ArticleService {
    /**
     * 通过文章标题获得文章列表
     *
     * @param: [page 分页信息, name 文章标题]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response getArticleList(PageQuery page, String name,Byte type);

    /**
     * 添加文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response addArticle(Article article);

    /**
     * 添加文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response getArticleDetail(Integer id);

    /**
     * 修改文章
     *
     * @param: [article 文章信息]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response updateArticle(Article article);

    /**
     * 修改文章状态
     *
     * @param: [id 文章id, status 文章状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response updateArticleStatus(Integer id, Integer status);

    /**
     * 删除文章
     *
     * @param: [id 文章id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/5
     */
    Response delArticle(Integer id);

    /*---------------------------------文章商品管理-------------------------------------*/
    /**
     * 获取文章商品列表
     * @param id  文章id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    Response getItemList(Integer id);

    /**
     * 删除商品
     * @param id  文章商品id
     * @return
     * @author: linqin
     * @Date: 2019/1/16
     */
    Response delArticleItem(Integer id);

    /**
     * 添加文章商品
     *
     * @param articleId 文章id
     * @param ids     商品id集合
     * @return
     * @author linqin
     * @date 2019/1/15 11:25
     */
    Response addArticleItem(Integer articleId, String ids);
}
