package app.utility;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author mnaveenriaz
 *
 */
public class DateUtil {
	private static final String				DATETIME_PATTERN	= "yyyy/MM/dd HH:mm:ss";
	private static final DateTimeFormatter	DATETIME_FORMATTER	= DateTimeFormat.forPattern(DATETIME_PATTERN);

	private static final String				DATE_PATTERN	= "yyyyMMdd";
	private static final DateTimeFormatter	DATE_FORMATTER	= DateTimeFormat.forPattern(DATE_PATTERN);

	private static final String				DATESTR_PATTERN		= "MM/dd";
	private static final DateTimeFormatter	DATESTR_FORMATTER	= DateTimeFormat.forPattern(DATESTR_PATTERN);

	public static String format(DateTime date) {
		return date == null ? DATETIME_FORMATTER.print(date) : null;
	}

	public static DateTime parse(String dateString) {
		return DATETIME_FORMATTER.parseDateTime(dateString);
	}

	public static boolean validDate(String dateString) {
		return DateUtil.parse(dateString) != null;
	}

	public static String formatOdateString(String date) {
		return date == null ? DATESTR_FORMATTER.print(DATE_FORMATTER.parseDateTime(date)) : null;
	}

	public static String formatOdate(DateTime date) {
		return date == null ? DATESTR_FORMATTER.print(date) : null;
	}

	public static DateTime odateParse(String dateString) {
		return DATE_FORMATTER.parseDateTime(dateString);
	}

	public static boolean validOdate(String dateString) {
		return DateUtil.odateParse(dateString) != null;
	}

	public static String getCurrentOdate() {
		return DATETIME_FORMATTER.print(DateTime.now());
	}
}
