package com.chouchong.service.gift.label.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.gift.label.LabelMapper;
import com.chouchong.entity.gift.label.Label;
import com.chouchong.service.gift.label.LabelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.SelectableChannel;
import java.util.List;

/**
 * @author linqin
 * @date 2019/1/14 14:11
 */
@Service
@Transactional(rollbackFor = Exception.class,isolation = Isolation.REPEATABLE_READ)
public class LabelServiceImpl implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    /**
     * 获取标签列表
     * @param pageQuery
     * @param name
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @Override
    public Response getLabelList(PageQuery pageQuery, String name) {
        PageHelper.startPage(pageQuery.getPageNum(),pageQuery.getPageSize());
        // 根据名称获取列表
        List<Label> labels = labelMapper.selectByName(name);
        PageInfo pageInfo = new PageInfo<>(labels);
        return ResponseFactory.page(labels, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }


    /**
     * 添加标签
     *
     * @param name
     * @param cover
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @Override
    public Response addLabel(String name, String cover) {
        Label label = new Label();
        label.setName(name);
        label.setCover(cover);
        label.setStatus((byte)2);
        label.setSort(0);
        int insert = labelMapper.insert(label);
        if (insert < 1){
            return ResponseFactory.err("添加标签失败");
        }
        return ResponseFactory.sucMsg("添加标签成功");
    }

    /**
     * 修改标签
     *
     * @param label
     * @return
     * @author linqin
     * * @date 2019/1/14 10:39
     */
    @Override
    public Response updateLabel(Label label) {
        // 根据id查询标签
        Label label1 = labelMapper.selectById(label.getId());
        if (label1 == null){
            return ResponseFactory.err("无此标签或已被删除");
        }
        label1.setName(label.getName());
        label1.setCover(label.getCover());
        label1.setSort(label.getSort());
        int i = labelMapper.updateByPrimaryKeySelective(label1);
        if (i < 1){
            return ResponseFactory.err("修改失败");
        }
        return ResponseFactory.sucMsg("修改成功");
    }

    /**
     * 删除标签
     * @param labelId 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @Override
    public Response deleteLabel(Integer labelId) {
        Label label = labelMapper.selectByPrimaryKey(labelId);
        if (label == null){
            return ResponseFactory.err("无此标签");
        }
        // 根据id删除
        Byte status = -1;
        int i = labelMapper.updateStatusById(labelId,status);
        if (i < 1){
            return ResponseFactory.err("删除失败");
        }
        return ResponseFactory.sucMsg("删除成功");
    }

    /**
     * 改变标签状态
     * @param id 标签id
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @Override
    public Response updateStatus(Integer id) {
        Label label = labelMapper.selectById(id);
        if (label == null){
            return ResponseFactory.err("无此标签或已被删除");
        }
        if (label.getStatus() == 1){
            Byte status = 2;
            int i = labelMapper.updateStatusById(id,status);
            if (i < 1){
                return ResponseFactory.err("修改失败");
            }
        }else {
            Byte status = 1 ;
            int i = labelMapper.updateStatusById(id,status);
            if (i < 1){
                return ResponseFactory.err("修改失败");
            }
        }
        return ResponseFactory.sucMsg("修改成功");
    }


    /**
     * 获取所有的标签
     *
     * @return
     * @author linqin
     * @date 2019/1/14 10:39
     */
    @Override
    public Response getAllList() {
        List<Label> labels = labelMapper.selectAll();
        return ResponseFactory.sucData(labels);
    }

}
