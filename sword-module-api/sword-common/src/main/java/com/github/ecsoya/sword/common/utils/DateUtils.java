package com.github.ecsoya.sword.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author Jin Liu (angryred@qq.com)
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static String YYYY = "yyyy";

	public static String YYYY_MM = "yyyy-MM";

	public static String YYYY_MM_DD = "yyyy-MM-dd";

	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static String DEFAULT_DATE_FORMAT = YYYY_MM_DD_HH_MM_SS;

	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * 获取当前Date型日期
	 *
	 * @return Date() 当前日期
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * 获取当前日期, 默认格式为yyyy-MM-dd
	 *
	 * @return String
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	public static final String toDefault(final Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
	}

	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static final Date defaultDate(final String ts) {
		try {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(ts);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 日期路径 即年/月/日 如2018/08/08
	 */
	public static final String datePath() {
		final Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * 日期路径 即年/月/日 如20180808
	 */
	public static final String dateTime() {
		final Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * 日期型字符串转化为日期 格式
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (final ParseException e) {
			return null;
		}
	}

	/**
	 * 获取服务器启动时间
	 */
	public static Date getServerStartDate() {
		final long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * 计算相差天数
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
	}

	/**
	 * 计算两个时间差
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		final long nd = 1000 * 24 * 60 * 60;
		final long nh = 1000 * 60 * 60;
		final long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		final long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		final long day = diff / nd;
		// 计算差多少小时
		final long hour = diff % nd / nh;
		// 计算差多少分钟
		final long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static Long getIntervalSeconds(Date start, Date end) {
		if (start == null || end == null || start.after(end)) {
			return null;
		}
		final long diff = end.getTime() - start.getTime();
		return diff / 1000;
	}

	public static int getYear(Date date) {
		final Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.YEAR);
	}

	public static Integer getAge(Date birthday) {
		if (birthday == null) {
			return null;
		}
		final Date now = getNowDate();
		return getYear(now) - getYear(birthday);
	}

	public static Date getDateBefore(Integer duration) {
		if (duration == null || duration < 0) {
			return null;
		}
		try {
			final Date now = parseDate(dateTime(), "yyyyMMdd");
			return addDays(now, -duration);
		} catch (final ParseException e) {
		}
		return null;
	}

	public static boolean isBeforeToday(Date date) {
		if (date == null) {
			return false;
		}
		final Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		final Date today = c.getTime();
		return date.before(today);
	}

	public static boolean isBeforeYesterday(Date date) {
		if (date == null) {
			return false;
		}
		return date.before(getLastDayStart());
	}

	public static Date toDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	public static Date toDate(LocalDate localDate) {
		return toDate(localDate.atStartOfDay());
	}

	public static Date getLastDayStart() {
		return toDate(LocalDate.now().atStartOfDay().minusDays(1));
	}

	public static Date getLastDayEnd() {
		return getLastDayEnd(1);
	}

	public static Date getLastDayEnd(int minusSeconds) {
		return toDate(LocalDate.now().atStartOfDay().minusSeconds(minusSeconds));
	}

	public static Date getTodayStart() {
		return toDate(LocalDate.now().atStartOfDay());
	}

	public static boolean isDuringToday(Date date) {
		if (date == null) {
			return false;
		}
		final Date start = toDate(LocalDate.now().atStartOfDay());
		if (start.equals(date)) {
			return true;
		}
		return date.after(start) && date.before(getNowDate());
	}

	public static boolean isDuringYesterday(Date date) {
		if (date == null) {
			return false;
		}
		return date.before(getLastDayEnd()) && date.after(getLastDayStart());
	}

	public static boolean isDuring5Minutes(Date createTime) {
		if (createTime == null) {
			return false;
		}
		final Date fiveMinutesAgo = toDate(LocalDateTime.now().minusMinutes(5));
		return createTime.after(fiveMinutesAgo);
	}

	public static boolean isBefore1Minute(Date createTime) {
		if (createTime == null) {
			return false;
		}
		return createTime.before(toDate(LocalDateTime.now().minusMinutes(1)));
	}

	public static Date getWeekStart(int before) {
		final LocalDate now = LocalDate.now().minusWeeks(before);
		return toDate(now.minusDays(now.getDayOfWeek().getValue() - 1).atStartOfDay());
	}

	public static Date getMonthStart(int before) {
		final LocalDate now = LocalDate.now().minusMonths(before);
		return toDate(now.plusDays(1 - now.getDayOfMonth()).atStartOfDay());
	}

	public static List<Date> getDates(Date from, Date end) {
		if (from == null || end == null || from.after(end)) {
			return Collections.emptyList();
		}
		final List<Date> list = new ArrayList<Date>(); // 保存日期集合
		Date date = from;
		final Calendar cd = Calendar.getInstance();// 用Calendar 进行日期比较判断
		while (date.getTime() <= end.getTime()) {
			list.add(date);
			cd.setTime(date);
			cd.add(Calendar.DATE, 1);// 增加一天 放入集合
			date = cd.getTime();
		}
		return list;
	}

	public static LocalDateTime localDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static LocalDate localDate(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).toLocalDate();
	}

	public static Date getStartOf(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDateTime(date).toLocalDate().atStartOfDay());
	}

	public static Date getEndOf(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDateTime(date).toLocalDate().atTime(23, 59, 59));
	}

	public static Integer getMonth(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).getMonthValue();
	}

	public static Date getMonthDate(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDate(date));
	}

	public static String getDisplayWeek(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
	}

	public static ZonedDateTime zonedDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return ZonedDateTime.of(localDateTime(date), ZoneId.systemDefault());
	}

	public static List<Date> getMonths(Date start, Date end) {
		if (start == null || end == null || start.after(end)) {
			return Collections.emptyList();
		}

		LocalDate startDate = localDate(getStartOf(start));
		final LocalDate endDate = localDate(getEndOf(end));
		final List<Date> months = new ArrayList<>();
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			months.add(toDate(startDate));
			startDate = startDate.plusMonths(1);
		}
		return months;
	}

	public static Date getMonthStartOf(Date month) {
		if (month == null) {
			return null;
		}
		final LocalDateTime date = localDateTime(month);
		return getStartOf(toDate(date.minusDays(date.getDayOfMonth() - 1)));
	}

	public static Date getMonthEndOf(Date month) {
		if (month == null) {
			return null;
		}
		final LocalDate date = localDate(month);
		return getEndOf(toDate(date.withDayOfMonth(date.lengthOfMonth())));
	}

	public static Integer getLengthOfMonth(Date month) {
		if (month == null) {
			return null;
		}

		final LocalDate localDate = localDate(month);
		return localDate.lengthOfMonth();
	}

	public static Integer getDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}

		final LocalDate localDate = localDate(date);
		return localDate.getDayOfMonth();
	}

	public static int getRemainDaysOfMonth(Date date) {
		final LocalDate monthEnd = localDate(getMonthEndOf(date));
		final LocalDate now = localDate(date);
		return monthEnd.getDayOfMonth() - now.getDayOfMonth() + 1;
	}

	public static boolean monthEquals(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		final LocalDate ld1 = localDate(d1);
		final LocalDate ld2 = localDate(d2);
		return ld1.getYear() == ld2.getYear() && ld1.getMonthValue() == ld2.getMonthValue();
	}

	public static boolean dayEquals(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		final LocalDate ld1 = localDate(d1);
		final LocalDate ld2 = localDate(d2);
		return ld1.getYear() == ld2.getYear() && ld1.getMonthValue() == ld2.getMonthValue()
				&& ld1.getDayOfMonth() == ld2.getDayOfMonth();
	}

	public static String getNowDateStr() {
		return toDefault(getNowDate());
	}

	public static boolean isDuring(Date time, Date start, Date end) {
		if (time == null) {
			return false;
		}
		if (start != null && time.before(start)) {
			return false;
		}
		if (end != null && time.after(end)) {
			return false;
		}
		return true;
	}

	public static boolean isWeekend(Date date) {
		if (date == null) {
			return false;
		}
		final LocalDateTime time = localDateTime(date);
		final DayOfWeek dayOfWeek = time.getDayOfWeek();
		return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
	}

	public static Date getMidOfDate(Date date) {
		if (date == null) {
			return null;
		}
		final LocalDateTime localDateTime = localDateTime(date);
		return toDate(localDateTime.withHour(12).withMinute(0).withSecond(0).withNano(0));
	}

	public static Date fixHourOfDate(Date date, int hour) {
		final LocalDateTime localDateTime = localDateTime(date);
		return toDate(localDateTime.withHour(hour).withMinute(0).withSecond(0).withNano(0));
	}

	public static Integer countWeeks(Date start, Date end) {
		if (start == null || end == null || end.before(start)) {
			return 0;
		}
		int weeks = 1;
		Date date = addWeeks(start, 1);
		while (date.before(end)) {
			date = addWeeks(date, 1);
			weeks++;
		}
		return weeks;
	}

	public static Date tryParse(String date, String... formats) {
		try {
			return parseDate(date, formats);
		} catch (Exception e) {
			return null;
		}
	}
}
