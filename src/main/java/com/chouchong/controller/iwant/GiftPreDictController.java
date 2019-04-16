package com.chouchong.controller.iwant;

import com.chouchong.common.PageQuery;
import com.chouchong.common.Response;
import com.chouchong.common.ResponseFactory;
import com.chouchong.service.iwant.GiftPreDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yy
 * @date 2018/7/11
 **/

@RestController
@RequestMapping("manage/giftPre")
public class GiftPreDictController {
    @Autowired
    private GiftPreDictService giftPreDictService;

    /**
     * 获得礼物偏好列表
     *
     * @param: [page 分页信息, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("list")
    public Response getGiftPreList(PageQuery page, String name) {
        return giftPreDictService.getGiftPreList(page, name);
    }

    /**
     * 添加礼物偏好
     *
     * @param: [name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/11
     */
    @PostMapping("add")
    public Response addGiftPre(String name) {
        if (name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return giftPreDictService.addGiftPre(name);
    }

    /**
     * 修改礼物偏好
     *
     * @param: [id 礼物偏好id, name 礼物偏好名称]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("update")
    public Response updateGiftPre(Integer id, String name) {
        if (id == null || name == null) {
            return ResponseFactory.errMissingParameter();
        }
        return giftPreDictService.updateGiftPre(id, name);
    }

    /**
     * 删除礼物偏好
     *
     * @param: [id 礼物偏好id]
     * @return: com.chouchong.common.Response
     * @author: yy
     * @Date: 2018/7/20
     */
    @PostMapping("del")
    public Response delGiftPre(Integer id) {
        if (id == null) {
            return ResponseFactory.errMissingParameter();
        }
        return giftPreDictService.delGiftPre(id);
    }
}
