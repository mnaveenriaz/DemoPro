package app.utility;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {
	private static final String				DATETIME_PATTERN	= "yyyy/MM/dd HH:mm:ss";
	private static final DateTimeFormatter	DATETIME_FORMATTER	= DateTimeFormat.forPattern(DATETIME_PATTERN);

	private static final String				DATE_PATTERN	= "yyyyMMdd";
	private static final DateTimeFormatter	DATE_FORMATTER	= DateTimeFormat.forPattern(DATE_PATTERN);

	private static final String				DATESTR_PATTERN		= "MM/dd";
	private static final DateTimeFormatter	DATESTR_FORMATTER	= DateTimeFormat.forPattern(DATESTR_PATTERN);

	public static String format(DateTime date) {
		if (date == null) return null;
		else return DATETIME_FORMATTER.print(date);

	}

	public static DateTime parse(String dateString) {
		return DATETIME_FORMATTER.parseDateTime(dateString);
	}

	public static boolean validDate(String dateString) {

		return DateUtil.parse(dateString) != null;
	}

	public static String formatOdateString(String date) {
		if (date == null) return null;
		else return DATESTR_FORMATTER.print(DATE_FORMATTER.parseDateTime(date));
	}

	public static String formatOdate(DateTime date) {
		if (date == null) return null;
		else return DATESTR_FORMATTER.print(date);

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
