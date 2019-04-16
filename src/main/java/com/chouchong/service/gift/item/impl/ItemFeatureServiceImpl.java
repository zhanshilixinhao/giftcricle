package com.chouchong.service.gift.item.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.ItemFeatureMapper;
import com.chouchong.entity.gift.item.ItemFeature;
import com.chouchong.service.gift.item.ItemFeatureService;
import com.chouchong.service.gift.item.vo.FeatureVo;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/6/28
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class ItemFeatureServiceImpl implements ItemFeatureService{
    @Autowired
    private ItemFeatureMapper itemFeatureMapper;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 根据商品属性名称查询商品属性列表
     *
     * @param: [page, name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response getItemFeatureList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        // 平台商登录
        Integer adminId = null;
        if (webUserInfo.getRoleId() == 3){
            adminId = webUserInfo.getSysAdmin().getId();
        }
        List<FeatureVo> itemFeatures = itemFeatureMapper.selectByName(name,adminId);
        PageInfo pageInfo = new PageInfo<>(itemFeatures);
        return ResponseFactory.page(itemFeatures,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加商品属性
     *
     * @param: [name 属性名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response addItemFeature(String name) {
        // 平台商登录
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        ItemFeature itemFeature = new ItemFeature();
        itemFeature.setValues("");
        itemFeature.setSort(0);
        itemFeature.setUpdated(new Date());
        itemFeature.setStatus((byte)1);
        itemFeature.setName(name);
        itemFeature.setIsSelect((byte)1);
        itemFeature.setCreated(new Date());
        itemFeature.setAdminId(webUserInfo.getSysAdmin().getId());
        int count = itemFeatureMapper.insert(itemFeature);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功!");
        }
        return ResponseFactory.err("添加失败!");
    }


    /**
     * 修改商品属性
     *
     * @param: [id 属性id, name 属性名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response updateItemFeature(Integer id, String name, Integer sort) {
        ItemFeature itemFeature = itemFeatureMapper.selectByPrimaryKey(id);
        if (itemFeature == null) {
            return ResponseFactory.err("无此商品属性");
        }
        itemFeature.setSort(sort);
        itemFeature.setName(name);
        itemFeature.setUpdated(new Date());
        int count = itemFeatureMapper.updateByPrimaryKey(itemFeature);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功!");
        }
        return ResponseFactory.err("修改失败!");
    }

    /**
     * 删除商品属性
     *
     * @param: [id 属性id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response delItemFeature(Integer id) {
        int count = itemFeatureMapper.deleteByItemFeatureId(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功!");
        }
        return ResponseFactory.err("删除失败!");
    }

    /**
     * 查询全部的商品属性
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/29
     */
    @Override
    public Response getAllItemFeature() {
        List<ItemFeature> itemFeatures = itemFeatureMapper.getAllItemFeature();
        return ResponseFactory.sucData(itemFeatures);
    }
}
