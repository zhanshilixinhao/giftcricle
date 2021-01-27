package com.chouchong.service.v4.impl;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.v3.StoreMapper;
import com.chouchong.dao.v4.RebateCouponManageMapper;
import com.chouchong.entity.v4.RebateCouponManage;
import com.chouchong.service.v3.vo.StoreVo;
import com.chouchong.service.v4.RebateCouponManageService;
import com.chouchong.service.webUser.vo.WebUserInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.chouchong.entity.v4.ShareCouponUser.TITLE;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/12/30 9:40
 */
@Service
public class RebateCouponManageServiceImpl implements RebateCouponManageService {

    @Resource
    private RebateCouponManageMapper rebateCouponManageMapper;

    @Resource
    private HttpServletRequest httpServletRequest;

    @Resource
    private StoreMapper storeMapper;

    /**
     * @param: rebateCouponManage
     * @Description: 后台添加折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:43
     */
    @Override
    public Response addRebateCouponManage(RebateCouponManage rebateCouponManage) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        Example example = new Example(RebateCouponManage.class);
        example.createCriteria().andEqualTo("status", 1).
                andEqualTo("adminId", webUserInfo.getSysAdmin().getId());
        RebateCouponManage manage = rebateCouponManageMapper.selectOneByExample(example);
        if (manage!=null) {
            return ResponseFactory.err("上限为一张");
        }
        rebateCouponManage.setAdminId(webUserInfo.getSysAdmin().getId()).
                setStatus((byte) 1).setCreated(new Date()).setUpdated(rebateCouponManage.getCreated());
        int i = rebateCouponManageMapper.insertSelective(rebateCouponManage);
        if (i>0) {
            return ResponseFactory.sucMsg("折扣券添加成功");
        } else {
            return ResponseFactory.err("折扣券添加失败");
        }
    }

    /**
     * @param： rebateCouponManage
     * @Description: 后台修改折扣券
     * @Author: LxH
     * @Date: 2020/12/30 9:59
     */
    @Override
    public Response updateRebateCouponManage(RebateCouponManage rebateCouponManage) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        RebateCouponManage manage = rebateCouponManageMapper.selectByPrimaryKey(rebateCouponManage.getId());
        if (manage == null) {
            return ResponseFactory.err("没有这张折扣券");
        }
        manage.setAdminId(webUserInfo.getSysAdmin().getId()).setTitle(rebateCouponManage.getTitle()).
                setSummary(rebateCouponManage.getSummary()).setRebateNew(rebateCouponManage.getRebateNew()).
                setRebateOld(rebateCouponManage.getRebateOld()).setStoreIds(rebateCouponManage.getStoreIds()).
                setUpdated(new Date()).setRebate(rebateCouponManage.getRebate());
        int i = rebateCouponManageMapper.updateByPrimaryKeySelective(manage);
        if (i>0) {
            return ResponseFactory.sucMsg("折扣券修改成功");
        } else {
            return ResponseFactory.err("折扣券修改失败");
        }
    }

    /**
     * @param: id
     * @Description: 后台删除折扣券
     * @Author: LxH
     * @Date: 2020/12/30 10:19
     */
    @Override
    public Response deleteRebateCouponManage(Integer id) {
        RebateCouponManage manage = rebateCouponManageMapper.selectByPrimaryKey(id);
        if (manage == null) {
            return ResponseFactory.err("折扣券不存在");
        }
        manage.setStatus((byte) 0);
        int i = rebateCouponManageMapper.updateByPrimaryKeySelective(manage);
        if (i>0) {
            return ResponseFactory.sucMsg("折扣券删除成功");
        } else {
            return ResponseFactory.err("折扣券删除失败");
        }
    }

    /**
     * @param: title
     * @param: page
     * @Description: 折扣券查询
     * @Author: LxH
     * @Date: 2020/12/30 10:27
     */
    @Override
    public Response getList(String title, PageQuery page) {
        WebUserInfo webUserInfo = (WebUserInfo) httpServletRequest.getAttribute("user");
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        if (webUserInfo.getRoleId() == 3) {
            Example example = new Example(RebateCouponManage.class);
            Example.Criteria criteria = example.createCriteria().andEqualTo("status", 1).
                    andEqualTo("adminId", webUserInfo.getSysAdmin().getId());
            if (StringUtils.isNotBlank(title)) {
                criteria.andLike("title","%"+title+"%");
            }
            List<RebateCouponManage> rebateCouponManages = rebateCouponManageMapper.selectByExample(example);
            for (RebateCouponManage rebateCouponManage : rebateCouponManages) {
                ArrayList<StoreVo> storeVos = new ArrayList<>();
                if (rebateCouponManage.getStoreIds() !=null) {
                    String[] split = rebateCouponManage.getStoreIds().split(",");
                    for (String s : split) {
                        StoreVo storeVo = storeMapper.selectById(Integer.parseInt(s));
                        storeVos.add(storeVo);
                    }
                }
                rebateCouponManage.setStoreVos(storeVos);
            }
            PageInfo pageInfo = new PageInfo<>(rebateCouponManages);
            return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }else {
            Example example = new Example(RebateCouponManage.class);
            Example.Criteria criteria = example.createCriteria().andEqualTo("status", 1).
                    andEqualTo("adminId", webUserInfo.getSysAdmin().getCreateAdminId());
            if (StringUtils.isNotBlank(title)) {
                criteria.andEqualTo(TITLE,"%"+title+"%");
            }
            List<RebateCouponManage> rebateCouponManages = rebateCouponManageMapper.selectByExample(example);
            PageInfo pageInfo = new PageInfo<>(rebateCouponManages);
            return ResponseFactory.page(pageInfo, pageInfo.getTotal(), pageInfo.getPages(),
                    pageInfo.getPageNum(), pageInfo.getPageSize());
        }
    }
}
