/*
package com.chouchong.giftcricle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.chouchong.common.ResponseFactory;
import com.chouchong.dao.order.ReceiveItemOrderMapper;
import com.chouchong.dao.webUser.SysMenuMapper;
import com.chouchong.entity.webUser.ReceiveItemOrder;
import com.chouchong.entity.webUser.SysMenu;
import com.chouchong.service.order.ReItemOrderService;
import com.chouchong.service.order.vo.Shipping;
import com.chouchong.service.webUser.vo.MenuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

*/
/*@RunWith(SpringRunner.class)
@SpringBootTest*//*

public class GiftcricleApplicationTests {

//	@Autowired
//	private ReceiveItemOrderMapper receiveItemOrderMapper;

	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Test
	public void contextLoads() {
		List<SysMenu> sysMenus = sysMenuMapper.selectByParentId();
		List<SysMenu> list = new ArrayList<>();
		for (SysMenu sysMenu : sysMenus) {
			SysMenu menu  = new SysMenu();
			menu.setName(sysMenu.getName());
			menu.setId(sysMenu.getId());
			list.add(menu);
		}
		System.out.println(JSON.toJSONString(list));
	}

//	@Test
//	public void text(){
//		ReceiveItemOrder order = receiveItemOrderMapper.selectByPrimaryKey(1);
//
//		//将字符串转化为对象
//		Shipping receive = JSON.parseObject(order.getReceiveInfo(), new TypeReference<Shipping>() {
//		});
//
//		System.out.println(receive);
//	}
}
*/
