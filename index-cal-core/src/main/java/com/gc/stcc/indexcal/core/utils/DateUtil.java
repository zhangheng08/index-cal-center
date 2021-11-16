package com.gc.stcc.indexcal.core.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

/**
 * 日期工具类
 */
public class DateUtil {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
	public static final String DEFAULT_HM_FORMAT = "HH:mm";
	public static final String DEFAULT_MD_FORMAT = "MM-dd";
	public static final String DEFAULT_DHM_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMAT_YEAR = "yyyy";
	public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
	public static final String FORMAT_YEAR_MONTH_DAY = "yyyy-MM-dd";
	public static final String FORMAT_HOUR_MINUTE_SECOND = "HH:mm:ss";
	public static final String CN_DATE_FORMAT = "yyyy年MM月dd日";

	public static final String PURE_FORMATE_YEAR = "yyyy";
	public static final String PURE_FORMATE_MONTH = "yyyyMM";
	public static final String PURE_FORMATE_DAY = "yyyyMMdd";

	public static final String MYSQL_DATE_TIME_FORMATE="%Y-%m-%d %T";

	/**
	 * get now
	 */
	public static Date getNow() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 按指定格式将字符串转换为日期
	 */
	public static Date str2Date(String dateStr, String pattern) throws Exception {
		if (null == dateStr) {
			return null;
		}
		if (null == pattern) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		return format.parse(dateStr);
	}

	/**
	 * 按指定格式将字符串转换为日期时间
	 */
	public static Date str2DateTime(String dateStr, String pattern) throws ParseException {
		if (null == dateStr) {
			return null;
		}
		if (null == pattern) {
			pattern = DEFAULT_DATE_TIME_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		return format.parse(dateStr);
	}

	/**
	 * 将日期格式化为字符串
	 */
	public static String date2Str(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		if (null == pattern) {
			pattern = DEFAULT_DATE_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		return format.format(date);
	}

	/**
	 * 将日期时间格式化为字符串
	 */
	public static String dateTime2Str(Date date, String pattern) {
		if (null == date) {
			return null;
		}
		if (null == pattern) {
			pattern = DEFAULT_DATE_TIME_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat();
		format.applyPattern(pattern);
		return format.format(date);
	}

	/**
	 * 取得当前时间戳
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	}

	/**
	 * 取得当前日期
	 */
	public static String thisDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(calendar.getTime());
	}

	/**
	 * 取得当前时间
	 */
	public static String thisTime() {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		return new SimpleDateFormat(DEFAULT_TIME_FORMAT).format(calendar.getTime());
	}

	/**
	 * 取得当前完整日期时间
	 */
	public static Date thisDateTime(int minute) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 取得当前完整日期时间(字符串)
	 */
	public static String thisDateTimeStr(int minute) {
		return new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT).format(thisDateTime(minute));
	}

	/**
	 * 获取某月最后一天的日期数字
	 */
	public static int getLastDayOfMonth(Date firstDate) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 取得今天的最小时间
	 */
	public static Date getTodayMin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 取得今天的最小时间[+1秒,如:2016-01-11 00:00:01]
	 */
	public static Date getTodayMinEx(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 取得今天的最小时间Calendar
	 */
	public static Calendar getTodayMinCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * 取得今天的最大时间
	 */
	public static Date getTodayMax() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * 取得下的最小时间
	 */
	public static Date getTomarrowMin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, 1);
		return cal.getTime();
	}

	/**
	 * 取得上周某一天的最小时间
	 */
	public static Date getLastWeekMin() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	/**
	 * 取得上周某一天的最大时间
	 */
	public static Date getLastWeekMax() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}

	/**
	 * 根据日期来获取day后的第一个周日
	 * 
	 * @param day
	 * @return
	 */
	public static Date getSundayByDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0) {
			day_of_week = 7;
		}
		cal.add(Calendar.DATE, -day_of_week + 7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据日期来获取day后的第一个季度的第一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getSeasonByDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		if (month >= 0 && month <= 2) {
			if (month != 0 && cal.get(Calendar.DATE) != 1) {
				cal.set(Calendar.MONTH, 3);
			}
		} else if (month >= 3 && month <= 5) {
			if (month != 3 && cal.get(Calendar.DATE) != 1) {
				cal.set(Calendar.MONTH, 6);
			}
		} else if (month >= 6 && month <= 8) {
			if (month != 6 && cal.get(Calendar.DATE) != 1) {
				cal.set(Calendar.MONTH, 9);
			}
		} else {
			if (month != 9 && cal.get(Calendar.DATE) != 1) {
				cal.set(Calendar.YEAR, year + 1);
				cal.set(Calendar.MONTH, 0);
			}
		}
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 根据日期来获取day后的第一年的第一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getYearByDay(Date day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(day);
		if (cal.get(Calendar.MONTH) != 0 && cal.get(Calendar.DATE) != 1) {
			cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 由指定时间、时间域、数额，计算时间值
	 */
	public static Date genDiffDate(Date standard, int type, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(standard);
		cal.add(type, amount);
		return cal.getTime();
	}

	/**
	 * 生成指定时间所在的小时段（清空：分钟、秒、毫秒）
	 */
	public static Date genHourStart(Date standard) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(standard);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * 取得当前天后，减去指定天数后的最小时间
	 */
	public static Date getBeforeDayMin(Date date, int beforeDay) {

		return getDayMin(date, -beforeDay);
	}

	/**
	 * 取得当前天后，减去指定天数后的最大时间
	 */
	public static Date getBeforeDayMax(Date date, int beforeDay) {

		return getDayMax(date, -beforeDay);
	}

	/**
	 * 取得当前天后，加上指定天数后的最小时间
	 */
	public static Date getAfterDayMin(Date date, int afterDay) {

		return getDayMin(date, afterDay);
	}

	/**
	 * 取得当前天后，加上指定天数后的最大时间
	 */
	public static Date getAfterDayMax(Date date, int afterDay) {

		return getDayMax(date, afterDay);
	}

	/**
	 * 取得当前天后，加上指定天数后的最小时间
	 */
	public static Date getDayMin(Date date, int addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DATE, addDay);

		return cal.getTime();
	}

	/**
	 * 取得当前天 ,加上指定天数后的最大时间
	 */
	public static Date getDayMax(Date date, int addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, addDay);

		return cal.getTime();
	}

	/**
	 * 获取指定日期的最小值
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTheDayMin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取指定日期的最大值
	 * 
	 * @param date
	 * @return
	 */
	public static Date getTheDayMax(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	/**
	 * 取得当前天 ,加上指定天数后的时间
	 */
	public static Date addDays(Date date, int addDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, addDay);
		return cal.getTime();
	}

	/**
	 * 取得当前天 ,加上指定月份数后的时间
	 */
	public static Date addMonths(Date date, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		return cal.getTime();
	}

	/**
	 * 日期差天数(按照时间比较,如果不足一天会自动补足)
	 */
	public static int diff(Date date1, Date date2) throws Exception {
		long day = 24L * 60L * 60L * 1000L;
		String str1 = date2Str(date1, "yyyy-MM-dd");
		date1 = str2Date(str1, "yyyy-MM-dd");
		String str2 = date2Str(date2, "yyyy-MM-dd");
		date2 = str2Date(str2, "yyyy-MM-dd");

		return (int) (((date2.getTime() - date1.getTime()) / day));
		// return (int) Math.ceil((((date2.getTime() - date1.getTime()) / (24 *
		// 60 * 60 * 1000d))));
	}

	/**
	 * 日期差天数(和当前时间比)
	 * 
	 * @param date
	 *            比较日期
	 * @return 和当前日期差天数
	 * @throws Exception
	 */
	public static int diff(Date date) throws Exception {
		return diff(new Date(), date);
	}

	/**
	 * 按固定格式比较两个日期
	 */
	public static int compareDate(Date date1, Date date2, String pattern) {
		String d1 = date2Str(date1, pattern);
		String d2 = date2Str(date2, pattern);
		return d1.compareTo(d2);
	}

	/**
	 * 按固定格式比较两个日期+时间
	 */
	public static int compareDateTime(Date time1, Date time2, String pattern) {
		String d1 = dateTime2Str(time1, pattern);
		String d2 = dateTime2Str(time2, pattern);
		return d1.compareTo(d2);
	}

	/**
	 * 判断是否是闰年
	 */
	public static boolean isLeapyear(Date date) {
		GregorianCalendar gregorianCalendar = new GregorianCalendar();
		gregorianCalendar.setTime(date);
		return gregorianCalendar.isLeapYear(gregorianCalendar.get(Calendar.YEAR));
	}

	/**
	 * 根据传入日期得到本月月末
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getLastDateOfMonth(c);
	}

	/**
	 * 根据传入日期得到本月月初
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return getFirstDateOfMonth(c);
	}

	/**
	 * 根据传入日期得到本月月初
	 */
	public static Date getFirstDateOfMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 根据传入日期得到本月月末
	 */
	public static Date getLastDateOfMonth(Calendar calendar) {
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
	 */
	public static boolean isLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return isLastDateOfMonth(c);
	}

	/**
	 * 根据传入日期得到本月月末，如果传入日期为月末则返回传入日期
	 */
	public static boolean isLastDateOfMonth(Calendar date) {
		if (date.getActualMaximum(Calendar.DAY_OF_MONTH) == date.get(Calendar.DAY_OF_MONTH)) {
			return true;
		}
		return false;
	}

	/**
	 * 根据日期得到年份
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * 根据日期得到月份
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * 根据日期得到日
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MINUTE);
	}

	/**
	 * 时间格式化
	 */
	public static String formatMilliseconds(long millonSeconds, String language) {
		String v = "";
		long second = millonSeconds / 1000;// 转换为秒
		long millonSecond = millonSeconds - second * 1000;// 多出的不足一秒的毫秒数
		boolean isChinese = language.equalsIgnoreCase("chinese");
		if (isChinese) {
			v += millonSecond + "毫秒";
		} else {
			v += millonSecond + "ms.";
		}
		if (second > 0)// 如果还有秒
		{
			long minutes = second / 60;// 分钟取整
			second = second - minutes * 60;// 不足一分钟的秒数
			if (isChinese) {
				v = second + "秒" + v;
			} else {
				v = second + "s" + v;
			}
			if (minutes > 0)// 如果还有分钟
			{
				long hours = minutes / 60;// 小时取整
				minutes = minutes - hours * 60;// 不足一小时的分钟数
				if (isChinese) {
					v = minutes + "分" + v;
				} else {
					v = minutes + "minutes " + v;
				}
				if (hours > 0) {
					long days = hours / 24;// 天取整
					hours = hours - days * 24;// 不足一天的小时数
					if (isChinese) {
						v = hours + "小时" + v;
					} else {
						v = hours + "hours " + v;
					}
					if (days > 0) {
						if (isChinese) {
							v += days + "天" + v;
						} else {
							v += days + " days " + v;
						}
					}
				}
			}
		}
		return v;
	}

	/**
	 * 时间格式化
	 */
	public static String formatMilliseconds(long millonSeconds) {

		return formatMilliseconds(millonSeconds, "CHINESE");
	}

	/**
	 * 时间加分钟
	 * 
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	public static Date addSecond(Date date, int sec) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, sec);
		return calendar.getTime();
	}

	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static String getMonthOfDate(Date date) throws Exception {
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		int w = calendar.get(Calendar.MONTH) + 1;
		if (w < 0) {
			w = 0;
		}
		return w + "月";
	}

	public static Date setHour(Date date, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, hour);
		return cal.getTime();
	}

	public static Date setMinute(Date date, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 根据date日期判断是否为“当天”，若是当天返回0点到当前的start_date和end_date，若不是当天，返回整天
	 * 
	 * @param params
	 * @return
	 */
	public static Map<String, String> modfiTimeString(Map<String, String> params) {
		if (params != null && !params.isEmpty()) {
			if (params.containsKey("date") || params.containsKey("create_date")) {
				String date = "";
				if (params.containsKey("date")) {
					date = params.get("date");
				} else {
					date = params.get("create_date");
				}
				Date conDate = null;
				try {
					conDate = str2Date(date, null);
				} catch (Exception e) {
					System.out.println("时间字符串转化日期错误");
					e.printStackTrace();
				}
				Date currDate = getNow();
				if (getYear(currDate) == getYear(conDate) && getMonth(currDate) == getMonth(conDate)
						&& getDay(currDate) == getDay(conDate)) {
					String dateStr = date2Str(currDate, DEFAULT_DATE_TIME_FORMAT);
					String timeStr = dateStr.substring(10);
					params.put("start_date", date + " 00:00:00");
					params.put("end_date", (date + timeStr));
				} else {
					String startStr = date2Str(getDayMin(conDate, 0), DEFAULT_DATE_TIME_FORMAT);
					String endStr = date2Str(getDayMax(conDate, 0), DEFAULT_DATE_TIME_FORMAT);
					params.put("start_date", startStr);
					params.put("end_date", endStr);
				}

			}
		}
		return params;
	}


	public static Map<String, String> modfiTimeStringByHour(Map<String, String> params) {
		if (params != null && !params.isEmpty()) {
			if ((params.containsKey("date") || params.containsKey("create_date")) && params.containsKey("start_time")
					&& params.containsKey("end_time")) {
				String date = "";
				if (params.containsKey("date")) {
					date = params.get("date");
				} else {
					date = params.get("create_date");
				}
				Date conDate = null;
				try {
					conDate = str2Date(date, null);
				} catch (Exception e) {
					System.out.println("时间字符串转化日期错误");
					e.printStackTrace();
				}
				Date currDate = getNow();
				String startStr = "";
				String endStr = "";
				startStr = date2Str(setHour(conDate, Integer.parseInt(params.get("start_time"))),
						DEFAULT_DATE_TIME_FORMAT);
				if (getYear(currDate) == getYear(conDate) && getMonth(currDate) == getMonth(conDate)
						&& getDay(currDate) == getDay(conDate)) {
					if (getHour(currDate) > Integer.parseInt(params.get("end_time"))) {
						endStr = date2Str(setHour(conDate, Integer.parseInt(params.get("end_time"))),
								DEFAULT_DATE_TIME_FORMAT);
					} else {
						endStr = date2Str(currDate, DEFAULT_DATE_TIME_FORMAT);
					}
				} else {
					endStr = date2Str(setHour(conDate, Integer.parseInt(params.get("end_time"))),
							DEFAULT_DATE_TIME_FORMAT);
				}
				params.put("start_date", startStr);
				params.put("end_date", endStr);
			}
		}
		return params;
	}

	public static Map<String, String> modfiTimeStringByDate(Map<String, String> params) throws Exception {
		if (params != null && !params.isEmpty()) {
			String startStr = params.get("start_date");
			String endStr = params.get("end_date");
			String date = "";
			if (params.containsKey("date")) {
				date = params.get("date");
			} else {
				date = params.get("create_date");
			}
			Date currDate = null;
			try {
				currDate = str2Date(date, null);
			} catch (Exception e) {
				System.out.println("时间字符串转化日期错误");
				e.printStackTrace();
			}
			if (startStr == null) {
				startStr = date2Str(getDayMin(currDate, 0), DEFAULT_DATE_TIME_FORMAT);
			} else {
				startStr = date2Str(str2Date(startStr, DEFAULT_DATE_FORMAT), DEFAULT_DATE_TIME_FORMAT);
			}
			if (endStr == null) {
				endStr = date2Str(getDayMax(currDate, 0), DEFAULT_DATE_TIME_FORMAT);
			} else {
				endStr = date2Str(getDayMax(str2Date(endStr, DEFAULT_DATE_FORMAT), 0), DEFAULT_DATE_TIME_FORMAT);
			}
			params.put("start_date", startStr);
			params.put("end_date", endStr);
		}
		return params;
	}

	public static Date dateOffsetBy(Date now, int synonym, int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);
		calendar.add(synonym, offset);
		return calendar.getTime();
	}

}