package com.huishan.enjoytravel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author 沈煜 on 2017/12/16.
 */

public class TimeUtil {
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_TIME = "HH:mm";
    public final static String FORMAT_TIME2 = "HH mm";
    public final static String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm";
    public final static String FORMAT_NORMAL_TIME = "yyyy-MM-dd HH:mm:ss";
    public final static String FORMAT_MONTH_DAY_TIME = "MM月dd日 hh:mm";
    private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
    private static final int YEAR = 365 * 24 * 60 * 60;// 年
    private static final int MONTH = 30 * 24 * 60 * 60;// 月
    private static final int DAY = 24 * 60 * 60;// 天
    private static final int HOUR = 60 * 60;// 小时
    private static final int MINUTE = 60;// 分钟

    public static long getFormatDateTimeToLong(String date, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        try {
            Date date1 = sdf.parse(date);
            if (date1 != null) {
                return date1.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getFormatDateTime(String date, String originFormat, String targetFormat) {
        try {
            SimpleDateFormat sdf = getSimpleDateFormat(originFormat);
            Date date1 = sdf.parse(date);
            sdf.applyPattern(targetFormat);

            return sdf.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getFormatDateTime(Calendar calendar, String pattern) {
        SimpleDateFormat sdf = getSimpleDateFormat(pattern);
        sdf.applyPattern(pattern);
        return sdf.format(calendar.getTime());
    }

    public static String getMyFormat(int time) {
        return time % 2 == 1 ? FORMAT_TIME : FORMAT_TIME2;
    }

    public static String getTime(long timeStamp, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        sdf.applyPattern(format);
        String s = sdf.format(new Date(timeStamp));
        return s;
    }

    /**
     * 比较时间大小(参数为Calendar类型)
     * @param day_1
     * @param day_2
     * @return  ==0 相等 ；     <0 day_1小于day_2；   >0  day_1大于day_2
     */
    public static int compareTime(Calendar day_1, Calendar day_2){
        return day_1.compareTo(day_2);
    }

    public static String getTimeInterval(long timestamp) {
        long timeGap = timestamp;// 与现在时间相差秒数
        String timeStr;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年送达";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月送达";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天送达";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时送达";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟送达";
        } else if (timeGap > 0 && timeGap < MINUTE) {// 1秒钟-59秒钟
            timeStr = "1分钟内送达";
        } else {
            timeStr = "已超时";
        }
        return timeStr;
    }

    /**
     * 根据时间戳获取描述性时间，如3分钟前，1天前
     *
     * @param timestamp 时间戳 单位为毫秒
     * @return 时间字符串
     */
    public static String getDescriptionTimeFromTimestamp(long timestamp) {
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        System.out.println("timeGap: " + timeGap);
        String timeStr;
        if (timeGap > YEAR) {
            timeStr = timeGap / YEAR + "年前";
        } else if (timeGap > MONTH) {
            timeStr = timeGap / MONTH + "个月前";
        } else if (timeGap > DAY) {// 1天以上
            timeStr = timeGap / DAY + "天前";
        } else if (timeGap > HOUR) {// 1小时-24小时
            timeStr = timeGap / HOUR + "小时前";
        } else if (timeGap > MINUTE) {// 1分钟-59分钟
            timeStr = timeGap / MINUTE + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }


    public static long parseCurrentDateToMS(String date) throws ParseException {
        SimpleDateFormat sdf = getSimpleDateFormat(FORMAT_DATE);
        return sdf.parse(date).getTime();
    }

    public static String getCurrentDay() {
        SimpleDateFormat sdf = getSimpleDateFormat(FORMAT_DATE);
        //SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return sdf.format(new Date());
    }

    /**
     * 根据时间戳获取指定格式的时间，如2011-11-30 08:40
     *
     * @param timestamp 时间戳 单位为毫秒
     * @param format    指定格式 如果为null或空串则使用默认格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getFormatTimeFromTimestamp(long timestamp, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE);
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.parseInt(sdf.format(new Date(timestamp))
                    .substring(0, 4));
            System.out.println("currentYear: " + currentYear);
            System.out.println("year: " + year);
            if (currentYear == year) {//如果为今年则不显示年份
                sdf.applyPattern(FORMAT_MONTH_DAY_TIME);
            } else {
                sdf.applyPattern(FORMAT_DATE_TIME);
            }
        } else {
            sdf.applyPattern(format);
        }
        Date date = new Date(timestamp);
        return sdf.format(date);
    }

    /**
     * 根据时间戳获取时间字符串，并根据指定的时间分割数partSeconds来自动判断返回描述性时间还是指定格式的时间
     *
     * @param timestamp   时间戳 单位是毫秒
     * @param partSeconds 时间分割线，当现在时间与指定的时间戳的秒数差大于这个分割线时则返回指定格式时间，否则返回描述性时间
     * @param format
     * @return
     */
    public static String getMixTimeFromTimestamp(long timestamp, long partSeconds, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        long currentTime = System.currentTimeMillis();
        long timeGap = (currentTime - timestamp) / 1000;// 与现在时间相差秒数
        if (timeGap <= partSeconds) {
            return getDescriptionTimeFromTimestamp(timestamp);
        } else {
            return getFormatTimeFromTimestamp(timestamp, format);
        }
    }

    /**
     * 获取当前日期的指定格式的字符串
     *
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(new Date());
    }

    /**
     * 将日期字符串以指定格式转换为Date
     *
     * @param timeStr 日期字符串
     * @param format  指定的日期格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static Date getTimeFromString(String timeStr, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        try {
            return sdf.parse(timeStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    /**
     * 将Date以指定格式转换为日期时间字符串
     *
     * @param date   日期
     * @param format 指定的日期时间格式，若为null或""则使用指定的格式"yyyy-MM-dd HH:MM"
     * @return
     */
    public static String getStringFromTime(Date date, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        if (format == null || format.trim().equals("")) {
            sdf.applyPattern(FORMAT_DATE_TIME);
        } else {
            sdf.applyPattern(format);
        }
        return sdf.format(date);
    }

    //使用ThreadLocal限制SimpleDateFormat只能在线程内共享
    public static SimpleDateFormat getSimpleDateFormat(String datePattern) {
        SimpleDateFormat sdf = null;
        sdf = threadLocal.get();
        if (sdf == null) {
            sdf = new SimpleDateFormat(datePattern, Locale.CHINA);
            threadLocal.set(sdf);
        } else {
            sdf.applyPattern(datePattern);
        }
        return sdf;
    }
}