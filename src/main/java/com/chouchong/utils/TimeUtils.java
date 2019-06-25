package com.chouchong.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author linqin
 * @date 2019/6/24
 */

public class TimeUtils {


    /**
     * 时间戳(当天0点)
     */
    public static Long time(Long day) throws ParseException {
        Date now = new Date(day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(now);//日期
        Date parse = dateFormat.parse(format);  //时间戳
        day = parse.getTime() / 1000;
        return day;
    }

    /**
     * 时间戳（当天12点）
     */
    public static Long timeEnd(Long end) throws ParseException {
        Date now = new Date(end);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(now);
        Date parse = dateFormat.parse(format);
        end = DateUtils.addDays(parse, 1).getTime() / 1000;
        return end;
    }
}
