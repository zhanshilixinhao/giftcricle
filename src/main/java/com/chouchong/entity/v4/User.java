package com.chouchong.entity.v4;

import com.chouchong.entity.iwant.appUser.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Table;

/**
 * @Description:
 * @Author Lxh
 * @Date 2020/9/24 15:54
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class User extends AppUser {
    private Byte isHide;
}
