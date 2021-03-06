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
 * The Class DateUtils.
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	/** The yyyy. */
	public static String YYYY = "yyyy";

	/** The yyyy mm. */
	public static String YYYY_MM = "yyyy-MM";

	/** The yyyy mm dd. */
	public static String YYYY_MM_DD = "yyyy-MM-dd";

	/** The yyyymmddhhmmss. */
	public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/** The yyyy mm dd hh mm ss. */
	public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/** The default date format. */
	public static String DEFAULT_DATE_FORMAT = YYYY_MM_DD_HH_MM_SS;

	/** The parse patterns. */
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM" };

	/**
	 * Gets the now date.
	 *
	 * @return the now date
	 */
	public static Date getNowDate() {
		return new Date();
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public static String getDate() {
		return dateTimeNow(YYYY_MM_DD);
	}

	/**
	 * Gets the time.
	 *
	 * @return the time
	 */
	public static final String getTime() {
		return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * Date time now.
	 *
	 * @return the string
	 */
	public static final String dateTimeNow() {
		return dateTimeNow(YYYYMMDDHHMMSS);
	}

	/**
	 * Date time now.
	 *
	 * @param format the format
	 * @return the string
	 */
	public static final String dateTimeNow(final String format) {
		return parseDateToStr(format, new Date());
	}

	/**
	 * Date time.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static final String dateTime(final Date date) {
		return parseDateToStr(YYYY_MM_DD, date);
	}

	/**
	 * Parses the date to str.
	 *
	 * @param format the format
	 * @param date   the date
	 * @return the string
	 */
	public static final String parseDateToStr(final String format, final Date date) {
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * To default.
	 *
	 * @param date the date
	 * @return the string
	 */
	public static final String toDefault(final Date date) {
		if (date == null) {
			return "";
		}
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
	}

	/**
	 * Date time.
	 *
	 * @param format the format
	 * @param ts     the ts
	 * @return the date
	 */
	public static final Date dateTime(final String format, final String ts) {
		try {
			return new SimpleDateFormat(format).parse(ts);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Default date.
	 *
	 * @param ts the ts
	 * @return the date
	 */
	public static final Date defaultDate(final String ts) {
		try {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(ts);
		} catch (final ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Date path.
	 *
	 * @return the string
	 */
	public static final String datePath() {
		final Date now = new Date();
		return DateFormatUtils.format(now, "yyyy/MM/dd");
	}

	/**
	 * Date time.
	 *
	 * @return the string
	 */
	public static final String dateTime() {
		final Date now = new Date();
		return DateFormatUtils.format(now, "yyyyMMdd");
	}

	/**
	 * Parses the date.
	 *
	 * @param str the str
	 * @return the date
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
	 * Gets the server start date.
	 *
	 * @return the server start date
	 */
	public static Date getServerStartDate() {
		final long time = ManagementFactory.getRuntimeMXBean().getStartTime();
		return new Date(time);
	}

	/**
	 * Different days by millisecond.
	 *
	 * @param date1 the date 1
	 * @param date2 the date 2
	 * @return the int
	 */
	public static int differentDaysByMillisecond(Date date1, Date date2) {
		return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
	}

	/**
	 * Gets the date poor.
	 *
	 * @param endDate the end date
	 * @param nowDate the now date
	 * @return the date poor
	 */
	public static String getDatePoor(Date endDate, Date nowDate) {
		final long nd = 1000 * 24 * 60 * 60;
		final long nh = 1000 * 60 * 60;
		final long nm = 1000 * 60;
		// long ns = 1000;
		// ???????????????????????????????????????
		final long diff = endDate.getTime() - nowDate.getTime();
		// ??????????????????
		final long day = diff / nd;
		// ?????????????????????
		final long hour = diff % nd / nh;
		// ?????????????????????
		final long min = diff % nd % nh / nm;
		// ??????????????????//????????????
		// long sec = diff % nd % nh % nm / ns;
		return day + "???" + hour + "??????" + min + "??????";
	}

	/**
	 * Gets the interval seconds.
	 *
	 * @param start the start
	 * @param end   the end
	 * @return the interval seconds
	 */
	public static Long getIntervalSeconds(Date start, Date end) {
		if (start == null || end == null || start.after(end)) {
			return null;
		}
		final long diff = end.getTime() - start.getTime();
		return diff / 1000;
	}

	/**
	 * Gets the year.
	 *
	 * @param date the date
	 * @return the year
	 */
	public static int getYear(Date date) {
		final Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.YEAR);
	}

	/**
	 * Gets the age.
	 *
	 * @param birthday the birthday
	 * @return the age
	 */
	public static Integer getAge(Date birthday) {
		if (birthday == null) {
			return null;
		}
		final Date now = getNowDate();
		return getYear(now) - getYear(birthday);
	}

	/**
	 * Gets the date before.
	 *
	 * @param duration the duration
	 * @return the date before
	 */
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

	/**
	 * Checks if is before today.
	 *
	 * @param date the date
	 * @return true, if is before today
	 */
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

	/**
	 * Checks if is before yesterday.
	 *
	 * @param date the date
	 * @return true, if is before yesterday
	 */
	public static boolean isBeforeYesterday(Date date) {
		if (date == null) {
			return false;
		}
		return date.before(getLastDayStart());
	}

	/**
	 * To date.
	 *
	 * @param localDateTime the local date time
	 * @return the date
	 */
	public static Date toDate(LocalDateTime localDateTime) {
		if (localDateTime == null) {
			return null;
		}
		final Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	/**
	 * To date.
	 *
	 * @param localDate the local date
	 * @return the date
	 */
	public static Date toDate(LocalDate localDate) {
		return toDate(localDate.atStartOfDay());
	}

	/**
	 * Gets the last day start.
	 *
	 * @return the last day start
	 */
	public static Date getLastDayStart() {
		return toDate(LocalDate.now().atStartOfDay().minusDays(1));
	}

	/**
	 * Gets the last day end.
	 *
	 * @return the last day end
	 */
	public static Date getLastDayEnd() {
		return getLastDayEnd(1);
	}

	/**
	 * Gets the last day end.
	 *
	 * @param minusSeconds the minus seconds
	 * @return the last day end
	 */
	public static Date getLastDayEnd(int minusSeconds) {
		return toDate(LocalDate.now().atStartOfDay().minusSeconds(minusSeconds));
	}

	/**
	 * Gets the today start.
	 *
	 * @return the today start
	 */
	public static Date getTodayStart() {
		return toDate(LocalDate.now().atStartOfDay());
	}

	/**
	 * Checks if is during today.
	 *
	 * @param date the date
	 * @return true, if is during today
	 */
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

	/**
	 * Checks if is during yesterday.
	 *
	 * @param date the date
	 * @return true, if is during yesterday
	 */
	public static boolean isDuringYesterday(Date date) {
		if (date == null) {
			return false;
		}
		return date.before(getLastDayEnd()) && date.after(getLastDayStart());
	}

	/**
	 * Checks if is during 5 minutes.
	 *
	 * @param createTime the create time
	 * @return true, if is during 5 minutes
	 */
	public static boolean isDuring5Minutes(Date createTime) {
		if (createTime == null) {
			return false;
		}
		final Date fiveMinutesAgo = toDate(LocalDateTime.now().minusMinutes(5));
		return createTime.after(fiveMinutesAgo);
	}

	/**
	 * Checks if is before 1 minute.
	 *
	 * @param createTime the create time
	 * @return true, if is before 1 minute
	 */
	public static boolean isBefore1Minute(Date createTime) {
		if (createTime == null) {
			return false;
		}
		return createTime.before(toDate(LocalDateTime.now().minusMinutes(1)));
	}

	/**
	 * Gets the week start.
	 *
	 * @param before the before
	 * @return the week start
	 */
	public static Date getWeekStart(int before) {
		final LocalDate now = LocalDate.now().minusWeeks(before);
		return toDate(now.minusDays(now.getDayOfWeek().getValue() - 1).atStartOfDay());
	}

	/**
	 * Gets the month start.
	 *
	 * @param before the before
	 * @return the month start
	 */
	public static Date getMonthStart(int before) {
		final LocalDate now = LocalDate.now().minusMonths(before);
		return toDate(now.plusDays(1 - now.getDayOfMonth()).atStartOfDay());
	}

	/**
	 * Gets the dates.
	 *
	 * @param from the from
	 * @param end  the end
	 * @return the dates
	 */
	public static List<Date> getDates(Date from, Date end) {
		if (from == null || end == null || from.after(end)) {
			return Collections.emptyList();
		}
		final List<Date> list = new ArrayList<Date>(); // ??????????????????
		Date date = from;
		final Calendar cd = Calendar.getInstance();// ???Calendar ????????????????????????
		while (date.getTime() <= end.getTime()) {
			list.add(date);
			cd.setTime(date);
			cd.add(Calendar.DATE, 1);// ???????????? ????????????
			date = cd.getTime();
		}
		return list;
	}

	/**
	 * Local date time.
	 *
	 * @param date the date
	 * @return the local date time
	 */
	public static LocalDateTime localDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Local date.
	 *
	 * @param date the date
	 * @return the local date
	 */
	public static LocalDate localDate(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).toLocalDate();
	}

	/**
	 * Gets the start of.
	 *
	 * @param date the date
	 * @return the start of
	 */
	public static Date getStartOf(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDateTime(date).toLocalDate().atStartOfDay());
	}

	/**
	 * Gets the end of.
	 *
	 * @param date the date
	 * @return the end of
	 */
	public static Date getEndOf(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDateTime(date).toLocalDate().atTime(23, 59, 59));
	}

	/**
	 * Gets the month.
	 *
	 * @param date the date
	 * @return the month
	 */
	public static Integer getMonth(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).getMonthValue();
	}

	/**
	 * Gets the month date.
	 *
	 * @param date the date
	 * @return the month date
	 */
	public static Date getMonthDate(Date date) {
		if (date == null) {
			return null;
		}
		return toDate(localDate(date));
	}

	/**
	 * Gets the display week.
	 *
	 * @param date the date
	 * @return the display week
	 */
	public static String getDisplayWeek(Date date) {
		if (date == null) {
			return null;
		}
		return localDateTime(date).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.CHINA);
	}

	/**
	 * Zoned date time.
	 *
	 * @param date the date
	 * @return the zoned date time
	 */
	public static ZonedDateTime zonedDateTime(Date date) {
		if (date == null) {
			return null;
		}
		return ZonedDateTime.of(localDateTime(date), ZoneId.systemDefault());
	}

	/**
	 * Gets the months.
	 *
	 * @param start the start
	 * @param end   the end
	 * @return the months
	 */
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

	/**
	 * Gets the month start of.
	 *
	 * @param month the month
	 * @return the month start of
	 */
	public static Date getMonthStartOf(Date month) {
		if (month == null) {
			return null;
		}
		final LocalDateTime date = localDateTime(month);
		return getStartOf(toDate(date.minusDays(date.getDayOfMonth() - 1)));
	}

	/**
	 * Gets the month end of.
	 *
	 * @param month the month
	 * @return the month end of
	 */
	public static Date getMonthEndOf(Date month) {
		if (month == null) {
			return null;
		}
		final LocalDate date = localDate(month);
		return getEndOf(toDate(date.withDayOfMonth(date.lengthOfMonth())));
	}

	/**
	 * Gets the length of month.
	 *
	 * @param month the month
	 * @return the length of month
	 */
	public static Integer getLengthOfMonth(Date month) {
		if (month == null) {
			return null;
		}

		final LocalDate localDate = localDate(month);
		return localDate.lengthOfMonth();
	}

	/**
	 * Gets the day of month.
	 *
	 * @param date the date
	 * @return the day of month
	 */
	public static Integer getDayOfMonth(Date date) {
		if (date == null) {
			return null;
		}

		final LocalDate localDate = localDate(date);
		return localDate.getDayOfMonth();
	}

	/**
	 * Gets the remain days of month.
	 *
	 * @param date the date
	 * @return the remain days of month
	 */
	public static int getRemainDaysOfMonth(Date date) {
		final LocalDate monthEnd = localDate(getMonthEndOf(date));
		final LocalDate now = localDate(date);
		return monthEnd.getDayOfMonth() - now.getDayOfMonth() + 1;
	}

	/**
	 * Month equals.
	 *
	 * @param d1 the d 1
	 * @param d2 the d 2
	 * @return true, if successful
	 */
	public static boolean monthEquals(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		final LocalDate ld1 = localDate(d1);
		final LocalDate ld2 = localDate(d2);
		return ld1.getYear() == ld2.getYear() && ld1.getMonthValue() == ld2.getMonthValue();
	}

	/**
	 * Day equals.
	 *
	 * @param d1 the d 1
	 * @param d2 the d 2
	 * @return true, if successful
	 */
	public static boolean dayEquals(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		final LocalDate ld1 = localDate(d1);
		final LocalDate ld2 = localDate(d2);
		return ld1.getYear() == ld2.getYear() && ld1.getMonthValue() == ld2.getMonthValue()
				&& ld1.getDayOfMonth() == ld2.getDayOfMonth();
	}

	/**
	 * Gets the now date str.
	 *
	 * @return the now date str
	 */
	public static String getNowDateStr() {
		return toDefault(getNowDate());
	}

	/**
	 * Checks if is during.
	 *
	 * @param time  the time
	 * @param start the start
	 * @param end   the end
	 * @return true, if is during
	 */
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

	/**
	 * Checks if is weekend.
	 *
	 * @param date the date
	 * @return true, if is weekend
	 */
	public static boolean isWeekend(Date date) {
		if (date == null) {
			return false;
		}
		final LocalDateTime time = localDateTime(date);
		final DayOfWeek dayOfWeek = time.getDayOfWeek();
		return DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek);
	}

	/**
	 * Gets the mid of date.
	 *
	 * @param date the date
	 * @return the mid of date
	 */
	public static Date getMidOfDate(Date date) {
		if (date == null) {
			return null;
		}
		final LocalDateTime localDateTime = localDateTime(date);
		return toDate(localDateTime.withHour(12).withMinute(0).withSecond(0).withNano(0));
	}

	/**
	 * Fix hour of date.
	 *
	 * @param date the date
	 * @param hour the hour
	 * @return the date
	 */
	public static Date fixHourOfDate(Date date, int hour) {
		final LocalDateTime localDateTime = localDateTime(date);
		return toDate(localDateTime.withHour(hour).withMinute(0).withSecond(0).withNano(0));
	}

	/**
	 * Count weeks.
	 *
	 * @param start the start
	 * @param end   the end
	 * @return the integer
	 */
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

	/**
	 * Try parse.
	 *
	 * @param date    the date
	 * @param formats the formats
	 * @return the date
	 */
	public static Date tryParse(String date, String... formats) {
		try {
			return parseDate(date, formats);
		} catch (Exception e) {
			return null;
		}
	}
}
