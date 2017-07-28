package can.dennis.weatherforecast.itface;

import java.util.List;

import can.dennis.weatherforecast.itface.element.IForecastBean;
/**
 * OpenWeather forecast interface
 * Created by Dennis Can on 2017-06-29.
 */
public interface IOpenWeatherForecast {
	List<? extends IForecastBean> get_forecastBeanList();
}
