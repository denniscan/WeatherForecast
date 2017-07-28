package can.dennis.weatherforecast.utils.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import can.dennis.weatherforecast.utils.app.MyApp;
import can.dennis.weatherforecast.utils.constants.Constants;
/**
 * Preference Utils
 * Created by Dennis Can on 2017-06-29.
 */
public class PreferenceUtils {
	private static final String TAG_LAST_CHECK_TIME = "last_check_time";
	private static final String TAG_OPEN_WEATHER_CURRENT_JSON = "open_weather_current_json";
	private static final String TAG_OPEN_WEATHER_FORECAST_JSON = "open_weather_forecast_json";
	private static final String TAG_BING_REFRESH_TIME = "bing_refresh_time";

	private static PreferenceUtils instance = new PreferenceUtils();

	public static PreferenceUtils getInstance() { return instance; }

	private final SharedPreferences sharedPreferences;

	private PreferenceUtils() {
		sharedPreferences =
				MyApp.getContext().getSharedPreferences(Constants.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
	}

	private void markTime(String key) {setLong(key, System.currentTimeMillis());}

	private String getString(@NonNull String key, String defaultValue) {
		return sharedPreferences.getString(key, defaultValue);
	}

	private void setString(@NonNull String key, String value) {
		sharedPreferences.edit().putString(key, value).apply();
	}

	private long getLong(@NonNull String key, int defaultValue) { return sharedPreferences.getLong(key, defaultValue); }

	private void setLong(@NonNull String key, long value) { sharedPreferences.edit().putLong(key, value).apply(); }

	public long getLastCheckTime() { return getLong(TAG_LAST_CHECK_TIME, 0); }

	public void markWeatherCheckTime() { markTime(TAG_LAST_CHECK_TIME); }

	@Nullable public String getLastOpenWeatherCurrentJson() { return getString(TAG_OPEN_WEATHER_CURRENT_JSON, null); }

	public void markLastOpenWeatherCurrentJson(String jsonString) {
		setString(TAG_OPEN_WEATHER_CURRENT_JSON, jsonString);
	}

	@Nullable public String getLastOpenWeatherForecastJson() { return getString(TAG_OPEN_WEATHER_FORECAST_JSON, null); }

	public void markLastOpenWeatherForecastJson(String jsonString) {
		setString(TAG_OPEN_WEATHER_FORECAST_JSON, jsonString);
	}

	public long getBingImageRefreshTime() { return getLong(TAG_BING_REFRESH_TIME, 0); }

	public void markBingImageRefreshTime() { markTime(TAG_BING_REFRESH_TIME); }
}
