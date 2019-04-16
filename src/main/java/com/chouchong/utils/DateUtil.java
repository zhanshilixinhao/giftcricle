package com.chouchong.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间日期工具类
 *
 * @author yichenshanren
 * @date 2017/9/23
 */

public class DateUtil {

    //joda-time
    // string->date
    public static Date string2date(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        DateTime dateTime = formatter.parseDateTime(date);
        return dateTime.toDate();
    }

    // date->string
    public static String date2string(Date date, String format) {
        if (date == null) return StringUtils.EMPTY;
        DateTime dateTime = new DateTime(date);
        return dateTime.toString(format);
    }

    public static String date2string(Date date) {
        return date2string(date, STANDARD_FORMAT);
    }

    public static String today(String format) {
        return date2string(new Date(), format);
    }

    public static Date string2date(String date) {
        return string2date(date, STANDARD_FORMAT);
    }

    /**
     * 获得从现在到明天0点的时间 毫秒
     *
     * @return 获得从现在到明天0点的时间 毫秒
     */
    public static long tomZeroDiff() {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //今天零点零分零秒的毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        // 明天0点
        long tom = zero + 24 * 60 * 60 * 1000;
        return tom - current;
    }

    /**
     * 获取明天0点的时间
     * @return
     */
    public static long tomZeroMill() {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //今天零点零分零秒的毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        // 明天0点
        long tom = zero + 24 * 60 * 60 * 1000;
        return tom;
    }


    public static long todayZeroMill() {
        //当前时间毫秒数
        long current = System.currentTimeMillis();
        //今天零点零分零秒的毫秒数
        long zero = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        return zero;
    }

    public static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param sDateTime 较小日期时间戳
     * @param bDateTime 较大日期时间戳
     * @return 相差秒数
     * @throws ParseException
     * @功能说明:计算两个日期时间相差的秒数.
     * @返回类型:Long
     * @方法名称:getDiiferSecondsBetween2DateTime
     * @类名称:DateUtils
     * @文件名称:DateUtils.java
     * @所属包名:com.children.utils
     * @项目名称:children
     * @创建时间:2017-7-7 下午5:25:57
     * @作者:SAM QZL
     * @修改：yichen
     * @版本:1.0
     */
    public static long getDiiferSecondsBetween2DateTime(long sDateTime, long bDateTime) throws ParseException {

        /** 时间相减计算相差秒 **/
        /** 返回结果 **/
        return (bDateTime - sDateTime) / 1000;
    }

    public static final long DAYMI = 1000 * 60 * 60 * 24;

    public static final boolean compare(long time1, long time2, int days) {
        long curDays = (time1 - time2) / DAYMI;
        return curDays < days;
    }

    public static int getDiffDays(long time1, long time2 ) {
       return (int) ((time1 - time2) / DAYMI);
    }

}
