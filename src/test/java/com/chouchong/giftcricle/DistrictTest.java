package com.chouchong.giftcricle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chouchong.GiftcricleApplication;
import com.chouchong.dao.gift.item.ItemSkuMapper;
import com.chouchong.dao.iwant.withdraw.DistrictMapper;
import com.chouchong.dao.webUser.SysFunctionPermissionMapper;
import com.chouchong.entity.SkuListVo;
import com.chouchong.entity.SkuValueVo;
import com.chouchong.entity.gift.item.ItemSku;
import com.chouchong.entity.iwant.withdraw.District;
import com.chouchong.entity.webUser.SysFunctionPermission;
import com.chouchong.service.amap.AMapApiImpl;
import com.chouchong.service.amap.DistrictResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GiftcricleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
public class DistrictTest {

    @Autowired
    private SysFunctionPermissionMapper sysFunctionPermissionMapper;

    @Autowired
    private DistrictMapper districtMapper;

    @Autowired
    private ItemSkuMapper itemSkuMapper;


    @Test
    public void amapTest() throws IOException {
        DistrictResponse response = AMapApiImpl.createWebApi().getDistricts("100000", 3);
        insert(response.getDistricts().get(0));
    }

    private void insert(DistrictResponse.District district) {
        if (district != null && !CollectionUtils.isEmpty(district.getDistricts())) {
            for (DistrictResponse.District item : district.getDistricts()) {
                District ob = new District();
                ob.setpAdcode(district.getAdcode());
                ob.setAdcode(item.getAdcode());
                ob.setName(item.getName());
                ob.setLevel(item.getLevel());
                System.out.println(JSON.toJSONString(item));
                districtMapper.insert(ob);
                insert(item);
            }
        }
    }

    /**
     * 建立那啥
     */
    @Test
    public void sku() {
        List<ItemSku> list = itemSkuMapper.selectAll();
        for (ItemSku itemSku : list) {
            SkuListVo skuListVo = itemSkuMapper.selectDetailBySkuId(itemSku.getId());
            String linqin = genTitle(skuListVo);
            itemSkuMapper.updateBatch(itemSku.getId(), linqin);
        }
    }

    private String genTitle(SkuListVo skuListVo) {
        if (skuListVo == null) return "";
        StringBuilder sb = new StringBuilder();
        for (SkuValueVo value : skuListVo.getValues()) {
            sb.append(" ").append(value.getValue());
        }
        return sb.toString();
    }

//    @Test
//    public void tt(){
//        String json = "[\n" +
//                "  {\n" +
//                "    \"id\": 101,\n" +
//                "    \"name\": \"角色管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取后台角色列表\",\n" +
//                "        \"url\": \"manage/permission/roles\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变角色状态\",\n" +
//                "        \"url\": \"manage/permission/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除角色\",\n" +
//                "        \"url\": \"manage/permission/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 102,\n" +
//                "    \"name\": \"添加角色\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取后台菜单列表\",\n" +
//                "        \"url\": \"manage/permission/menus\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加后台角色\",\n" +
//                "        \"url\": \"manage/permission/addRole\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 103,\n" +
//                "    \"name\": \"修改角色\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得角色详情\",\n" +
//                "        \"url\": \"manage/permission/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取后台菜单列表\",\n" +
//                "        \"url\": \"manage/permission/menus\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改角色\",\n" +
//                "        \"url\": \"manage/permission/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 104,\n" +
//                "    \"name\": \"后台用户管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得全部的角色\",\n" +
//                "        \"url\": \"manage/permission/allRole\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取后台用户列表\",\n" +
//                "        \"url\": \"manage/user/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变用户状态\",\n" +
//                "        \"url\": \"manage/user/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除后台用户\",\n" +
//                "        \"url\": \"manage/user/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 105,\n" +
//                "    \"name\": \"后台用户添加\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得全部的角色\",\n" +
//                "        \"url\": \"manage/permission/allRole\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加后台用户\",\n" +
//                "        \"url\": \"manage/user/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 106,\n" +
//                "    \"name\": \"后台用户修改\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得全部的角色\",\n" +
//                "        \"url\": \"manage/permission/allRole\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取后台用户详情\",\n" +
//                "        \"url\": \"manage/user/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改后台用户\",\n" +
//                "        \"url\": \"manage/user/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改密码\",\n" +
//                "        \"url\": \"manage/user/updatePass\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 201,\n" +
//                "    \"name\": \"APP用户管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"登录用户信息\",\n" +
//                "        \"url\": \"manage/user/info\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取app用户列表\",\n" +
//                "        \"url\": \"manage/appUser/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变用户状态\",\n" +
//                "        \"url\": \"manage/appUser/status\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 202,\n" +
//                "    \"name\": \"印象标签管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"登录用户信息\",\n" +
//                "        \"url\": \"manage/user/info\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取app用户列表\",\n" +
//                "        \"url\": \"manage/tagDict/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加印象标签\",\n" +
//                "        \"url\": \"manage/tagDict/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变印象标签状态\",\n" +
//                "        \"url\": \"manage/tagDict/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除印象标签\",\n" +
//                "        \"url\": \"manage/tagDict/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改印象标签\",\n" +
//                "        \"url\": \"manage/tagDict/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 203,\n" +
//                "    \"name\": \"礼物偏好管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取礼物偏好列表\",\n" +
//                "        \"url\": \"manage/giftPre/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改礼物偏好\",\n" +
//                "        \"url\": \"manage/giftPre/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除礼物偏好\",\n" +
//                "        \"url\": \"manage/giftPre/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加礼物偏好\",\n" +
//                "        \"url\": \"manage/giftPre/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 204,\n" +
//                "    \"name\": \"送礼事件管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取送礼事件管理\",\n" +
//                "        \"url\": \"manage/event/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加送礼事件\",\n" +
//                "        \"url\": \"manage/event/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改送礼事件\",\n" +
//                "        \"url\": \"manage/event/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除送礼事件\",\n" +
//                "        \"url\": \"manage/event/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 301,\n" +
//                "    \"name\": \"首页横幅管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取首页横幅列表\",\n" +
//                "        \"url\": \"manage/banner/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除首页横幅\",\n" +
//                "        \"url\": \"manage/banner/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变首页横幅状态\",\n" +
//                "        \"url\": \"manage/banner/status\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 302,\n" +
//                "    \"name\": \"添加横幅\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"添加横幅\",\n" +
//                "        \"url\": \"manage/banner/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 303,\n" +
//                "    \"name\": \"横幅修改\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"修改横幅\",\n" +
//                "        \"url\": \"manage/banner/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"横幅详情\",\n" +
//                "        \"url\": \"manage/banner/detail\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 401,\n" +
//                "    \"name\": \"优惠券管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取优惠券列表\",\n" +
//                "        \"url\": \"manage/coupon/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加优惠券\",\n" +
//                "        \"url\": \"manage/coupon/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取app用户列表通过电话号码\",\n" +
//                "        \"url\": \"manage/appUser/phone\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"赠送优惠券\",\n" +
//                "        \"url\": \"manage/coupon/give\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变优惠券状态\",\n" +
//                "        \"url\": \"manage/coupon/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改优惠券\",\n" +
//                "        \"url\": \"manage/coupon/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除优惠券\",\n" +
//                "        \"url\": \"manage/coupon/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"使用优惠券\",\n" +
//                "        \"url\": \"manage/coupon/use\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 501,\n" +
//                "    \"name\": \"分类管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取商品分类列表\",\n" +
//                "        \"url\": \"manage/itemCate/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变商品分类状态\",\n" +
//                "        \"url\": \"manage/itemCate/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 502,\n" +
//                "    \"name\": \"品牌管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取品牌列表\",\n" +
//                "        \"url\": \"manage/brand/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加品牌\",\n" +
//                "        \"url\": \"manage/brand/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除品牌\",\n" +
//                "        \"url\": \"manage/brand/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改品牌\",\n" +
//                "        \"url\": \"manage/brand/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 503,\n" +
//                "    \"name\": \"商品管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的品牌\",\n" +
//                "        \"url\": \"manage/brand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获取商品列表\",\n" +
//                "        \"url\": \"manage/item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变商品状态\",\n" +
//                "        \"url\": \"manage/item/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"是否设为热门商品\",\n" +
//                "        \"url\": \"manage/item/hot\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"是否设为精品商品\",\n" +
//                "        \"url\": \"manage/item/choiceness\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除商品\",\n" +
//                "        \"url\": \"manage/item/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 504,\n" +
//                "    \"name\": \"商品添加\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的品牌\",\n" +
//                "        \"url\": \"manage/brand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加商品\",\n" +
//                "        \"url\": \"manage/item/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 505,\n" +
//                "    \"name\": \"商品修改\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的商品分类\",\n" +
//                "        \"url\": \"manage/itemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的品牌\",\n" +
//                "        \"url\": \"manage/brand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商品详情\",\n" +
//                "        \"url\": \"manage/item/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商品修改\",\n" +
//                "        \"url\": \"manage/item/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 506,\n" +
//                "    \"name\": \"属性管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取商品属性列表\",\n" +
//                "        \"url\": \"manage/itemFeature/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加商品属性\",\n" +
//                "        \"url\": \"manage/itemFeature/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改商品属性\",\n" +
//                "        \"url\": \"manage/itemFeature/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除商品属性\",\n" +
//                "        \"url\": \"manage/itemFeature/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 507,\n" +
//                "    \"name\": \"组合商品\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"查询sku组合的数量\",\n" +
//                "        \"url\": \"manage/itemSku/isGroup\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的商品属性\",\n" +
//                "        \"url\": \"manage/itemFeature/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加sku\",\n" +
//                "        \"url\": \"manage/itemSku/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 508,\n" +
//                "    \"name\": \"组合商品列表\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"改变sku状态\",\n" +
//                "        \"url\": \"manage/itemSku/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的商品属性\",\n" +
//                "        \"url\": \"manage/itemFeature/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"sku列表\",\n" +
//                "        \"url\": \"manage/itemSku/list\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 509,\n" +
//                "    \"name\": \"组合商品编辑\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"sku详情\",\n" +
//                "        \"url\": \"manage/itemSku/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改sku\",\n" +
//                "        \"url\": \"manage/itemSku/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 510,\n" +
//                "    \"name\": \"商品属性值管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得商品的所有商品属性值\",\n" +
//                "        \"url\": \"manage/itemSku/value\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改sku属性值\",\n" +
//                "        \"url\": \"manage/itemSku/editValue\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 601,\n" +
//                "    \"name\": \"分类管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取虚拟商品分类列表\",\n" +
//                "        \"url\": \"manage/virItemCate/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变虚拟商品分类状态\",\n" +
//                "        \"url\": \"manage/virItemCate/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 602,\n" +
//                "    \"name\": \"品牌管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取虚拟商品品牌列表\",\n" +
//                "        \"url\": \"manage/virItemBrand/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 603,\n" +
//                "    \"name\": \"商品管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品列表\",\n" +
//                "        \"url\": \"manage/virItem/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变虚拟商品状态\",\n" +
//                "        \"url\": \"manage/virItem/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除虚拟商品\",\n" +
//                "        \"url\": \"manage/virItem/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 604,\n" +
//                "    \"name\": \"添加虚拟商品\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加虚拟商品\",\n" +
//                "        \"url\": \"manage/virItem/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 605,\n" +
//                "    \"name\": \"编辑虚拟商品\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品分类\",\n" +
//                "        \"url\": \"manage/virItemCate/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"获得所有的虚拟商品品牌\",\n" +
//                "        \"url\": \"manage/virItemBrand/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"虚拟商品详情\",\n" +
//                "        \"url\": \"manage/virItem/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改虚拟商品\",\n" +
//                "        \"url\": \"manage/virItem/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 701,\n" +
//                "    \"name\": \"主题管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"主题列表\",\n" +
//                "        \"url\": \"manage/theme/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除主题\",\n" +
//                "        \"url\": \"manage/theme/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改主题\",\n" +
//                "        \"url\": \"manage/theme/update\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加主题\",\n" +
//                "        \"url\": \"manage/theme/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变主题状态\",\n" +
//                "        \"url\": \"manage/theme/status\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 702,\n" +
//                "    \"name\": \"商品管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"获取所有主题\",\n" +
//                "        \"url\": \"manage/theme/all\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"主题商品列表\",\n" +
//                "        \"url\": \"manage/themeItem/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"改变主题商品状态\",\n" +
//                "        \"url\": \"manage/themeItem/status\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"设置排序值\",\n" +
//                "        \"url\": \"manage/themeItem/sort\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商品列表\",\n" +
//                "        \"url\": \"manage/item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加主题商品\",\n" +
//                "        \"url\": \"manage/themeItem/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除主题商品\",\n" +
//                "        \"url\": \"manage/themeItem/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 801,\n" +
//                "    \"name\": \"文章管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"文章列表\",\n" +
//                "        \"url\": \"manage/article/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"文章删除\",\n" +
//                "        \"url\": \"manage/article/del\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改文章状态\",\n" +
//                "        \"url\": \"manage/article/status\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 802,\n" +
//                "    \"name\": \"文章添加\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"添加文章\",\n" +
//                "        \"url\": \"manage/article/add\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 803,\n" +
//                "    \"name\": \"文章修改\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"文章详情\",\n" +
//                "        \"url\": \"manage/article/detail\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"文章详情\",\n" +
//                "        \"url\": \"manage/article/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 901,\n" +
//                "    \"name\": \"商家管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"商家列表\",\n" +
//                "        \"url\": \"manage/merchant/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商家审核\",\n" +
//                "        \"url\": \"manage/merchant/pass\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商家审核失败\",\n" +
//                "        \"url\": \"manage/merchant/fail\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1001,\n" +
//                "    \"name\": \"系统通知\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"系统消息列表\",\n" +
//                "        \"url\": \"manage/message/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"添加系统消息\",\n" +
//                "        \"url\": \"manage/message/add\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除系统消息\",\n" +
//                "        \"url\": \"manage/message/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1002,\n" +
//                "    \"name\": \"意见反馈\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"意见列表\",\n" +
//                "        \"url\": \"manage/suggestion/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除意见反馈\",\n" +
//                "        \"url\": \"manage/suggestion/del\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1101,\n" +
//                "    \"name\": \"提现管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"提现列表\",\n" +
//                "        \"url\": \"manage/withdraw/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"提现处理\",\n" +
//                "        \"url\": \"manage/withdraw/handle\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"银行列表\",\n" +
//                "        \"url\": \"manage/withdraw/bank\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"开始处理\",\n" +
//                "        \"url\": \"manage/withdraw/status\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1201,\n" +
//                "    \"name\": \"充值订单\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"充值订单列表\",\n" +
//                "        \"url\": \"manage/order/charge/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"充值订单删除\",\n" +
//                "        \"url\": \"manage/order/charge/delete\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1202,\n" +
//                "    \"name\": \"商品购买订单\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"商品订单列表\",\n" +
//                "        \"url\": \"manage/order/item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商品订单删除\",\n" +
//                "        \"url\": \"manage/order/item/delete\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"商品详情\",\n" +
//                "        \"url\": \"manage/order/item/detail\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1203,\n" +
//                "    \"name\": \"虚拟商品订单\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"虚拟订单列表\",\n" +
//                "        \"url\": \"manage/order/virtual_item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"虚拟订单删除\",\n" +
//                "        \"url\": \"manage/order/virtual_item/delete\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1204,\n" +
//                "    \"name\": \"寄售台订单\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"寄售台订单劣币哦\",\n" +
//                "        \"url\": \"manage/order/con_item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"寄售台顶底删除\",\n" +
//                "        \"url\": \"manage/order/con_item/delete\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1205,\n" +
//                "    \"name\": \"商品提货订单\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"提货订单泪飙\",\n" +
//                "        \"url\": \"manage/order/re_item/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"删除提货订单\",\n" +
//                "        \"url\": \"manage/order/re_item/delete\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"收货信息\",\n" +
//                "        \"url\": \"manage/order/re_item/receive\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"物流信息\",\n" +
//                "        \"url\": \"manage/order/re_item/logistics\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"发货\",\n" +
//                "        \"url\": \"manage/order/re_item/shipments\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"修改物流信息\",\n" +
//                "        \"url\": \"manage/order/re_item/update\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1301,\n" +
//                "    \"name\": \"折现管理\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"折现列表\",\n" +
//                "        \"url\": \"manage/item/discount/list\"\n" +
//                "      },\n" +
//                "      {\n" +
//                "        \"name\": \"折现删除\",\n" +
//                "        \"url\": \"manage/item/discount/delete\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1401,\n" +
//                "    \"name\": \"订单统计\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"订单统计列表\",\n" +
//                "        \"url\": \"/manage/statistics/order\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  },\n" +
//                "  {\n" +
//                "    \"id\": 1402,\n" +
//                "    \"name\": \"用户统计\",\n" +
//                "    \"urls\": [\n" +
//                "      {\n" +
//                "        \"name\": \"用户统计\",\n" +
//                "        \"url\": \"/manage/statistics/user\"\n" +
//                "      }\n" +
//                "    ]\n" +
//                "  }\n" +
//                "]\n";
//        JSONArray object = JSON.parseArray(json);
//        for (Object ob : object) {
//            JSONObject db = (JSONObject) ob;
//            JSONArray urls = db.getJSONArray("urls");
//            for (Object uri : urls) {
//                JSONObject da = (JSONObject) uri;
//                SysFunctionPermission permission = new SysFunctionPermission();
//                permission.setName(da.getString("name"));
//                permission.setDesciption(da.getString("name"));
//                permission.setUrl(da.getString("url"));
//                permission.setMenuId(db.getInteger("id"));
//                permission.setActive((byte)1);
//                permission.setCreateAdminId(1);
//                permission.setUpdated(new Date());
//                permission.setCreated(new Date());
//                permission.setUpdateAdminId(1);
//                sysFunctionPermissionMapper.insert(permission);
//            }
//        }
//    }


}
