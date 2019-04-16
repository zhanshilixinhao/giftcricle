package com.chouchong.service.gift.virItem.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.virItem.VirtualItemBrandMapper;
import com.chouchong.entity.gift.virItem.VirtualItemBrand;
import com.chouchong.service.gift.virItem.VirItemBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/2
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class VirItemBrandServiceImpl implements VirItemBrandService{
    @Autowired
    private VirtualItemBrandMapper virtualItemBrandMapper;

    /**
     * 获得虚拟商品品牌列表
     *
     * @param: [page 分页信息, name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response getVirItemBrandList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<VirtualItemBrand> virtualItemBrands = virtualItemBrandMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(virtualItemBrands);
        return ResponseFactory.page(virtualItemBrands, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除虚拟商品品牌
     *
     * @param: [id 虚拟商品品牌id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response delVirItemBrand(Integer id) {
        int count = virtualItemBrandMapper.deleteByVirItemBrandId(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }

    /**
     * 添加虚拟商品品牌
     *
     * @param: [name 品牌名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response addVirItemBrand(String name) {
        VirtualItemBrand virtualItemBrand = new VirtualItemBrand();
        virtualItemBrand.setUpdated(new Date());
        virtualItemBrand.setStatus((byte)1);
        virtualItemBrand.setName(name);
        virtualItemBrand.setCreated(new Date());
        int count = virtualItemBrandMapper.insert(virtualItemBrand);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改虚拟商品品牌
     *
     * @param: [id 品牌id, name 品牌名称, sort 排序值]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response updateVirItemBrand(Integer id, String name) {
        VirtualItemBrand virtualItemBrand = virtualItemBrandMapper.selectByPrimaryKey(id);
        if (virtualItemBrand == null) {
            return ResponseFactory.err("虚拟商品品牌不存在!");
        }
        virtualItemBrand.setUpdated(new Date());
        virtualItemBrand.setName(name);
        int count = virtualItemBrandMapper.updateByPrimaryKey(virtualItemBrand);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 查询全部的虚拟商品品牌
     *
     * @param: []
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/2
     */
    @Override
    public Response getAllVirItemBrand() {
        List<VirtualItemBrand> virtualItemBrands = virtualItemBrandMapper.selectAllVirItemBrand();
        return ResponseFactory.sucData(virtualItemBrands);
    }
}
