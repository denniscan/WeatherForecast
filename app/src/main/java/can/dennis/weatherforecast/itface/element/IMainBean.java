package can.dennis.weatherforecast.itface.element;

/**
 * 天气主要信息Bean数据接口
 * Created by Dennis Can on 2017-06-30.
 */
public interface IMainBean {
	String get_currentTemperatureString();

	float get_currentTemperatureFloat();

	String get_maxTemperatureString();

	String get_minTemperatureString();

	String get_pressureString();

	String get_humidityString();
}
