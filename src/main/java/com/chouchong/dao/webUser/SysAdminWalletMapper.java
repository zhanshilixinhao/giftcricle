package com.chouchong.dao.webUser;

import com.chouchong.entity.webUser.SysAdminWallet;

public interface SysAdminWalletMapper {
    int insert(SysAdminWallet record);

    int insertSelective(SysAdminWallet record);

    int updateByPrimaryKeySelective(SysAdminWallet record);

    SysAdminWallet selectByAdminId(Integer adminId);
}
