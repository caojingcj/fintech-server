package com.fintech.util;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**   
* @Title: DateUtils.java 
* @Package com.medcfc.utils 
* @author qierkang xyqierkang@163.com   
* @date 2018年5月23日 下午10:37:36  
* @Description: TODO[ 用一句话描述该文件做什么 ]
*/
public class DateUtils {

    private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
    /** */
    public static final String DATESHOWFORMAT = "yyyy-MM-dd";

    /** */
    public static final String TIMESHOWFORMAT = "HH:mm:ss";
    /** */
    public static final String DATETIMESHOWFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_SHOWFORMAT = "yyyyMMddHHmmss";

    public static final String DATESHOT = "yyyyMMdd";
    public static final String TIMESHOT = "HHmmss";

    /**
     * 计算两个日期的间隔天数
     *
     * @param startDate 开始时间，如：2008-12-03 11:00:00
     * @param endDate   结束时间，如：2009-12-31 11:00:00
     * @return long 间隔天数(long)
     * @throws ParseException
     */
    public static long getBetweenDays(String startDate, String endDate) {
        SimpleDateFormat d = new SimpleDateFormat(DATESHOWFORMAT);
        long t1 = 0, t2 = 0;
        try {
            t1 = d.parse(startDate).getTime();
            t2 = d.parse(endDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (t2 - t1) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取与指定日期相差指定 天数 的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param dayCount      向前或向后的天数，向后为正数，向前为负数
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return String 处理后的日期字符
     */
    public static String getAfterDate(String baseDate, int dayCount, String patternString) {
        int year = Integer.parseInt(baseDate.substring(0, 4));
        int month = Integer.parseInt(baseDate.substring(5, 7));
        int date = Integer.parseInt(baseDate.substring(8, 10));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        calendar.add(Calendar.DATE, dayCount);
        Date _date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(patternString);
        String dateString = formatter.format(_date);
        return dateString;
    }

    /**
     * 获取与指定日期相差指定 天数 的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param dayCount      向前或向后的天数，向后为正数，向前为负数
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return String 处理后的日期字符
     */
    public static String getAfterDate(Date baseDate, int dayCount, String patternString) {
        String _baseDate = getDateString(baseDate, DATETIMESHOWFORMAT);
        return getAfterDate(_baseDate, dayCount, patternString);
    }

    /**
     * 获取与指定日期相差指定 月数 的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param monthCount    向前或向后的月数，向后为正数，向前为负数
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return String 处理后的日期字符
     */
    public static String getAfterMonth(String baseDate, int monthCount, String patternString) {
        int year = Integer.parseInt(baseDate.substring(0, 4));
        int month = Integer.parseInt(baseDate.substring(5, 7));
        int date = Integer.parseInt(baseDate.substring(8, 10));
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        calendar.add(Calendar.MONTH, monthCount);
        Date _date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(patternString);
        String dateString = formatter.format(_date);
        return dateString;
    }

    /**
     * 获取与指定日期相差指定 月数 的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param monthCount    向前或向后的月数，向后为正数，向前为负数
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return String 处理后的日期字符
     */
    public static String getAfterMonth(Date baseDate, int monthCount, String patternString) {
        String _baseDate = getDateString(baseDate, DATETIMESHOWFORMAT);
        return getAfterMonth(_baseDate, monthCount, patternString);
    }

    /**
     * 获取与指定日期相差指定 月数 并减去天数的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param monthCount    向前或向后的月数，向后为正数，向前为负
     * @param dateCount     加或减去的天数，向后为正数，向前为负
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static String getEndDate(String baseDate, int monthCount, int dateCount,
                                    String patternString) {
        int day = Integer.parseInt(baseDate.substring(8, 10));
        String endDate = getAfterMonth(baseDate, monthCount, patternString);
        int endDay = Integer.parseInt(endDate.substring(8, 10));
        // 说明日期没变
        if (endDay == day) {
            // 月数为正则为减一
            if (monthCount > 0) {
                endDate = getAfterDate(endDate, dateCount, patternString);
            } else {
                endDate = getAfterDate(endDate, dateCount, patternString);
            }
        } else { // 日期已变
            if (monthCount < 0) {
                endDate = getAfterDate(endDate, dateCount, patternString);
            }
        }
        return endDate;
    }

    /**
     * 获取与指定日期相差指定 月数 并减去天数的日期
     *
     * @param baseDate      时间字符串，如：2008-12-03 11:00:00
     * @param monthCount    向前或向后的月数，向后为正数，向前为负
     * @param dateCount     加或减去的天数，向后为正数，向前为负
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static String getEndDate(Date baseDate, int monthCount, int dateCount,
                                    String patternString) {
        String _baseDate = getDateString(baseDate, DATETIMESHOWFORMAT);
        return getEndDate(_baseDate, monthCount, dateCount, patternString);
    }

    /**
     * 根据日期获取当前日期是否为周六周日
     * @param dateStr
     * @return 1 表示为周末, 0 表示为平常日子
     */
    public static String getIsWeekDay(String dateStr){
    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    String bDate =dateStr;
        Date bdate = null;
        try {
            bdate = format1.parse(bDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
    cal.setTime(bdate);
    if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY)
    {
        return "1";
    }else {
        return "0";
    }
}
    /**
     * 当前日期转换为指定月数后 的日期
     *
     * @param monthCount    向前或向后的月数，向后为正数，向前为负
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return String 转换后的日期
     */
    public static String getBeforeMonth(int monthCount, String patternString) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthCount);
        Date _date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(patternString);
        return formatter.format(_date);
    }

    /**
     * 日期格式化(String转换为Date)
     *
     * @param dateStr 日期字符串
     * @param patten  处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static Date getDateToString(String dateStr, String patten) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            logger.error("转换异常", e);
        }
        return null;
    }

    /**
     * 日期格式化(String转换为String)
     *
     * @param date          日期字符串
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static String getDateString(String date, String patternString) {
        if (date == null)
            return "";
        if (date.length() < 10)
            return "";
        SimpleDateFormat formatter = new SimpleDateFormat(patternString, Locale.ENGLISH);
        int len = patternString.length();
        if (len > date.length()) {
            patternString = patternString.substring(0, date.length());
        }
        return formatter.format(getDateToString(date, patternString));
    }

    /**
     * 日期格式化(Date转换为String)
     *
     * @param _date         日期
     * @param patternString 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static String getDateString(Date _date, String patternString) {
        String dateString = "";
        if (_date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat(patternString);
            dateString = formatter.format(_date);
        }
        return dateString;
    }

    /**
     * 日期格式转换 DATE to DATE
     *
     * @param _date  日期
     * @param patten 处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static Date dateToDate(Date _date, String patten) {
        String dateStr = "";
        SimpleDateFormat formatter = new SimpleDateFormat(patten);
        try {
            if (_date != null) {
                dateStr = formatter.format(_date);
            }
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            logger.error("转换异常", e);
        }
        return null;
    }

    /**
     * 获得格式化日期之后的 String数据
     *
     * @param dateLong
     * @param patten
     * @return
     */
    public static String getDateOfString(Long dateLong, String patten) {
        if (dateLong != null) {
            return (new SimpleDateFormat(patten).format(new Date(dateLong.longValue()))).toString();
        }
        return "";
    }

    /**
     * 文本时间转换为时间对象
     *
     * @param baseDate 日期字符串
     * @return
     */
    public static java.sql.Date getSqlDate(String baseDate) {
        if (baseDate == null || baseDate.length() == 0)
            return null;
        Date date = getDateToString(baseDate, DATESHOWFORMAT);
        return new java.sql.Date(date.getTime());
    }

    /**
     * java.util.Date对象转换为java.sql.Date对象
     *
     * @param date java.util.Date对象
     * @return Date java.sql.Date对象
     */
    public static java.sql.Date UtilDateToSQLDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 获取到指定样式的年月日(年月日参数为int型)
     *
     * @param year          年
     * @param month         月
     * @param date          日
     * @param patternString 日期格式，如：yyyy-MM-dd HH:mm:ss EEE
     * @return 格式化后的字符串
     */
    public static String getDateString(int year, int month, int date, String patternString) {
        String dateString = "";
        SimpleDateFormat formatter = new SimpleDateFormat(patternString);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, date);
        Date showDate = calendar.getTime();
        dateString = formatter.format(showDate);
        return dateString;
    }

    /**
     * 获取到指定样式的年月日(年月日参数为String型)
     *
     * @param year          年
     * @param month         月
     * @param date          日
     * @param patternString 日期格式，如：yyyy-MM-dd HH:mm:ss EEE
     * @return 格式化后的字符串
     */
    public static String getDateString(String year, String month, String date, String patternString) {
        String dateString = "";
        try {
            int y = Integer.parseInt(year);
            int m = Integer.parseInt(month);
            int d = Integer.parseInt(date);
            dateString = getDateString(y, m, d, patternString);
        } catch (Exception e) {
            logger.error("转换异常", e);
        }
        return dateString;
    }

    /**
     * 获取当前日期
     *
     * @param patternString
     * @return
     */
    public static String getDateStr(String patternString) {
        SimpleDateFormat formatter = new SimpleDateFormat(patternString);
        String date = formatter.format(new Date(System.currentTimeMillis()));
        return date;
    }

    /**
     * 验证输入的文本信息日期是否合
     *
     * @param dateStr
     * @return
     */
    public static Date isDate(String dateStr) {
        String date_format_1 = "yyyy/MM/dd";
        String date_format_2 = "yyyy-MM-dd";
        String date_format_3 = "yyyyMMdd";
        String date_format_4 = "yyyy.MM.dd";
        String[] date_format = {date_format_1, date_format_2, date_format_3, date_format_4};
        for (int i = 0; i < date_format.length; i++) {
            Date tempDate = isDate(dateStr, date_format[i]);
            if (null != tempDate) {
                return tempDate;
            }
        }
        return null;
    }

    /**
     * 验证输入的文本信息日期是否合
     *
     * @param dateStr
     * @param patternString
     * @return
     */
    public static Date isDate(String dateStr, String patternString) {
        if (StringUtils.isEmpty(patternString)) {
            patternString = DATESHOWFORMAT;
        }
        try {
            SimpleDateFormat formatDate = new SimpleDateFormat(patternString);
            formatDate.setLenient(false);
            ParsePosition pos = new ParsePosition(0);
            Date tempDate = formatDate.parse(dateStr, pos);
            tempDate.getTime();
            return tempDate;
        } catch (Exception e) {
            logger.error("转换异常", e);
        }
        return null;
    }

    /**
     * 把Date转换为Calendar对象
     *
     * @param d Date对象
     * @return Calendar对象
     */
    public static Calendar getCalendar(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal;
    }

    /**
     * 将时间对象转换成指定的格式有小时
     *
     * @param date
     * @return
     */
    public static String parseDateTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(DATETIMESHOWFORMAT);
        return bartDateFormat.format(date);
    }

    /**
     * 将时间对象转换成指定的格式有
     *
     * @param date
     * @return
     */
    public static String parsTime(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(TIMESHOWFORMAT);
        return bartDateFormat.format(date);
    }

    /**
     * 将时间对象转换成指定的格式无小时
     *
     * @param date
     * @return
     */
    public static String parseDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(DATESHOWFORMAT);
        return bartDateFormat.format(date);
    }

    /**
     * 将时间对象转换成指定的格式无小时-YYYYMMDD
     *
     * @param date
     * @return
     */
    public static String parseDate2(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat bartDateFormat = new SimpleDateFormat(DATESHOT);
        return bartDateFormat.format(date);
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static String firstDate() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = ca.getTime();
        return getDateString(firstDate, DATESHOWFORMAT);
    }

    /**
     * 获取当前月最后
     *
     * @return
     */
    public static String lastDate() {
        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.set(Calendar.DAY_OF_MONTH, 1);
        ca.add(Calendar.MONTH, 1);
        ca.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = ca.getTime();
        return getDateString(lastDate, DATESHOWFORMAT);
    }

    /**
     * 获取当前数据里的时间参数
     *
     * @return
     */
    public static String getDateStr() {
        return "sysdate";
    }

    /**
     * 获取上一个月的日期
     *
     * @param date
     * @return
     */
    public static Date getUpMouth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.MONTH, -1);
        return ca.getTime();
    }

    /**
     * 获取日期的年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的日
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DATE);
    }

    /**
     * 获取日期事第几周
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取上一个月的日期
     *
     * @param date
     * @return
     */
    public static Date getUpMouth(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(getDateToString(date, DATESHOWFORMAT));
        ca.add(Calendar.MONTH, -1);
        return ca.getTime();
    }

    /**
     * 获取日期的年
     *
     * @param date
     * @return
     */
    public static int getYear(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(getDateToString(date, DATESHOWFORMAT));
        return ca.get(Calendar.YEAR);
    }

    /**
     * 获取日期的月
     *
     * @param date
     * @return
     */
    public static int getMonth(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(getDateToString(date, DATESHOWFORMAT));
        return ca.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日期的日
     *
     * @param date
     * @return
     */
    public static int getDay(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(getDateToString(date, DATESHOWFORMAT));
        return ca.get(Calendar.DATE);
    }

    /**
     * 获取日期的第几周
     *
     * @param date
     * @return
     */
    public static int getWeek(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(getDateToString(date, DATESHOWFORMAT));
        return ca.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 检测d1 是否大于等于d2
     *
     * @param d1
     * @param d2
     * @return true d1 是否大于等于d2
     */
    public static boolean checkMax(Date d1, Date d2) {
        boolean flag = false;
        if (null != d1) {
            if (null != d2) {
                String d1s = getDateString(d1, "yyyyMMdd");
                String d12s = getDateString(d2, "yyyyMMdd");
                if (Double.valueOf(d1s) >= Double.valueOf(d12s)) {
                    flag = true;
                }
            } else {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据格式，对日期/时间比较
     *
     * @param date1
     * @param date2
     * @param format
     * @return
     * @throws ParseException
     */
    public static int compareDate(String date1, String date2, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long d1 = sdf.parse(date1).getTime();
        long d2 = sdf.parse(date2).getTime();
        if (d1 < d2) {
            return -1;
        } else if (d1 == d2) {
            return 0;
        } else {
            return 1;
        }
    }

    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (Calendar.SUNDAY == calendar.get(Calendar.DAY_OF_WEEK)
                || Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK)) {
            return true;
        }
        return false;
    }

    /**
     * 调整日期对象
     *
     * @param date   日期对象
     * @param field  时间域，参考Calendar中field的定义
     * @param amount 调整数量
     * @return 调整后的日期对象
     */
    public static Date rollDate(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.add(field, amount);
        return cal.getTime();
    }


    /**
     * 给定时间往后延给定分钟
     *
     * @param date
     * @param minute
     * @return
     * @author doumingjun
     * @create date 2012-06-27
     */
    public static Date addMinutes(Date date, int minute) {
        if (null == date)
            date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return (Date) calendar.getTime();
    }

    /**
     * 字符串转换到时间格式
     *
     * @param dateStr   需要转换的字符串
     * @param formatStr 需要格式的目标字符串  举例 yyyy-MM-dd
     * @return Date 返回转换后的时间
     * @throws ParseException 转换异常
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        DateFormat sdf = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("转换异常", e);
        }
        return date;
    }

    /**
     * 日期以天为单位进行加减
     *
     * @param date   原日期
     * @param format 日期格式
     * @param day    日期加减天数
     * @return String  返回字符串日期
     * @throws ParseException 转换异常
     */
    public static String addDate(String date, String format, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long d1 = sdf.parse(date).getTime() + (long) (day * 24 * 3600 * 1000);
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(d1);
        return dateString;
    }

    /**
     * 日期以天为单位进行加减
     *
     * @param date   原日期
     * @param day    日期加减天数
     * @return String  返回字符串日期
     * @throws ParseException 转换异常
     */
    public static Date addDateByDay(Date date, int day) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATESHOWFORMAT);
        long d1 = date.getTime() + (long) (day * 24 * 3600 * 1000);
        String dateString = sdf.format(d1);
        return sdf.parse(dateString);
    }

    /**
     * 重置传入时间的毫秒数
     * @param date 日期
     * @return
     */
    public static Date zeroDateSSS(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    
    public static java.util.Date parse(String pattern, String strDateTime) {
		java.util.Date date = null;
		if (strDateTime == null || pattern == null)
			return null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(pattern);
			formatter.setLenient(false);
			date = formatter.parse(strDateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
    
    public static Date parse(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
     }
    
    /**
	 * 以指定格式返回当时时间
	 * 
	 * @param pattern
	 *            - 日期显示格式
	 * @return the formatted date-time string
	 * @see java.text.SimpleDateFormat
	 */
	public static String formatDateTime(String pattern) {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
		String now = sdf.format(new java.util.Date());
		return now;
	}
	
	/**
	 * 以指定格式返回指定日期的字符串
	 * 
	 * @param pattern
	 *            - 日期显示格式
	 * @param date
	 *            - 需要格式 化的时间
	 * @return the formatted date-time string
	 * @see java.text.SimpleDateFormat
	 */
	public static String formatDateTime(String pattern, java.util.Date date) {
		String strDate = null;
		String strFormat = pattern;
		SimpleDateFormat dateFormat = null;

		if (date == null)
			return "";

		dateFormat = new SimpleDateFormat(strFormat);
		strDate = dateFormat.format(date);

		return strDate;
	}
	
	/** 
     * 获得20150803 --> 15/8/3,15/12/6,15/2/15,15/10/3文件夹形式 
     * 
     * @param date 
     * @return 15/10/3文件夹形式 
     */  
    public static String endFileDir () {  
          Date date = new Date(System. currentTimeMillis());  
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd" );  
          String str = sdf.format(date).toString();  
          StringBuffer sb = new StringBuffer();  
           char[] timeArr = str.toCharArray();  
          sb = sb.append(timeArr[2]).append(timeArr[3]);  
           // str = ""+timeArr[2]+timeArr[3];  
           if (timeArr[4] != '0') {  
               sb = sb.append(timeArr[4]);  
                // str+=timeArr[4];  
          }  
          sb = sb.append(timeArr[5]).append( "/");  
           // str+=""+timeArr[5]+"/";//根据当前系统环境确定分隔符  
            
            
           //确定天数作为文件夹,测试部不需要天数，直接注释即可  
           if(timeArr[6]!= '0'){  
               sb = sb.append(timeArr[6]);  
          }  
          sb = sb.append(timeArr[7]).append( "/");  
           return sb.toString().trim();  
    }
    
    /** 
    * @Title: DateUtils.java 
    * @param @param s
    * @param @param e
    * @param @return    设定文件 
    * @Description: TODO[ 2个时间比较返回天数 ]
    * @throws 
    */
    public static Long diffDay(Date s,Date e){
		return TimeUnit.MILLISECONDS.toDays(Math.abs(e.getTime() - s.getTime()));
	}
    
    /**
	    * @Title: dateComparison
	    * @param @param dateStr 开始时间
	    * @param @param dateS 开始时间 
	    * @param @param dateE 当前时间要比较的时间
	    * @param @return    参数
	    * @return Integer    返回-1说明小于当前时间  1大于
	    * @Description: (  )
	    */
	public static Integer dateComparison(String dateStr,Date dateS,Date dateE){
		Integer dateNumber;
		try {
			dateNumber = null;
			if(StringUtils.isNotEmpty(dateStr)){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date=dateFormat.parse(dateStr);
				dateNumber = date.compareTo(dateE);
			}else{
				dateNumber = dateS.compareTo(dateE);
			}
			return dateNumber;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(long l){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(l);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    
    /**
     * Date => String
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return new SimpleDateFormat(pattern).format(date);
    }
    
    /* 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s){
        String res=null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = simpleDateFormat.parse(s);
            long ts = date.getTime();
            res = String.valueOf(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    
    /** 
    * @Title: DateUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年12月28日 上午2:45:30  
    * @param @param beginDate
    * @param @param endDate
    * @param @return    设定文件 
    * @Description: TODO[ 根据开始和结束时间 返回间隔天数  如：2017-12-1  2017-12-31  返回2017-12-1,2017-12-2,2017-12-3 ....2017-12-31 ]
    * @throws 
    */
    public static List<String> dateRange(String beginDate,String endDate){
        List<String> date=new ArrayList<String>();
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(DATESHOWFORMAT);
            Calendar cal = Calendar.getInstance();  
            cal.setTime(sdf.parse(beginDate));  
            for (long d = cal.getTimeInMillis(); d <= sdf.parse(endDate).getTime(); d = get_D_Plaus_1(cal)) {  
                date.add(sdf.format(d));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }  
        return date;
    }
    
    public static long get_D_Plaus_1(Calendar c) {  
        c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) + 1);  
        return c.getTimeInMillis();  
    }  
    
    /** 
    * @Title: DateUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2017年12月28日 上午3:16:07  
    * @param @param y
    * @param @return    设定文件 
    * @Description: TODO[ 根据年份返回12个月 ]
    * @throws 
    */
    public static List<String> yearReturnMonth(String y){
        List<String> year=new ArrayList<String>();
        for(int i=1;i<13;i++){
            if(i<10){
                year.add(y+"-0"+i);
            }else{
                year.add(y+"-"+i);
            }
        }
        return year;
    }

    /***
     * 根据年份与月份求出,那个月有多少天
     * @param years
     * @param month
     * @return
     */
    public static int getDays(String years,String month)
    {
        if(years!=null&&month!=null&&!years.equals("")&&!month.equals("")) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, Integer.parseInt(years));
            c.set(Calendar.MONTH, Integer.parseInt(month)-1);
            return c.getActualMaximum(Calendar.DAY_OF_MONTH);
        }else {
            return -1;
        }
    }

    /** 
    * @Title: DateUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年1月15日 下午4:11:06  
    * @param @param pattern
    * @param @return    设定文件 
    * @Description: TODO[ 根据 格式 返回当前 时间]
    * @throws 
    */
    public static String getDateTime(){
        SimpleDateFormat df = new SimpleDateFormat(DATETIMESHOWFORMAT);//设置日期格式
        return df.format(new Date());
    }
    
    /** 
    * @Title: DateUtils.java 
    * @author qierkang xyqierkang@163.com   
    * @date 2018年1月15日 下午4:38:04  
    * @param @param pattern
    * @param @return    设定文件 
    * @Description: TODO[ 获取当前时间戳 毫秒 ]
    * @throws 
    */
    public static long getCurrentTimeMillis(){
        return System.currentTimeMillis();
    }
    
	public static void main(String[] args) throws ParseException {
	    System.out.println(yearReturnMonth("2015"));
	    String aa="2017-12-14";
	    System.out.println(System.currentTimeMillis());
	    System.out.println(getDateTime());
	    
//	    System.out.println(DateUtils.parseDate(getSqlDate(aa)));
//	    DateUtils.parseDate(new Date(), DateUtils.parseDate(aa));
		System.out.println(getBetweenDays(DateUtils.parseDate(new Date()), "2017-10-11 23:59:59"));
//		System.out.println( DateUtils.parseDate(new Date()));
//		System.out.println(firstDate());
//		System.out.println(parseDate(getUpMouth(firstDate())));
//		System.out.println(formatDateTime("yyyy-MM",getUpMouth(firstDate())));
	}
    
}
