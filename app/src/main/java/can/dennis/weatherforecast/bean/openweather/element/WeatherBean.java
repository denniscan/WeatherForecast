package can.dennis.weatherforecast.bean.openweather.element;

import can.dennis.weatherforecast.itface.element.IWeatherBean;
/**
 * 天气描述Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class WeatherBean implements IWeatherBean {
	/**
	 * id : 803
	 * main : Clouds
	 * description : broken clouds
	 * icon : 04n
	 */

	private String id;
	private String main;
	private String description;
	private String icon;

	public String getId() { return id;}

	public void setId(String id) { this.id = id;}

	public String getMain() { return main;}

	public void setMain(String main) { this.main = main;}

	public String getDescription() { return description;}

	public void setDescription(String description) { this.description = description;}

	public String getIcon() { return icon;}

	public void setIcon(String icon) { this.icon = icon;}

	// <-------- interface implements -------->

	@Override public String get_weatherDescription() { return getDescription(); }
}