package com.chouchong.entity.v3;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer merchantId;

    private String name;

    private String address;

    private String phone;

    private Date created;

    private Date updated;

    private String area;

    private String linkman;

    private Integer adminId;

   /* @Transient
    private String merchantName;*/

    private String password;
}
