package can.dennis.weatherforecast.bean.openweather.element;

import can.dennis.weatherforecast.itface.element.IMainBean;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * 天气主要信息Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class MainBean implements IMainBean {
	/**
	 * temp : 293.25
	 * pressure : 1019 (气压 hPa)
	 * humidity : 83 (湿度 %)
	 * temp_min : 289.82
	 * temp_max : 295.37
	 * sea_level : 1019 (海平面气压 hPa)
	 * grnd_level : 1019 (地面气压 hPa)
	 * temp_kf : 1.49
	 */

	private String temp;
	private String pressure;
	private String humidity;
	private String temp_min;
	private String temp_max;
	private String sea_level;
	private String grnd_level;
	private String temp_kf;

	public String getTemp() { return temp;}

	public void setTemp(String temp) { this.temp = temp;}

	public String getPressure() { return pressure;}

	public void setPressure(String pressure) { this.pressure = pressure;}

	public String getHumidity() { return humidity;}

	public void setHumidity(String humidity) { this.humidity = humidity;}

	public String getTemp_min() { return temp_min;}

	public void setTemp_min(String temp_min) { this.temp_min = temp_min;}

	public String getTemp_max() { return temp_max;}

	public void setTemp_max(String temp_max) { this.temp_max = temp_max;}

	public String getSea_level() { return sea_level;}

	public void setSea_level(String sea_level) { this.sea_level = sea_level;}

	public String getGrnd_level() { return grnd_level;}

	public void setGrnd_level(String grnd_level) { this.grnd_level = grnd_level;}

	public String getTemp_kf() { return temp_kf; }

	public void setTemp_kf(String temp_kf) { this.temp_kf = temp_kf; }

	// <-------- interface implements -------->

	@Override public String get_currentTemperatureString() { return getTemp(); }

	@Override public float get_currentTemperatureFloat() {
		return ResponseParseUtils.getInstance().parseFloat(getTemp());
	}

	@Override public String get_maxTemperatureString() { return getTemp_max(); }

	@Override public String get_minTemperatureString() { return getTemp_min(); }

	@Override public String get_pressureString() { return getPressure(); }

	@Override public String get_humidityString() { return getHumidity(); }
}