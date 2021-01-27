package com.chouchong.dao.v4;

import com.chouchong.entity.v3.ElectronicCoupons;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.nio.MappedByteBuffer;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/30 14:20
 */
public interface CoMapper extends Mapper<ElectronicCoupons>, MySqlMapper<ElectronicCoupons> {
}
