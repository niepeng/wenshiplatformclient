package com.baibutao.app.waibao.yun.android.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author lsb
 *
 * @date 2012-5-29 ����11:31:09
 */
public class DateUtil {
	public static final String DEFAULT_DATE_FMT = "yyyy-MM-dd HH:mm";

	public static final String DATE_FMT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FMT_YMD = "MM-dd";
	
	public static final String DATE_MMddHHmm = "MM-dd HH:mm";
	
	public static final String DATE_FMT_DM = "MM/dd";
	
	public static final String DATE_HHmm = "HH:mm";
	
	public static final String DATE_HHmmss = "HH:mm:ss";
	
	/**
	 * date1��date2���£����٣�����������true�����򷵻�false
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isNewer(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return false;
		}
		long d1 = date1.getTime();
		long d2 = date2.getTime();
		return d1 > d2;
	}
	
	public static Date parse(String input, String fmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		try {
			return sdf.parse(input);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static Date parse(String input) {
		return parse(input, DEFAULT_DATE_FMT);
	}
	
	public static Date parseDefault(String input) {
		return parse(input, DATE_FMT);
	}
	
	
	public static String format(Date date, String fmt) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(fmt);
			return sdf.format(date);
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String format(Date date) {
		return format(date, DEFAULT_DATE_FMT);
	}

	public static String formatDefault(Date date) {
		return format(date, DATE_FMT);
	}
	
	
	
	
	/**
	 * ������
	 * @param date
	 * @param changeValue  ����Ϊ���ӣ�����Ϊ��ȥ
	 * @return
	 */
	public static Date changeDay(Date date , int changeValue) {
		if(date == null || changeValue == 0) {
			return date;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, changeValue);
		return c.getTime();
	}
	
	public static Date initDate(int year, int month, int day, int hour, int min, int second) {
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, day, hour, min, second);
		return c.getTime();
	}

}
