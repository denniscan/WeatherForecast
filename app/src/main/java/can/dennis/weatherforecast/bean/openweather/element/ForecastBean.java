package can.dennis.weatherforecast.bean.openweather.element;

import java.util.List;

import can.dennis.weatherforecast.itface.element.IForecastBean;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * ForecastBean
 * Created by Dennis Can on 2017-06-30.
 */
public class ForecastBean implements IForecastBean {

	/**
	 * dt : 1498813200
	 * main : {"temp":32.78,"temp_min":31.29,"temp_max":32.78,"pressure":1010.41,"sea_level":1019.18,"grnd_level":1010.41,"humidity":65,"temp_kf":1.49}
	 * weather : [{"id":801,"main":"Clouds","description":"晴，少云","icon":"02d"}]
	 * clouds : {"all":24}
	 * wind : {"speed":4.57,"deg":170.003}
	 * rain : {"3h":12}
	 * snow : {"3h":12}
	 * sys : {"pod":"d"}
	 * dt_txt : 2017-06-30 09:00:00
	 */

	/** Time of data forecasted, unix, UTC */
	private String dt;
	private MainBean main;
	private CloudsBean clouds;
	private WindBean wind;
	private RainBean rain;
	private SnowBean snow;
	private SysBean sys;
	private String dt_txt;
	private List<WeatherBean> weather;

	public String getDt() { return dt;}

	public void setDt(String dt) { this.dt = dt;}

	public MainBean getMain() { return main;}

	public void setMain(MainBean main) { this.main = main;}

	public CloudsBean getClouds() { return clouds;}

	public void setClouds(CloudsBean clouds) { this.clouds = clouds;}

	public WindBean getWind() { return wind;}

	public void setWind(WindBean wind) { this.wind = wind;}

	public RainBean getRain() { return rain;}

	public void setRain(RainBean rain) { this.rain = rain;}

	public SnowBean getSnow() { return snow;}

	public void setSnow(SnowBean snow) { this.snow = snow;}

	public SysBean getSys() { return sys;}

	public void setSys(SysBean sys) { this.sys = sys;}

	public String getDt_txt() { return dt_txt;}

	public void setDt_txt(String dt_txt) { this.dt_txt = dt_txt;}

	public List<WeatherBean> getWeather() { return weather;}

	public void setWeather(List<WeatherBean> weather) { this.weather = weather;}

	// <-------- interface implements -------->

	@Override public float get_temperatureFloat() {
		MainBean mainBean = getMain();
		if (mainBean == null)
			return ResponseParseUtils.ERROR_FLOAT;
		return mainBean.get_currentTemperatureFloat();
	}

	@Override public float get_rainFloat() {
		RainBean rainBean = getRain();
		if (rainBean == null) return ResponseParseUtils.ERROR_FLOAT;
		return rainBean.get_rainFloat();
	}

	@Override public long get_dateTimeValue() { return ResponseParseUtils.getInstance().parseLong(getDt()); }
}
