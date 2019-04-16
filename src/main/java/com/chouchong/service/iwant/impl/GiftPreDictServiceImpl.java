package com.chouchong.service.iwant.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.GiftPreDictMapper;
import com.chouchong.entity.iwant.appUser.GiftPreDict;
import com.chouchong.service.iwant.GiftPreDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yy
 * @date 2018/7/11
 **/

@Service
public class GiftPreDictServiceImpl implements GiftPreDictService{
    @Autowired
    private GiftPreDictMapper giftPreDictMapper;

    /**
     * 获得礼物偏好列表
     *
     * @param: [page 分页信息, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response getGiftPreList(PageQuery page, String name) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        List<GiftPreDict> giftPreDicts = giftPreDictMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(giftPreDicts);
        return ResponseFactory.page(giftPreDicts, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加礼物偏好
     *
     * @param: [name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response addGiftPre(String name) {
        GiftPreDict giftPreDict = new GiftPreDict();
        giftPreDict.setCreated(new Date());
        giftPreDict.setUpdated(new Date());
        giftPreDict.setText(name);
        int count = giftPreDictMapper.insert(giftPreDict);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改礼物偏好
     *
     * @param: [id 礼物偏好id, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response updateGiftPre(Integer id, String name) {
        GiftPreDict giftPreDict = giftPreDictMapper.selectByPrimaryKey(id);
        if (giftPreDict != null) {
            giftPreDict.setText(name);
            giftPreDict.setUpdated(new Date());
            int count = giftPreDictMapper.updateByPrimaryKey(giftPreDict);
            if (count == 1) {
                return ResponseFactory.sucMsg("修改成功");
            }
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 删除礼物偏好
     *
     * @param: [id 礼物偏好id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @Override
    public Response delGiftPre(Integer id) {
        int count = giftPreDictMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
