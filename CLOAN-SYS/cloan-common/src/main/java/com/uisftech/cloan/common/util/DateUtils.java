package com.uisftech.cloan.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.uisftech.cloan.common.exception.AppException;

/**
 *
 * @author xuxigang
 * @version 0.4
 * @since 0.3
 * @date 2010-11-16
 */
public class DateUtils {
	public static final String STANDARD_DATE = "yyyy-MM-dd";

	public static final String STANDARD_TIME = "HH:mm:ss";

	public static final String STANDARD_TIME2 = "HHmm";

	public static final String STANDARD_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

	public static final String STANDARD_TIMESTAMP2 = "yyyyMMddHHmm";

	public static final String STANDARD_DATE_SHORT = "yyyyMMdd";

	public static final String STANDARD_YEAR = "yyyy";

	public static final String STANDARD_MONTH = "MM";

	public static final String STANDARD_DAY = "dd";

	public static final String STANDARD_TIMESTAMP3 = "yyyy-MM-dd HH:mm";

	public static Date get(String strDate, String format) {
		if (format == null) {
			throw new AppException("时间格式不能为空");
		}
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(strDate);
		} catch (ParseException ex) {
			throw new AppException("时间转化错误：" + ex.getMessage());
		}
		return date;
	}

	public static String getTodayDate() {
		return get(new Date(), STANDARD_DATE_SHORT);
	}

	public static Date getDate(String strDate) {
		return get(strDate, STANDARD_DATE);
	}

	public static Date getTimestamp(String strDate) {
		return get(strDate, STANDARD_TIMESTAMP);
	}

	public static Date getTimestamp2(String strDate) {
		return get(strDate, STANDARD_TIMESTAMP2);
	}

	public static Date getTimestamp3(String strDate) {
		return get(strDate, STANDARD_TIMESTAMP3);
	}

	public static Date getTime(String strDate) {
		return get(strDate, STANDARD_TIME);
	}

	public static String getDate(Date date) {
		return get(date, STANDARD_DATE);
	}

	public static String getTimestamp(Date date) {
		return get(date, STANDARD_TIMESTAMP);
	}

	public static String getTimestamp2(Date date) {
		return get(date, STANDARD_TIMESTAMP3);
	}

	public static String getTime(Date date) {
		return get(date, STANDARD_TIME);
	}

	public static String getTime2(Date date) {
		return get(date, STANDARD_TIME2);
	}

	public static String get(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 获取当前日期与参数间隔（分钟）的历史日期
	 *
	 * @param minute
	 *            分钟
	 * @return
	 */
	public static String getPreMinuteTimestamp(int minute) {

		Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, -minute);

		return new SimpleDateFormat(STANDARD_TIMESTAMP).format(date.getTime());

	}
	/**
	 * 获取当前日期与参数间隔（分钟）之后的日期
	 *
	 * @param minute
	 *            分钟
	 * @return
	 */
	public static Date getNextMinuteTimestamp(int minute){
		Calendar date = Calendar.getInstance();
		date.add(Calendar.MINUTE, minute);

		return date.getTime();
	}

	/**
	 * 获取上一日日期 格式:yyyy-MM-dd
	 *
	 * @return
	 */
	public static String getLastDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat(STANDARD_DATE).format(date.getTime());
	}

	/**
	 * 获取上一日日期 格式:yyyyMMdd
	 *
	 * @return
	 */
	public static String getLastShortDate() {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, -1);
		return new SimpleDateFormat(STANDARD_DATE_SHORT).format(date.getTime());
	}

	/**
	 * 取到距今天前后几天所属年
	 *
	 * @param day
	 *            -1前一天,1后一天,0当天
	 * @return
	 */
	public static String getYearByDay(int day) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, day);
		return new SimpleDateFormat(STANDARD_YEAR).format(date.getTime());
	}

	/**
	 * 取到距今天前后几天所属月
	 *
	 * @param day
	 *            -1前一天,1后一天,0当天
	 * @return
	 */
	public static String getMonthByDay(int day) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, day);
		return new SimpleDateFormat(STANDARD_MONTH).format(date.getTime());
	}

	/**
	 * 取到距今天前后几天所属天
	 *
	 * @param day
	 *            -1前一天,1后一天,0当天
	 * @return
	 */
	public static String getDayByDay(int day) {
		Calendar date = Calendar.getInstance();
		date.add(Calendar.DAY_OF_MONTH, day);
		return new SimpleDateFormat(STANDARD_DAY).format(date.getTime());
	}

	/**
	 * 获取下一日日期 格式:yyyyMMdd
	 *
	 * @return
	 */
	public static String getNextShortDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(STANDARD_DATE_SHORT).format(cal.getTime());
	}

	/**
	 * 获取下一日日期 格式:yyyyMMdd
	 *
	 * @return
	 */
	public static String getNextShortDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(STANDARD_DATE_SHORT).format(cal.getTime());
	}

	/**
	 * 获取下一日日期
	 *
	 * @return
	 */
	public static String getNextShortDate(Date date, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	public static void main(String[] args){

		int resolveMunit = (int)Math.round(4222225.5);

		System.out.println(resolveMunit);
		System.out.println(DateUtils.getNextMinuteTimestamp(resolveMunit));
	}
}
