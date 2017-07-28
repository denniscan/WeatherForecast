package can.dennis.weatherforecast.bean.openweather;

import java.util.List;

import can.dennis.weatherforecast.bean.openweather.element.CloudsBean;
import can.dennis.weatherforecast.bean.openweather.element.CoordBean;
import can.dennis.weatherforecast.bean.openweather.element.MainBean;
import can.dennis.weatherforecast.bean.openweather.element.RainBean;
import can.dennis.weatherforecast.bean.openweather.element.SnowBean;
import can.dennis.weatherforecast.bean.openweather.element.SysBean;
import can.dennis.weatherforecast.bean.openweather.element.WeatherBean;
import can.dennis.weatherforecast.bean.openweather.element.WindBean;
import can.dennis.weatherforecast.itface.IOpenWeatherCurrent;
/**
 * OpenWeather current json bean
 * Created by Dennis Can on 2017-06-29.
 */
public class OpenWeatherCurrentJsonBean implements IOpenWeatherCurrent {
	/**
	 * coord : {"lon":145.77,"lat":-16.92}
	 * weather : [{"id":803,"main":"Clouds","description":"broken clouds","icon":"04n"}]
	 * base : cmc stations
	 * main : {"temp":293.25,"pressure":1019,"humidity":83,"temp_min":289.82,"temp_max":295.37,"sea_level":1019,"grnd_level":1019}
	 * visibility : 10000
	 * wind : {"speed":5.1,"deg":150}
	 * clouds : {"all":75}
	 * rain : {"3h":3}
	 * snow : {"3h":3}
	 * dt : 1435658272
	 * sys : {"type":1,"id":8166,"message":0.0166,"country":"AU","sunrise":1435610796,"sunset":1435650870}
	 * id : 2172797 (城市ID)
	 * name : Cairns (城市名)
	 * cod : 200
	 */
	private CoordBean coord;
	private String base;
	private MainBean main;
	private String visibility;
	private WindBean wind;
	private CloudsBean clouds;
	private RainBean rain;
	private SnowBean snow;
	private String dt;
	private SysBean sys;
	private String id;
	private String name;
	private String cod;
	private List<WeatherBean> weather;

	public CoordBean getCoord() { return coord;}

	public void setCoord(CoordBean coord) { this.coord = coord;}

	public String getBase() { return base;}

	public void setBase(String base) { this.base = base;}

	public MainBean getMain() { return main;}

	public void setMain(MainBean main) { this.main = main;}

	public String getVisibility() { return visibility;}

	public void setVisibility(String visibility) { this.visibility = visibility;}

	public WindBean getWind() { return wind;}

	public void setWind(WindBean wind) { this.wind = wind;}

	public CloudsBean getClouds() { return clouds;}

	public void setClouds(CloudsBean clouds) { this.clouds = clouds;}

	public RainBean getRain() { return rain;}

	public void setRain(RainBean rain) { this.rain = rain;}

	public SnowBean getSnow() { return snow;}

	public void setSnow(SnowBean snow) { this.snow = snow;}

	public String getDt() { return dt;}

	public void setDt(String dt) { this.dt = dt;}

	public SysBean getSys() { return sys;}

	public void setSys(SysBean sys) { this.sys = sys;}

	public String getId() { return id;}

	public void setId(String id) { this.id = id;}

	public String getName() { return name;}

	public void setName(String name) { this.name = name;}

	public String getCod() { return cod;}

	public void setCod(String cod) { this.cod = cod;}

	public List<WeatherBean> getWeather() { return weather;}

	public void setWeather(List<WeatherBean> weather) { this.weather = weather;}

	// <-------- interface implements -------->

	@Override public String get_cityName() { return getName(); }

	@Override public String get_currentTemperatureString() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return null;
		return mainBean.get_currentTemperatureString();
	}

	@Override public String get_weatherDescription() {
		List<WeatherBean> weatherBeanList = getWeather();
		WeatherBean weatherBean;
		if (weatherBeanList == null || weatherBeanList.size() == 0 || (weatherBean = weatherBeanList.get(0)) == null)
			return null;

		return weatherBean.get_weatherDescription();
	}

	@Override public String get_maxTemperatureString() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return null;
		return mainBean.get_maxTemperatureString();
	}

	@Override public String get_minTemperatureString() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return null;
		return mainBean.get_minTemperatureString();
	}

	@Override public String get_pressureString() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return null;
		return mainBean.get_pressureString();
	}

	@Override public String get_humidityString() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return null;
		return mainBean.get_humidityString();
	}

	@Override public String get_visibilityString() { return getVisibility(); }

	@Override public String get_windSpeedString() {
		WindBean windBean = getWind();
		if(windBean==null)return null;
		return windBean.get_windSpeedString();
	}

	@Override public String get_windDirectionString() {
		WindBean windBean = getWind();
		if(windBean==null)return null;
		return windBean.get_windDirectionString();
	}

	@Override public String get_sunriseString() {
		SysBean sysBean = getSys();
		if(sysBean==null)return "";
		return sysBean.get_sunriseString();
	}

	@Override public String get_sunsetString() {
		SysBean sysBean = getSys();
		if(sysBean==null)return "";
		return sysBean.get_sunsetString();
	}
}
