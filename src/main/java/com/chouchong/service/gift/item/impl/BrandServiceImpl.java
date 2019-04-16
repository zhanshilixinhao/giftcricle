package com.chouchong.service.gift.item.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.item.BrandMapper;
import com.chouchong.entity.gift.item.Brand;
import com.chouchong.service.gift.item.BrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/6/25
 **/

@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandMapper brandMapper;

    /**
     * 根据品牌名称查询品牌列表
     *
     * @param: [page, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getBrandList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<Brand> brandList = brandMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(brandList);
        return ResponseFactory.page(brandList,pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加品牌
     *
     * @param: [name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response addBrand(String name, String logo) {
        Brand brand = new Brand();
        brand.setUpdated(new Date());
        brand.setStatus((byte)1);
        brand.setName(name);
        brand.setLogo(logo);
        brand.setCreated(new Date());
        int count = brandMapper.insert(brand);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("添加品牌失败!");
    }

    /**
     * 修改品牌
     *
     * @param: [id 品牌id, name 品牌名称, logo 品牌logo]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response updateBrand(Integer id, String name, String logo) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        if (brand == null) {
            return ResponseFactory.err("无此品牌!");
        }
        brand.setLogo(logo);
        brand.setUpdated(new Date());
        brand.setName(name);
        int count = brandMapper.updateByPrimaryKey(brand);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("修改品牌失败!");
    }

    /**
     * 删除品牌
     *
     * @param: [id 品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response delBrand(Integer id) {
        int count = brandMapper.deleteByUpdateStatus(id);
        if (count == 1) {
            return ResponseFactory.suc();
        }
        return ResponseFactory.err("删除品牌失败!");
    }

    /**
     * 获得全部的商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/6/27
     */
    @Override
    public Response getAllBrand() {
        List<Brand> brandList = brandMapper.selectAllBrand();
        return ResponseFactory.sucData(brandList);
    }
}
