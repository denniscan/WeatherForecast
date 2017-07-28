package can.dennis.weatherforecast.utils.constants;

import java.text.SimpleDateFormat;

import can.dennis.weatherforecast.BuildConfig;
/**
 * 常量
 * Created by Dennis Can on 2017-06-29.
 */
public class Constants {
	public static final String PREFERENCE_FILE_NAME = "datas";
	private static final long FIVE_MINUTES_MILLIS = 1000 * 60 * 10;
	public static final long AUTO_REFRESH_INTERVAL = FIVE_MINUTES_MILLIS;

	public static final String OPEN_WEATHER_CURRENT_URL_HEAD =
			"http://api.openweathermap.org/data/2.5/weather?units=metric&lang=zh_cn";
	public static final String OPEN_WEATHER_FORECAST_URL_HEAD =
			"http://api.openweathermap.org/data/2.5/forecast?units=metric&lang=zh_cn";
	public static final String OPEN_WEATHER_ID = BuildConfig.OPEN_WEATHER_ID;
	public static final String OPEN_WEATHER_APP_ID = BuildConfig.OPEN_WEATHER_APP_ID;

	public static final String URL_BING_PIC_GUO_LIN = "http://guolin.tech/api/bing_pic";
	public static final String URL_BING_PIC_DU_JIN = "https://www.dujin.org/sys/bing/1366.php";
	public static final String URL_BING_PIC = URL_BING_PIC_GUO_LIN;
	public static final String BING_CACHE_IMAGE_FILE_NAME = "bing.jpg";

	public static final SimpleDateFormat SHOW_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat MONTH_DAY_DATE_FORMATTER = new SimpleDateFormat("MM-dd");
	public static final SimpleDateFormat HOUR_MINUTE_SECOND_DATE_FORMATTER = new SimpleDateFormat("HH:mm:ss");
}
