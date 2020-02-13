/*==============================================================*/
/* Table: sys_admin                                               */
/*==============================================================*/
DROP TABLE IF EXISTS `sys_admin`;
CREATE TABLE `sys_admin`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(20)  DEFAULT NULL COMMENT '登录用户名',
  `password` varchar(32)  DEFAULT NULL COMMENT '登录密码MD5加密',
  `active` tinyint(4) UNSIGNED DEFAULT NULL COMMENT '1.启用 0.禁用',
  `avatar` varchar(255)  DEFAULT NULL COMMENT '头像',
  `real_name` varchar(20)  DEFAULT NULL COMMENT '用户真实姓名',
  `phone` varchar(15)  DEFAULT NULL COMMENT '用户手机号',
  `id_number` varchar(20)  DEFAULT NULL COMMENT '身份证号',
  `gender` tinyint(4) UNSIGNED DEFAULT NULL COMMENT '1.男 2.女',
  `email` varchar(255)  DEFAULT NULL COMMENT '电子邮箱地址',
  `qq` varchar(255)  DEFAULT NULL COMMENT 'QQ号',
  `weChat` varchar(255)  DEFAULT NULL COMMENT '微信号',
  `created` datetime(0) DEFAULT NULL COMMENT '注册时间',
  `updated` datetime(0) DEFAULT NULL COMMENT '更新时间',
  `create_admin_id` int(11) UNSIGNED DEFAULT NULL COMMENT '创建人',
  `update_admin_id` int(11) UNSIGNED DEFAULT NULL COMMENT '更新人',
  `create_ip` varchar(20)  DEFAULT NULL COMMENT '注册ip',
  `login_count` int(10) UNSIGNED DEFAULT NULL COMMENT '登录次数',
  `last_login_time` datetime(0) DEFAULT NULL COMMENT '最后一次登录时间',
  `last_login_ip` varchar(20)  DEFAULT NULL COMMENT '最后一次登录ip',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT = '后台用户表';


/*==============================================================*/
/* Table: el_coupon_use_log                                               */
/*==============================================================*/
DROP TABLE IF EXISTS `el_coupon_use_log`;
CREATE TABLE `el_coupon_use_log`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `num` bigint(20) comment '用户优惠券id',
  coupon_id int(10) comment '优惠券id',
  `user_id` int(10) comment '用户id',
  `store_id` int(10) comment '核销门店id',
  detail varchar(1000) comment '优惠券信息',
  admin_id int(10) comment '管理员id',
  quantity int(10) comment '使用熟练',
  created datetime,
  PRIMARY KEY (`id`) USING BTREE,
  key num (`num`),
  key user_id (`user_id`),
  key coupon_id (`coupon_id`),
  key store_id (`store_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COMMENT = '优惠券核销记录';
