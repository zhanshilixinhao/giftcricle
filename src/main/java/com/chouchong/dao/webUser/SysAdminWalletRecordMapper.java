package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysAdminWalletRecord;

public interface SysAdminWalletRecordMapper {
    int insert(SysAdminWalletRecord record);

    int insertSelective(SysAdminWalletRecord record);
}