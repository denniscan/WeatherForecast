package can.dennis.weatherforecast.itface.element;

/**
 * ForecastBean数据接口
 * Created by Dennis Can on 2017-06-30.
 */
public interface IForecastBean {
	float get_temperatureFloat();

	float get_rainFloat();

	long get_dateTimeValue();
}
