package com.chouchong.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yichenshanren
 * @date 2017/10/9
 */

public class Utils {

    /**
     * 将字符串进行MD5加密
     */
    public static String toMD5(String info) {
        return toMD5(info, true);
    }

    public static String toMD5(String info, boolean toUpperCase) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            if (toUpperCase) {
                return strBuf.toString().toUpperCase();
            } else return strBuf.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return "";
        }
    }

    /**
     * 字符串转long
     *
     * @param str
     * @return 转换之后的long，失败返回-1
     */
    public static long string2long(String str) {
        long l = -1;
        try {
            l = Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return l;
    }

    public static int string2int(String str) {
        int i = -1;
        try {
            i = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static double string2double(String amount) {
        double d = -1;
        d  = Double.parseDouble(amount);
        return 0;
    }

    public static String getNowtime() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String str = formatter.format(curDate);
        return str;
    }
}
