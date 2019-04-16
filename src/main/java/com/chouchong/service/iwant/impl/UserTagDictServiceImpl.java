package com.chouchong.service.iwant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.UserTagDictMapper;
import com.chouchong.entity.iwant.appUser.UserTagDict;
import com.chouchong.service.iwant.UserTagDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/10
 **/

@Service
public class UserTagDictServiceImpl implements UserTagDictService {
    @Autowired
    private UserTagDictMapper userTagDictMapper;

    /**
     * 获得标签列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @Override
    public Response getTagDictList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("tag",jsonObject.getString("tag"));
        map.put("type",jsonObject.getInteger("type"));
        map.put("status",jsonObject.getInteger("status"));
        List<UserTagDict> userTagDicts = userTagDictMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(userTagDicts);
        return ResponseFactory.page(userTagDicts, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 添加印象标签
     *
     * @param: [tag 标签名称, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @Override
    public Response addTagDict(String tag, Integer type) {
        UserTagDict userTagDict = new UserTagDict();
        userTagDict.setCreated(new Date());
        userTagDict.setUpdated(new Date());
        userTagDict.setType(type.byteValue());
        userTagDict.setTag(tag);
        userTagDict.setStatus((byte)1);
        int count = userTagDictMapper.insert(userTagDict);
        if (count == 1) {
            return ResponseFactory.sucMsg("添加成功");
        }
        return ResponseFactory.err("添加失败");
    }

    /**
     * 修改印象标签
     *
     * @param: [id 标签id, tag 标签内容, type 标签类型]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/10
     */
    @Override
    public Response updateTagDict(Integer id, String tag, Integer type) {
        UserTagDict userTagDict = userTagDictMapper.selectByPrimaryKey(id);
        if (userTagDict == null) {
            return ResponseFactory.err("无此印象标签");
        }
        userTagDict.setUpdated(new Date());
        userTagDict.setTag(tag);
        userTagDict.setType(type.byteValue());
        int count = userTagDictMapper.updateByPrimaryKey(userTagDict);
        if (count == 1) {
            return ResponseFactory.sucMsg("修改成功");
        }
        return ResponseFactory.err("修改失败");
    }

    /**
     * 修改印象标签状态
     *
     * @param: [id 标签id, status 标签状态]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response changeStatus(Integer id, Integer status) {
        UserTagDict userTagDict = userTagDictMapper.selectByPrimaryKey(id);
        if (userTagDict == null) {
            return ResponseFactory.err("无此印象标签");
        }
        userTagDict.setUpdated(new Date());
        userTagDict.setStatus(status.byteValue());
        int count = userTagDictMapper.updateByPrimaryKey(userTagDict);
        if (count == 1) {
            return ResponseFactory.sucMsg("设置成功");
        }
        return ResponseFactory.err("设置失败");
    }

    /**
     * 删除印象标签
     *
     * @param: [id 标签id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @Override
    public Response delTag(Integer id) {
        UserTagDict userTagDict = userTagDictMapper.selectByPrimaryKey(id);
        if (userTagDict == null) {
            return ResponseFactory.err("无此印象标签");
        }
        userTagDict.setUpdated(new Date());
        userTagDict.setStatus((byte)3);
        int count = userTagDictMapper.updateByPrimaryKey(userTagDict);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
