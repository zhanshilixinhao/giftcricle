package com.chouchong.utils;

import com.chouchong.common.Utils;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;
import java.util.UUID;

/**
 * @author yichenshanren
 * @date 2017/9/29
 */

public class IDUtils {

    public static final String KEY_PREFIX_FAVORITE = "favorite";
    public static final String KEY_PREFIX_CART = "cart";
    public static final String KEY_PREFIX_ORDER_ID = "order_id";
    public static final String KEY_PREFIX_MESSAGE = "message";
    public static final String KEY_PREFIX_ADMIN = "admin";
    public static final String KEY_PREFIX_EARNINGS = "earnings";
    public static final String KEY_PREFIX_AGENT = "agent";
    public static final String KEY_PREFIX_AUCTION = "auction";
    public static final String SPILTE = ":";

    /* 用户信息 */
    public final static String USER_KEY = "PhVTQt8xM5kYbm12gw0w";
    /* banner */
    public final static String CONTENT_KEY = "Z3LdM4LdT122pSwJ";
    /* 商品分类 */
    public final static String ITEM_CATE_KEY = "POTF21BuC5Gdv4XB";
    /* 商品详情 */
    public static final String ITEM_DETAIL_KEY = "rUKraYaxCUAPTfew";
    /* 商品详情 */
    public static final String ITEM_DETAIL_KEY_1 = "rUKradeYaxCUAPTfew";
    /* 商品收藏 */
    public static final String ITEM_FAVORITE = "vMHoRdnzeSErF5tZ";
    /* 商品购物车 */
    public static final String CART_KEY = "HQj05paJvfBwVAyv";
    /* 订单号 */
    public static final String ORDER_ID_KEY = "zI7PStVQIcLaGrXf";
    /* 订单号 */
    public static final String USER_MESSAGE = "dasFD43fdsdfs32fd";
    /* 管理员 */
    public static final String ADMIN_USER = "FH43-dsjf3.fdsfsd";

    public static final String SMS_CODE = "sms_code";
    /* 用户收益 */
    public static final String USER_EARNINGS = "4rfdsj$RRFJkfj";
    /* 代理商详情 */
    public static final String AGENT_DETAIL = "fs*Jfjdsne32432";
    /* 拍卖 */
    public static final String AUCTION = "JJKFDS943fdkj32dsJ";

    public static String genSMSCodeKey(String phone, Integer type) {
        return SMS_CODE + SPILTE + phone + SPILTE + type;
    }


    public static String genAdminUserKey(Integer adminId) {
        return KEY_PREFIX_ADMIN + SPILTE + ADMIN_USER + adminId;
    }

    /**
     * 订单号key
     *
     * @return
     */
    public static String genOrderNoKey() {
        return KEY_PREFIX_ORDER_ID + SPILTE + ORDER_ID_KEY;
    }

    /**
     * 获取用户购物车key
     *
     * @param id
     * @return
     */
    public static String genUserCartKey(Integer id) {
        return KEY_PREFIX_CART + SPILTE + CART_KEY + SPILTE + String.valueOf(id);
    }

    /**
     * 获取商品收藏的key
     *
     * @param itemId
     * @return
     */
    public static String genItemFavoriteKey(long itemId) {
        return KEY_PREFIX_FAVORITE + SPILTE + ITEM_FAVORITE + SPILTE + String.valueOf(itemId);
    }

    /**
     * 商品缓存key
     *
     * @param itemId 商品id
     * @return key
     */
    public static String genItemKey(long itemId) {
        return ITEM_DETAIL_KEY + itemId;
    }

    public static String genItemDetailKey(long itemId) {
        return ITEM_DETAIL_KEY_1 + itemId;
    }

    /**
     * 图片名生成
     */
    public static String genPictureName() {
        return UUID.randomUUID().toString();
    }

    public static String genPictureName(int width, int height) {
        //取当前时间的长整形值包含毫秒
        long millis = System.currentTimeMillis();
        //long millis = System.nanoTime();
        //加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        //如果不足三位前面补0
        String str = millis + String.format("%03d-%s-%s", end3, width, height);
        return str;
    }

    /**
     * 生成一个6位的随机短信验证码
     *
     * @return 6位的随机短信验证码的字符串
     */
    public static String genRandomSmsCode() {
        double r = Math.random();
        if (r < 0.1) r += 0.1;
        return String.valueOf((int) (r * 1000000));
    }

    /**
     * 获取用户key
     *
     * @param id
     * @return
     */
    public static String genUserKey(int id) {
        return USER_KEY + id;
    }

    public static int genUserId(String key) {
        if (StringUtils.isBlank(key)) return -1;
        String[] strings = key.split("@");
        if (strings.length > 1) {
            return Utils.string2int(strings[1]);
        }
        return -1;
    }

    public static String genPhone(String phoen) {
        if (StringUtils.isNotBlank(phoen) && phoen.length() > 10) {
            int len = phoen.length();
            return phoen.substring(0, 4) + " *** " + phoen.substring(len - 4, len);
        }
        return phoen;
    }

    public static String genApkInfoKey() {
        return "fdsf4324gfds5432rew";
    }

    public static String genUserMessageKey(Integer id) {
        return KEY_PREFIX_MESSAGE + SPILTE + USER_MESSAGE + id;
    }

    public static String genIdNumber(String idNumber) {
        if (StringUtils.isNotBlank(idNumber) && idNumber.length() > 8) {
            idNumber = idNumber.substring(0, 6) + " *** " + idNumber.substring(idNumber.length() - 4, idNumber.length());
        }
        return idNumber;
    }

    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static String genEarningsKey(Integer userId) {
        return KEY_PREFIX_EARNINGS + SPILTE + USER_EARNINGS + userId;
    }

    public static String genAgentDetailKey(Integer agentId) {
        return KEY_PREFIX_AGENT + SPILTE + AGENT_DETAIL + agentId;
    }

    public static String genGoldKey() {
        return "GOLD:fdsfdsrwrwsda";
    }

    public static String genAuctionOrderKey() {
        return "Jfs*kfjskJjdkjs:dsj";
    }

    /**
     * 拍卖的key
     *
     * @param itemAuctionId
     * @return
     */
    public static String genAuctionKey(Integer itemAuctionId) {
        return genAuctionKey(itemAuctionId, 1);
    }

    public static String genAuctionKey(Integer itemAuctionId, Integer status) {
        return KEY_PREFIX_AUCTION + SPILTE + AUCTION + SPILTE + status + SPILTE + itemAuctionId;
    }

    public static String genAgentEarningsKey(Integer userId) {
        return "JKFD8493jfdskjfkjds" + userId;
    }

    public static String genAdminTokenKey(Integer adminId) {

        return "JFDKS43kjfdskjzmx,fnsdkjfsd" + adminId;
    }

    public static String genAgentTokenKey(Integer adminId) {
        return "JDKS43fdv565ncmxmnsdhdjs" + adminId;
    }
}
