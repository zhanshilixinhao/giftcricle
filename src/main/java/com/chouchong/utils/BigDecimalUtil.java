package com.chouchong.utils;

import java.math.BigDecimal;

/**
 * @author yichenshanren
 * @date 2017/10/18
 */

public class BigDecimalUtil {

    /**
     * 加法
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static BigDecimal add(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.add(b2);
    }

    /**
     * 减法
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static BigDecimal sub(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.subtract(b2);
    }

    /**
     * 乘法
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static BigDecimal multi(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        return b1.multiply(b2);
    }

    public static BigDecimal multi(double arg1, float arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(String.valueOf(arg2));
        return b1.multiply(b2);
    }

    /**
     * 除法
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static BigDecimal div(double arg1, double arg2) {
        BigDecimal b1 = new BigDecimal(Double.toString(arg1));
        BigDecimal b2 = new BigDecimal(Double.toString(arg2));
        // 四舍五入保留两位小数
        return b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP);
    }

}
