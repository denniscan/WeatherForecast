package can.dennis.weatherforecast.itface;

/**
 * OpenWeather current interface
 * Created by Dennis Can on 2017-06-29.
 */
public interface IOpenWeatherCurrent {
	String get_cityName();

	String get_currentTemperatureString();

	String get_weatherDescription();

	String get_maxTemperatureString();

	String get_minTemperatureString();

	String get_pressureString();

	String get_humidityString();

	String get_visibilityString();

	String get_windSpeedString();

	String get_windDirectionString();

	String get_sunriseString();

	String get_sunsetString();
}
