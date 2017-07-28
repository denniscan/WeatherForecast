package can.dennis.weatherforecast.utils.calendarutils;

import java.util.Calendar;
import java.util.Date;
/**
 * Calendar utils
 * Created by Dennis Can on 2017-07-03.
 */
public class CalendarUtils {
	private static CalendarUtils instance = new CalendarUtils();

	public static CalendarUtils getInstance() { return instance; }

	private CalendarUtils() { }

	public Calendar getTodayZero() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar;
	}

	public Calendar getCalendar(long timeMillis) { return getCalendar(new Date(timeMillis)); }

	public Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public boolean isZero(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0 &&
		       calendar.get(Calendar.SECOND) == 0 && calendar.get(Calendar.MILLISECOND) == 0;
	}
}
