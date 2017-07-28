package can.dennis.weatherforecast.bean.openweather.element;

import can.dennis.weatherforecast.itface.element.IWindBean;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * 风力信息Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class WindBean implements IWindBean {
	/**
	 * speed : 5.1
	 * deg : 150 (角度)
	 */

	private String speed;
	private String deg;

	public String getSpeed() { return speed;}

	public void setSpeed(String speed) { this.speed = speed;}

	public String getDeg() { return deg;}

	public void setDeg(String deg) { this.deg = deg;}

	// <-------- interface implements -------->

	@Override public String get_windSpeedString() { return getSpeed(); }

	@Override public String get_windDirectionString() {
		int degree = ResponseParseUtils.getInstance().parseInt(getDeg());
		if (degree == ResponseParseUtils.ERROR_INT)
			return null;
		return parseWindDirectionString(degree);
	}

	private String parseWindDirectionString(int degree) {
		if (degree < 0 || degree >= 360)
			return null;
		/*
			22.5
			67.5
			112.5
			157.5
			202.5
			247.5
			292.5
			337.5
		* */
		if (degree >= 22.5f && degree < 67.5)
			return "东北风";
		if (degree >= 67.5 && degree < 112.5)
			return "东风";
		if (degree >= 112.5 && degree < 157.5)
			return "东南风";
		if (degree >= 157.5 && degree < 202.5)
			return "南风";
		if (degree >= 202.5 && degree < 247.5)
			return "西南风";
		if (degree >= 247.5 && degree < 292.5)
			return "西风";
		if (degree >= 292.5 && degree < 337.5)
			return "西北风";
		// degree >= 337.5f || degree < 22.5f
		return "北风";
	}
}