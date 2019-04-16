package com.chouchong.service.iwant.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.iwant.appUser.SuggestionMapper;
import com.chouchong.service.iwant.SuggestionService;
import com.chouchong.service.iwant.vo.SuggestionVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yy
 * @date 2018/7/25
 **/

@Service
@Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
public class SuggestionServiceImpl implements SuggestionService{
    @Autowired
    private SuggestionMapper suggestionMapper;

    /**
     * 获取意见反馈列表
     *
     * @param: [page 分页信息, search 查询条件]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response getSuggestionList(PageQuery page, String search) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        JSONObject jsonObject = JSON.parseObject(search);
        Map map = new HashMap();
        map.put("nickname",jsonObject.getString("nickname"));
        map.put("contactWay",jsonObject.getString("contactWay"));
        map.put("feedback",jsonObject.getString("feedback"));
        List<SuggestionVo> suggestionVos = suggestionMapper.selectBySearch(map);
        PageInfo pageInfo = new PageInfo<>(suggestionVos);
        return ResponseFactory.page(suggestionVos, pageInfo.getTotal(),
                pageInfo.getPages(), pageInfo.getPageNum(), pageInfo.getPageSize());
    }

    /**
     * 删除意见反馈
     *
     * @param: [id 意见反馈id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/25
     */
    @Override
    public Response delSuggestion(Integer id) {
        int count = suggestionMapper.deleteByPrimaryKey(id);
        if (count == 1) {
            return ResponseFactory.sucMsg("删除成功");
        }
        return ResponseFactory.err("删除失败");
    }
}
