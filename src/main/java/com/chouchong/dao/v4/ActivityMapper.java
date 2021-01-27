package com.chouchong.dao.v4;

import com.chouchong.entity.v3.MemberEvent;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/25 12:00
 */
public interface ActivityMapper extends Mapper<MemberEvent>, MySqlMapper<MemberEvent> {
}
