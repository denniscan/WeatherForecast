package can.dennis.weatherforecast.bean.openweather.element;

/**
 * 城市坐标Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class CoordBean {
	/**
	 * lon : 145.77 (经度)
	 * lat : -16.92 (纬度)
	 */
	/** 经度 */
	private String lon;
	/** 纬度 */
	private String lat;

	public String getLon() { return lon;}

	public void setLon(String lon) { this.lon = lon;}

	public String getLat() { return lat;}

	public void setLat(String lat) { this.lat = lat;}
}