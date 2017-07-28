package can.dennis.weatherforecast.bean.openweather.element;

import com.google.gson.annotations.SerializedName;

/**
 * 雪量Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class SnowBean {
	/**
	 * 3h : 3 (3小时记录 mm)
	 */

	@SerializedName("3h")
	private String _$3h;

	public String get_$3h() { return _$3h;}

	public void set_$3h(String _$3h) { this._$3h = _$3h;}
}