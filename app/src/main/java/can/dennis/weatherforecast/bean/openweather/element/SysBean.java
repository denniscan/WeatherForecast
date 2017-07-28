package can.dennis.weatherforecast.bean.openweather.element;

import java.util.Date;

import can.dennis.weatherforecast.itface.element.ISysBean;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * 系统信息Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class SysBean implements ISysBean {
	/**
	 * type : 1
	 * id : 8166
	 * message : 0.0166
	 * country : AU (Country code GB, JP etc.)
	 * sunrise : 1435610796 (日出时间 unix, UTC）
	 * sunset : 1435650870 (日落时间 unix, UTC)
	 * pod : d
	 */

	private String type;
	private String id;
	private String message;
	private String country;
	private String sunrise;
	private String sunset;
	private String pod;

	public String getType() { return type;}

	public void setType(String type) { this.type = type;}

	public String getId() { return id;}

	public void setId(String id) { this.id = id;}

	public String getMessage() { return message;}

	public void setMessage(String message) { this.message = message;}

	public String getCountry() { return country;}

	public void setCountry(String country) { this.country = country;}

	public String getSunrise() { return sunrise;}

	public void setSunrise(String sunrise) { this.sunrise = sunrise;}

	public String getSunset() { return sunset;}

	public void setSunset(String sunset) { this.sunset = sunset;}

	public String getPod() { return pod;}

	public void setPod(String pod) { this.pod = pod;}

	// <-------- interface implements -------->

	@Override public String get_sunriseString() {
		long sunrise = ResponseParseUtils.getInstance().parseLong(getSunrise());
		if (sunrise == ResponseParseUtils.ERROR_LONG)
			return null;
		return Constants.HOUR_MINUTE_SECOND_DATE_FORMATTER.format(new Date(sunrise * 1000));
	}

	@Override public String get_sunsetString() {
		long sunset = ResponseParseUtils.getInstance().parseLong(getSunset());
		if (sunset == ResponseParseUtils.ERROR_LONG)
			return null;
		return Constants.HOUR_MINUTE_SECOND_DATE_FORMATTER.format(new Date(sunset * 1000));
	}
}