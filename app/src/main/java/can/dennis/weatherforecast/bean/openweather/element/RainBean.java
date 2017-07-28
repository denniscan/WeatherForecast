package can.dennis.weatherforecast.bean.openweather.element;

import com.google.gson.annotations.SerializedName;

import can.dennis.weatherforecast.itface.element.IRainBean;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * 雨量Bean
 * Created by Dennis Can on 2017-06-30.
 */
public class RainBean implements IRainBean {
	/**
	 * 3h : 3 (3小时记录 mm)
	 */

	@SerializedName("3h")
	private String _$3h;

	public String get_$3h() { return _$3h;}

	public void set_$3h(String _$3h) { this._$3h = _$3h;}

	// <-------- interface implements -------->

	@Override public float get_rainFloat() { return ResponseParseUtils.getInstance().parseFloat(get_$3h()); }
}