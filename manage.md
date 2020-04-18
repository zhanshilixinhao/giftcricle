# **礼遇圈管理端接口文档**

## 更新记录

### v0.0.1

* 更新人：linqin
* 2020/2/3
* 更新内容

>  1. 修改消费回退接口，修改参数   （3.7接口）
>  2. 增加充值退款接口 （ 3.9接口）
>  3. 修改充值接口（3.5接口）
>  4. 扫描功能6
>  5. 增加3.10 获取活动详情（充值时查看优惠券可以用到）
>  6. 优惠券功能7



## 目录
<span id="m"> </span>

[TOC]


## 1 接口说明（不需要签名）
### 1.1 接口访问地址
https://liyuquan.cn/web/


### 1.2 接口响应 [↑](#m)

```javascript
{
    errCode: 0,
    msg: "错误信息",
    data: { },
    time:1589437847788
}
```

| 字段名称        | 字段类型       | 是否必传 | 描述  |
| ------------- |:--------:|:---------:|:--------:|
| errCode | Int | 是 | 错误码 |
| msg | String | 否 | 错误信息 |
| data | Object | 否 | 成功返回数据集 |
| time | Long | 是 | 接口返回时服务器的时间戳 |

### 1.3 接口错误码表

| 错误码        | 错误描述       |
| ------------- |:-------------:|
| 0             | 请求操作成功 |
| 1             | 请求操作失败 |
| 4             | 缺少参数 |
| 5 | 签名错误 |
| 1002 | 需要登录 |
| 1003 | 权限拒绝 |
| 1004 | 第三方登录时需要绑定手机号不绑定的话不能登录 |
| 1005 | 用户不存在 |
| 1006 | 短信验证码错误 |
| 1007 | 手机号已注册 |
| 1008 | appId 不存在 |
| 1009 | 无效的refresh_token |
| 1012 | 用户已加入其它未开始活动的战队 |

http 常用错误码

| 错误码        | 错误描述       |
| ------------- |:-------------:|
| 400 | 错误请求  服务器不理解请求的语法 |
| 401 | 未授权 需填写正确的access_token |
| 404 | （未找到） 服务器找不到请求的网页 |
| 405 | （方法禁用） 禁用请求中指定的方法 |
| 500 | 服务器内部错误，具体查看msg输出的内容 |



## 2.用户信息

### 2.1 登录

- 请求地址：manage/user/login
- 服务协议：HTTP/POST
- 是否需要身份认证：否
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|   username   |  string  |    是    |   无   | 用户名 |
|   password   |  string  |    是    |   无   | 密码（MD5加密） |
| client|int|是|无|1|

- 请求结果示例

```js
{
	errCode: 0,
	result: 0,
	time: 1576822926326,
	data: "7171d718-0c30-4fd0-9a6b-53a865be71a5"  //token
}
```

### 2.2 管理端用户信息

- 请求地址：manage/user/info
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 |    参数说明     |
| :------: | :------: | :------: | :----: | :-------------: |
|  token   |  String  |    是    |   无   |      访问令牌       |


- 请求结果示例

```js
// 登录成功返回
{
  "errCode": 0,
  "result": 0,
  "time": 1576826467787,
  "data": {
    "sysAdmin": {
      "id": 2,
      "username": "test",
      "password": "149ABFAC83C72B88C1538833655102F3",
      "active": 1,
      "avatar": "/item/190411/bb4d7cbb-7658-4088-87dd-ac0add78af63.jpg",
      "realName": "yy",
      "phone": "13925568955",
      "idNumber": "123213afssfd",
      "gender": 3,
      "email": "123456",
      "qq": "1234567",
      "wechat": "123456",
      "created": "2017-11-15T06:05:50.000+0000",
      "updated": "2019-04-11T03:06:34.000+0000",
      "createAdminId": 1,
      "updateAdminId": 2,
      "createIp": "",
      "loginCount": 850,
      "lastLoginTime": "2018-06-21T07:37:25.000+0000",
      "lastLoginIp": "106.58.234.219"
    },
    "menus": [
      "permission",
      "appUser"
    ],
    "permissions": [
      "manage/permission/allRole:1129",
      "manage/user/list"
    ],
    "permissionList": null,
    "roleId": 2
  }
}
```

## 3 基本操作
### 3.1 门店信息

- 请求地址：manage/v3/store/list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576828189764,
  "data": [
    {
      "id": 3, //门店id
      "merchantId": 12, //商家id
      "name": "外婆味道南屏店", // 门店名称
      "address": "南屏街",// 门店详细地址
      "phone": "123456666666", // 门店电话
      "area": "云南,昆明市,五华区", // 门店地址
      "linkman": "小明", //门店联系人
      "adminId": 21, 
      "merchantName": "外婆味道", //商家名称
      "password": null
      "created": "2019-12-13T03:07:27.000+0000",
      "updated": "2019-12-13T09:57:32.000+0000",
    }
  ],
  "total": 1,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}
```

### 3.2 会员卡列表（开卡）

- 请求地址：manage/v3/card/store_card
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|type|int|是|1|type=1不分页（这里不用分页）|

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576829699128,
  "data": [
    {
      "id": 4, // 会员卡id(后面接口需要用到字段)
      "cardNo": null,
      "title": "外婆味道活动卡", // 会员卡标题(后面接口需要用到字段)
      "colour": "#E43B19", // 会员卡颜色
      "logo": "/item/191213/  747d5de2-5415-4c70-812f-e1a87ff27184.jpeg",
      "storeIds": "3,4",
      "eventIds": null,
      "adminId": 19,
      "type": 11, // 1 礼遇圈卡 10 商家储值卡 11 商家活动卡
      "created": "2019-12-13T06:34:46.000+0000",
      "summary": "<p>活动卡活动卡</p>",
      "detail": null,
      "storeVos": [
        {
          "id": 3,
          "name": "外婆味道南屏店",
          "address": "南屏街",
          "area": "云南,昆明市,五华区",
          "phone": "123456666666",
          "linkman": "小明"
        }
      ],
      "eventVos": [
        {
          "id": 9,
          "title": "充200 送200 返200",
          "summary": "充200 送200 返200",
          "rechargeMoney": 200.00,
          "sendMoney": 200.00,
          "targetId": null,
          "adminId": 19,
          "type": 1,
          "status": 10,
          "created": "2019-12-13T06:34:24.000+0000",
          "targetName": null
        }
      ]
    }
  ]
}
```



### 3.3 开卡

- 请求地址：manage/v3/userCard/add
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|是|无|用户电话|
|membershipCardId|int|是|无|会员卡id（3.2中id字段）|
|password|string|否|号码后6位|密码（MD5加密）|

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "开卡成功",
  "time": 1576829251319
}
```




### 3.4 活动列表

- 请求地址：manage/v3/cardEvent/all_event
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|cardId|int|是|无|会员卡id会员卡id（3.2中id字段）|

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576830334236,
  "data": [
    {
      "id": 9, // 活动id
      "title": "充200 送200 返200", //活动标题
      "summary": "充200 送200 返200", //活动简介
      "rechargeMoney": 200.00, //充值金额
      "sendMoney": 200.00,//赠送金额
      "targetId": null, // type等于2或者5时，优惠券id
      "adminId": 19, 
      "type": 1, // 1 充钱送钱 2 充钱送优惠券 5 充钱送钱+ 送优惠券
      "status": 10, // 1 正常 10 活动卡才可以使用
      "created": "2019-12-13T06:34:24.000+0000",
      "targetName": null
    }
  ]
}

```


### 3.5 会员充值

- 请求地址：manage/v3/userCard/charge
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|是|无|用户电话|
|cardId|int|是|无|会员卡id（3.2中id字段）|
|recharge|BigDecimal|是|无|充值金额|
|send|BigDecimal|是|无|赠送金额|
|eventId|int|否|无|活动id（3.4id字段,活动卡充值时该字段必传）|
|image|String|是|无|图片|

- 注：如果选了活动，充值金额与赠送金额必须与活动里的充值金额赠送金额相等

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "充值成功",
  "time": 1576829251319
}
```


### 3.6 会员消费

- 请求地址：manage/v3/userCard/expense
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|是|无|用户电话|
|cardId|int|是|无|会员卡id（3.2中id字段）|
|expense|BigDecimal|是|无|消费金额|
|password|string|是|无|密码（MD5加密）|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "成功",
  "time": 1576829251319
}
```

### 3.7 消费回退

- 请求地址：manage/v3/turnover/refund
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|orderNo|long|是|无|消费订单号（消费订单列表是接口4.2）|
|code|string|是|无|短信验证码|



- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "成功",
  "time": 1576829251319
}
```

### 3.8 发送短信

- 请求地址：manage/ask/code
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "成功",
  "time": 1576829251319
}
```

### 3.9 充值退款

- 请求地址：manage/v3/turnover/refund_charge
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|orderNo|long|是|无|充值订单号（充值订单列表是接口4.1）|
|code|string|是|无|短信验证码|



- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "成功",
  "time": 1576829251319
}
```

### 3.10 获取活动详情（充值时查看优惠券可以用到）

- 请求地址：manage/v3/cardEvent/detail
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|eventId|int|是|无|活动id|

- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1581648311812,
  "data": {
    "id": 43, //活动id
    "title": "充100 送2 张50元优惠券", //标题
    "couponTitle": "啤酒券" // 优惠券标题
    "summary": "充100 送2 张50元优惠券",
    "rechargeMoney": 100.00,
    "sendMoney": 0.00,
    "targetId": 2,
    "adminId": 19,
    "type": 2,
    "status": 1,
    "updated": "2020-02-13 13:38:53",
    "created": "2020-02-13 13:38:53",
    "scale": null,
    "quantity": 2 //数量
  }
}

```

## 4 查询
### 4.1 充值记录

- 请求地址：manage/v3/turnover/record_list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|否|无|用户电话（模糊查询）|
|keywords|string|否|无|关键字（可查询号码和昵称）|
|startTime|Long|否|无|开始时间（13位时间戳）|
|endTime|Long|否|无|结束时间（13位时间戳）|
|pageNum|int|是|1|分页|
|pageSize|int|是|14|分页大小|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576832481514,
  "data": {
    "totalChargeMoney": 800.00, //总充值金额
    "totalSendMoney": 800.00, //总赠送金额
    "chargeReVo": [  //充值记录
      {
        "id": 50, //充值记录id
        "membershipCardId": 4, //会员卡id
        "title": "外婆味道活动卡", //会员卡标题
        "cardNo": 7919121420122, //会员卡卡号
        "userId": 7, //用户id
        "nickname": "哈哈", // 用户昵称
        "phone": "15752400657", //用户号码
        "memberEventId": 1, //活动id
        "eventName": "小二老坛子酸菜鱼开业活动卡", //活动名称
        "rechargeMoney": 200.00, //充值金额
        "sendMoney": 200.00, //赠送金额
        "type": 2, //1 app充值 2 门店充值
        "storeId": 3, //充值门店id
        "storeName": "外婆味道南屏店",//充值门店名称
        "explain": "余额充值", //充值说明
        "created": "2019-12-20T08:43:19.000+0000",//充值时间
        "orderNo": 71019122016110 //订单号
        "image": "https://liyuquan.cn/static/avatar" //充值图片
      },
      {
        "id": 49,
        "membershipCardId": 4,
        "title": "外婆味道活动卡",
        "cardNo": 7919121420122,
        "userId": 7,
        "nickname": "哈哈",
        "phone": "15752400657",
        "memberEventId": null,
        "eventName": null,
        "rechargeMoney": 200.00,
        "sendMoney": 200.00,
        "type": 2,
        "storeId": 3,
        "storeName": "外婆味道南屏店",
        "explain": "余额充值",
        "created": "2019-12-20T08:42:46.000+0000",
        "orderNo": 71019122016109
         "image": "https://liyuquan.cn/static/avatar" //充值图片
      }
    ]
  },
  "total": 4,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}
```

### 4.2 消费记录

- 请求地址：manage/v3/turnover/expense_list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|否|无|用户电话（模糊查询）|
|startTime|Long|否|无|开始时间（13位时间戳）|
|endTime|Long|否|无|结束时间（13位时间戳）|
|pageNum|int|是|1|分页|
|pageSize|int|是|14|分页大小|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576833628238,
  "data": {
    "totalExpenseMoney": 222.00, //总消费金额
    "expenseReVo": [ //消费记录
      {
        "id": 32, //消费记录id
        "membershipCardId": 4, // 会员卡id
        "title": "外婆味道活动卡", // 会员卡标题
        "cardNo": 7919121420122, //会员卡卡号
        "userId": 7, //用户id
        "nickname": "哈哈", //用户昵称
        "phone": "15752400657",//用户电话
        "expenseMoney": 200.00, //消费金额
        "type": 2, // 1app 消费 ，2 门店消费
        "storeId": 3, //消费门店id
        "storeName": "外婆味道南屏店", //消费门店名称
        "explain": "线下消费", //消费说明
        "created": "2019-12-20T08:54:10.000+0000",// 消费时间
        "orderNo": 71119122016113 //订单号
      }
    ]
  },
  "total": 3,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}

```


### 4.3 反结账（退款）记录

- 请求地址：manage/v3/turnover/refund_list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|否|无|用户电话（模糊查询）|
|startTime|Long|否|无|开始时间（13位时间戳）|
|endTime|Long|否|无|结束时间（13位时间戳）|
|pageNum|int|是|1|分页|
|pageSize|int|是|14|分页大小|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1576834068884,
  "data": [
    {
      "id": 4, //退款记录id
      "membershipCardId": 4, //会员卡id
      "title": "外婆味道活动卡", //会员卡标题
      "cardNo": 7919121420122, //会员卡卡号
      "userId": 7, //用户id
      "nickname": "哈哈", //用户昵称
      "phone": "15752400657", //用户电话
      "money": 200.00, //退款金额
      "storeName": "外婆味道南屏店", //退款门店名称
      "explain": "发",//退款说明
      "created": "2019-12-20T09:27:33.000+0000" //退款时间
    }
  ],
  "total": 1,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}

```


### 4.4 用户查询

- 请求地址：manage/v3/userCard/user_card
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|否|无|用户电话（模糊查询）|
|pageNum|int|是|1|分页|
|pageSize|int|是|14|分页大小|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1577092118432,
  "data": [
    {
      "userId": 2, //用户id
      "phone": "15368224942", // 用户电话
      "nickname": "淡水鱼", //用户昵称
      "avatar": "https://liyuquan.cn/staticorder/comment/20190729/1564386854932905-550-550.jpg" //用户头像
    },
    {
      "userId": 5,
      "phone": "18988074122",
      "nickname": "年锐",
      "avatar": "https://liyuquan.cn/staticorder/comment/20190731/1564561043231711-400-400.jpg"
    }
  ],
  "total": 8,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}

```

### 4.5 会员详情

- 请求地址：manage/v3/userCard/user_card_detail
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|userId|int|是|无|会员用户id(表4.4userId)|



- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1577093334533,
  "data": [
    {
      "userId": 7, //会员用户id
      "phone": "15752400657", // 用户电话
      "nickname": "哈哈", //用户昵称
      "avatar": "https://liyuquan.cn/staticorder/comment/20190923/1569232138231132-600-600.jpg", // 用户头像
      "cardId": 3, //会员卡id
      "title": "外婆味道储值卡", // 会员卡标题
      "cardNo": 7919121421128,//会员卡卡号
      "balance": 90.00, //会员卡余额
      "type": 10, // 1 礼遇圈卡 10 商家普通储值卡 11 商家活动卡
      "capital": null,
      "send": null,
      "created": "2019-12-14T13:04:41.000+0000", //开卡时间
      "eventCards": null
    },
    {
      "userId": 7,
      "phone": "15752400657",
      "nickname": "哈哈",
      "avatar": "https://liyuquan.cn/staticorder/comment/20190923/1569232138231132-600-600.jpg",
      "cardId": 4,
      "title": "外婆味道活动卡",
      "cardNo": 7919121420122,
      "balance": 3878.00,
      "type": 11,
      "capital": 1582.4, //剩余本金
      "send": 1895.6, //剩余赠送
      "created": "2019-12-14T12:57:08.000+0000", 
      "eventCards": [ //活动卡使用详情（type =11 活动卡才有）
        {
          "storeMemberEventId":10 , // 会员卡使用记录详情
          "capitalBalance": 0.00, //本金余额
          "sendBalance": 95.60, //赠送余额
          "status": 2 // 1,2 未完成 3 需返现 5 已返现
        },
        {
          "storeMemberEventId":11 ,
          "capitalBalance": 0.00,
          "sendBalance": 200.00,
          "status": 1
        }
      ]
    }
  ]
}


```



### 4.6 商家活动卡返现操作

- 请求地址：manage/v3/userCard/card_status
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|storeMemberEventId|int|是|无| 会员卡使用记录详情（4.5接口storeMemberEventId字段）|


- 请求结果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "",
  "time": 1577094365079
}
```

## 5 图片上传

### 5.1 图片上传

- 请求地址：manage/upload/image
- 服务协议：HTTP/POST
- 是否需要身份认证：否
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
| module | int | 是 | 无 | 模块（目前传1） |


## 6 二維碼掃描

### 6.1 二維碼掃描

- 请求地址：manage/tool/scan
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
| qrcode | string | 是 | 無 | 二維碼内容 |

- 請求結果示例

```js
// 掃描優惠券
{
	"errCode": 0,
	"result": 0,
	"time": 1581598043440,
	"data": {
		"num": 1, //优惠券编码
		"qrcodeType": 2, // 二維碼類型 1 會員卡 2 優惠券
		"couponId": 1, //优惠券id
		"userId": 7, //用户id
		"quantity": 4, //总数量
		"code": "", 
		"created": "2020-02-10 14:16:51", 
		"title": "20 元优惠券", //标题
		"summary": "使用说明，简介",
		"logo": "/item/200212/a1f46021-dcd2-44d0-802a-66aad94bbd23.png",
		"storeIds": "5,4,3",
		"date": "2020-02-29 00:00:00", //有效期
		"storeName": "外婆味道大观店/外婆味道吾悦店/外婆味道南屏店/", //适用店铺
		"status": null
	}
}

// 掃描會員卡
{
  "errCode": 0,
  "result": 0,
  "time": 1581652087162,
  "data": {
    "id": 8, //用户会员卡关联表Id
    "membershipCardId": 3, //会员卡id
    "userId": 7, //用户id 
    "qrcodeType": 1,// 二維碼類型 1 會員卡 2 優惠券
    "phone": "15752400657", //绑定号码
    "balance": 810.00, //余额
    "created": "2019-12-14 21:04:41", //开卡时间
    "cardNo": 7919121421128, //ID
    "title": "外婆味道储值卡", //会员卡标题
    "summary": "null3",
    "colour": "#34988E", //颜色
    "logo": "/item/191213/b711eb02-ceb0-4f52-b141-a45eb4f813dc.jpeg",
    "type": 10,
    "storeIds": "3,4",
    "grade": "黄金会员",
    "summaryGrade": "1",
    "capital": 0,
    "send": 0,
    "code": null,
    "avatar": "https://wx.qlogo.cn/order/comment/20190923/1569232138231132-600-600.jpg", //用户头像
    "nickname": "路遥\uD83D\uDE34\uD83D\uDE34"//用户昵称
  }
}

```


### 6.2 优惠券核销

- 请求地址：manage/v3/coupon/use
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
| num | Long | 是 | 无 | 优惠券编码 |

- 請求結果示例

```js
{
  "errCode": 0, // 核销成功
  "result": 0,
  "time": 1581647765999,
  "data": {
    "id": null,
    "num": 1,
    "couponId": 1,
    "userId": 7,
    "storeId": 3,
    "detail": "{\"code\":\"\",\"couponId\":1,\"created\":1581315411000,\"date\":1582905600000,\"logo\":\"/item/200212/a1f46021-dcd2-44d0-802a-66aad94bbd23.png\",\"num\":1,\"quantity\":5,\"storeIds\":\"5,4,3\",\"summary\":\"使用说明，简介\",\"title\":\"20 元优惠券\",\"userId\":7}",
    "adminId": 21,
    "quantity": 1,
    "created": null,
    "nickname": null,
    "phone": null,
    "storeName": null,
    "title": null
  }
}
```


## 7 优惠券
### 7.1 赠送优惠券

- 请求地址：manage/v3/coupon/forUser
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
| phone | String | 是 | 无 | 用户电话 |
|couponId|int|是|无|优惠券id|
|quantity|int|是|无|数量|

- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "赠送成功",
  "time": 1581648875666
}

```

### 7.2 优惠券列表（可选择的优惠券）

- 请求地址：manage/v3/coupon/xcx_list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |


- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1581649977170,
  "data": [
    {
      "id": 2, //优惠券id
      "title": "50 元优惠券", //优惠券标题
      "summary": "使用",
      "logo": null,
      "storeIds": "4,3",
      "adminId": 19,
      "type": 1,
      "status": 1,
      "date": "2020-04-30 00:00:00",
      "updated": "2020-02-12 22:46:37",
      "created": "2020-02-11 17:52:50",
      "storeVos": [
        {
          "id": 4,
          "name": "外婆味道吾悦店",
          "address": "吾悦广场",
          "area": "云南,昆明市,西山区",
          "phone": "15368224942",
          "linkman": "吴家"
        }
      ]
    },
    {
      "id": 1,
      "title": "20 元优惠券",
      "summary": "使用说明，简介",
      "logo": "/item/200212/a1f46021-dcd2-44d0-802a-66aad94bbd23.png",
      "storeIds": "5,4,3",
      "adminId": 19,
      "type": 1,
      "status": 1,
      "date": "2020-02-29 00:00:00",
      "updated": "2020-02-10 14:16:05",
      "created": "2020-02-10 14:16:08",
      "storeVos": [
        {
          "id": 5,
          "name": "外婆味道大观店",
          "address": "大观商业城",
          "area": "云南,昆明市,五华区",
          "phone": "45353",
          "linkman": "大观"
        }
      ]
    }
  ]
}

```


## 8 发票
### 8.1 发票列表

- 请求地址：manage/v3/invoice/list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|userId|int|是|无|用户id|
|cardId|int|是|无|会员卡id|

- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1582682730109,
  "data": {
    "totalCharge": 100.0,
    "totalInvoice": 100.0,
    "amount": 0.0,
    "invoiceVos": [
      {
        "id": 2, //发票列表id
        "amount": 90.00, //开票金额
        "summary": "",
        "storeId": 3, 
        "storeName": "外婆味道南屏店", //开票门店
        "created": "2020-02-25 12:46:31", //开票时间
        "image": null //发票图片
      },
      {
        "id": 1,
        "amount": 10.00,
        "summary": "",
        "storeId": 3,
        "storeName": "外婆味道南屏店",
        "created": "2020-02-25 12:46:20",
        "image": null
      }
    ]
  }
}

```

### 8.2 开发票

- 请求地址：manage/v3/invoice/add
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|userId|int|是|无|用户id|
|cardId|int|是|无|会员卡id|
|amount|BigDecimal|是|无|开票金额|
|image|string|是|无|发票图片|

- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "msg": "添加成功",
  "time": 1582683071521
}

```

### 8.3 用户会员详情

- 请求地址：manage/v3/card/card_detail
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|userId|int|是|无|用户id|
|cardId|int|是|无|会员卡id|


- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1582684903520,
  "data": {
    "id": 7, //用户会员关联表id
    "membershipCardId": 4, //会员卡id
    "userId": 7, //用户id
    "qrcodeType": null,
    "phone": "15752400657", //电话
    "balance": 3748.00, //可开票余额
    "created": "2019-12-14 20:57:08", //开卡时间
    "cardNo": 7919121420122, //卡号 ID
    "title": "外婆味道活动卡", //卡标题
    "summary": "<p>活动卡活动卡</p>",
    "colour": "#E43B19", //卡颜色
    "logo": "/item/191213/747d5de2-5415-4c70-812f-e1a87ff27184.jpeg",
    "type": 11, 
    "storeIds": "3,4",
    "grade": null,
    "summaryGrade": null,
    "capital": null,
    "send": null,
    "code": null,
    "avatar": "https://wx.qlogo.cn/order/comment/20190923/1569232138231132-600-600.jpg", //用户头像
    "nickname": "路遥\uD83D\uDE34\uD83D\uDE34" //用户昵称
    "storeName": "外婆味道南屏店" //开卡门店
  }
}

```

### 8.4 发票记录查询

- 请求地址：manage/v3/invoice/all_list
- 服务协议：HTTP/POST
- 是否需要身份认证：是
- 作者：linqin

| 参数名称 | 参数类型 | 是否必传 | 默认值 | 参数说明 |
| :------: | :------: | :------: | :----: | :------: |
|  token   |  String  |    是    |   无   |      访问令牌   |
|phone|string|否|无|用户电话（模糊查询）|
|startTime|Long|否|无|开始时间（13位时间戳）|
|endTime|Long|否|无|结束时间（13位时间戳）|
|pageNum|int|是|1|分页|
|pageSize|int|是|14|分页大小|


- 請求結果示例

```js
{
  "errCode": 0,
  "result": 0,
  "time": 1587210254396,
  "data": [
    {
     "id": 2, //发票列表id
      "amount": 90.00, //开票金额
      "summary": "", //简介/备注
      "storeId": 3, 
       "storeName": "外婆味道南屏店", //开票门店
      "created": "2020-02-25 12:46:31", //开票时间
      "image": null //发票图片
      "userId": 7, //用户id
      "phone": "15752400657",//用户待你好
      "nickname": "路遥\uD83D\uDE34\uD83D\uDE34"//用户昵称
    }
  ],
  "total": 3,
  "pages": 1,
  "pageNum": 1,
  "pageSize": 14
}


```