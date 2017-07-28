package can.dennis.weatherforecast.bean.openweather;

import java.util.List;

import can.dennis.weatherforecast.bean.openweather.element.CityBean;
import can.dennis.weatherforecast.bean.openweather.element.ForecastBean;
import can.dennis.weatherforecast.itface.element.IForecastBean;
import can.dennis.weatherforecast.itface.IOpenWeatherForecast;
/**
 * OpenWeather forecast json bean
 * Created by Dennis Can on 2017-06-30.
 */
public class OpenWeatherForecastJsonBean implements IOpenWeatherForecast {

	/**
	 * cod : 200
	 * message : 0.0055
	 * cnt : 37
	 * list : [{"dt":1498813200,"main":{"temp":32.78,"temp_min":31.29,"temp_max":32.78,"pressure":1010.41,"sea_level":1019.18,"grnd_level":1010.41,"humidity":65,"temp_kf":1.49},"weather":[{"id":801,"main":"Clouds","description":"晴，少云","icon":"02d"}],"clouds":{"all":24},"wind":{"speed":4.57,"deg":170.003},"rain":{"3h":12},"snow":{"3h":12},"sys":{"pod":"d"},"dt_txt":"2017-06-30 09:00:00"},{"dt":1498824000,"main":{"temp":29.03,"temp_min":27.91,"temp_max":29.03,"pressure":1011.85,"sea_level":1020.55,"grnd_level":1011.85,"humidity":71,"temp_kf":1.12},"weather":[{"id":801,"main":"Clouds","description":"晴，少云","icon":"02n"}],"clouds":{"all":20},"wind":{"speed":4.36,"deg":167.504},"rain":{},"sys":{"pod":"n"},"dt_txt":"2017-06-30 12:00:00"},{"dt":1498834800,"main":{"temp":26.79,"temp_min":26.05,"temp_max":26.79,"pressure":1012.89,"sea_level":1021.62,"grnd_level":1012.89,"humidity":80,"temp_kf":0.75},"weather":[{"id":800,"main":"Clear","description":"晴","icon":"02n"}],"clouds":{"all":8},"wind":{"speed":3.62,"deg":165.502},"rain":{},"sys":{"pod":"n"},"dt_txt":"2017-06-30 15:00:00"},{"dt":1498845600,"main":{"temp":24.72,"temp_min":24.34,"temp_max":24.72,"pressure":1011.87,"sea_level":1020.6,"grnd_level":1011.87,"humidity":91,"temp_kf":0.37},"weather":[{"id":800,"main":"Clear","description":"晴","icon":"01n"}],"clouds":{"all":0},"wind":{"speed":1.86,"deg":159.502},"rain":{},"sys":{"pod":"n"},"dt_txt":"2017-06-30 18:00:00"},{"dt":1498856400,"main":{"temp":24.09,"temp_min":24.09,"temp_max":24.09,"pressure":1010.97,"sea_level":1019.69,"grnd_level":1010.97,"humidity":93,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.65,"deg":144.505},"rain":{"3h":0.02},"sys":{"pod":"n"},"dt_txt":"2017-06-30 21:00:00"},{"dt":1498867200,"main":{"temp":26.21,"temp_min":26.21,"temp_max":26.21,"pressure":1011.91,"sea_level":1020.58,"grnd_level":1011.91,"humidity":92,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":44},"wind":{"speed":2.56,"deg":138.504},"rain":{"3h":0.77},"sys":{"pod":"d"},"dt_txt":"2017-07-01 00:00:00"},{"dt":1498878000,"main":{"temp":27.64,"temp_min":27.64,"temp_max":27.64,"pressure":1011.72,"sea_level":1020.57,"grnd_level":1011.72,"humidity":91,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":76},"wind":{"speed":4.27,"deg":157.5},"rain":{"3h":2.555},"sys":{"pod":"d"},"dt_txt":"2017-07-01 03:00:00"},{"dt":1498888800,"main":{"temp":27.21,"temp_min":27.21,"temp_max":27.21,"pressure":1010.89,"sea_level":1019.58,"grnd_level":1010.89,"humidity":91,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10d"}],"clouds":{"all":80},"wind":{"speed":3.85,"deg":174.517},"rain":{"3h":6.1},"sys":{"pod":"d"},"dt_txt":"2017-07-01 06:00:00"},{"dt":1498899600,"main":{"temp":27.1,"temp_min":27.1,"temp_max":27.1,"pressure":1009.51,"sea_level":1018.26,"grnd_level":1009.51,"humidity":91,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10d"}],"clouds":{"all":68},"wind":{"speed":4.17,"deg":168.003},"rain":{"3h":5.805},"sys":{"pod":"d"},"dt_txt":"2017-07-01 09:00:00"},{"dt":1498910400,"main":{"temp":25.48,"temp_min":25.48,"temp_max":25.48,"pressure":1010.07,"sea_level":1018.88,"grnd_level":1010.07,"humidity":93,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":80},"wind":{"speed":3.26,"deg":167.005},"rain":{"3h":6.97},"sys":{"pod":"n"},"dt_txt":"2017-07-01 12:00:00"},{"dt":1498921200,"main":{"temp":24.82,"temp_min":24.82,"temp_max":24.82,"pressure":1011.6,"sea_level":1020.34,"grnd_level":1011.6,"humidity":95,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":88},"wind":{"speed":2.06,"deg":174.001},"rain":{"3h":8.15},"sys":{"pod":"n"},"dt_txt":"2017-07-01 15:00:00"},{"dt":1498932000,"main":{"temp":23.68,"temp_min":23.68,"temp_max":23.68,"pressure":1010.7,"sea_level":1019.34,"grnd_level":1010.7,"humidity":98,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.87,"deg":128.509},"rain":{"3h":9.92},"sys":{"pod":"n"},"dt_txt":"2017-07-01 18:00:00"},{"dt":1498942800,"main":{"temp":23.45,"temp_min":23.45,"temp_max":23.45,"pressure":1010.54,"sea_level":1019.25,"grnd_level":1010.54,"humidity":98,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.87,"deg":123.506},"rain":{"3h":6.315},"sys":{"pod":"n"},"dt_txt":"2017-07-01 21:00:00"},{"dt":1498953600,"main":{"temp":24.05,"temp_min":24.05,"temp_max":24.05,"pressure":1011.97,"sea_level":1020.75,"grnd_level":1011.97,"humidity":99,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.06,"deg":121.506},"rain":{"3h":10.095},"sys":{"pod":"d"},"dt_txt":"2017-07-02 00:00:00"},{"dt":1498964400,"main":{"temp":25.1,"temp_min":25.1,"temp_max":25.1,"pressure":1012.55,"sea_level":1021.24,"grnd_level":1012.55,"humidity":98,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.41,"deg":127.004},"rain":{"3h":8.685},"sys":{"pod":"d"},"dt_txt":"2017-07-02 03:00:00"},{"dt":1498975200,"main":{"temp":27.78,"temp_min":27.78,"temp_max":27.78,"pressure":1010.39,"sea_level":1019.08,"grnd_level":1010.39,"humidity":89,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":88},"wind":{"speed":4.03,"deg":146.503},"rain":{"3h":1.92},"sys":{"pod":"d"},"dt_txt":"2017-07-02 06:00:00"},{"dt":1498986000,"main":{"temp":28.26,"temp_min":28.26,"temp_max":28.26,"pressure":1009.21,"sea_level":1017.86,"grnd_level":1009.21,"humidity":87,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":4.37,"deg":157.501},"rain":{"3h":0.38},"sys":{"pod":"d"},"dt_txt":"2017-07-02 09:00:00"},{"dt":1498996800,"main":{"temp":25.46,"temp_min":25.46,"temp_max":25.46,"pressure":1010.29,"sea_level":1019.13,"grnd_level":1010.29,"humidity":93,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":88},"wind":{"speed":2.78,"deg":147.01},"rain":{"3h":4.76},"sys":{"pod":"n"},"dt_txt":"2017-07-02 12:00:00"},{"dt":1499007600,"main":{"temp":24.92,"temp_min":24.92,"temp_max":24.92,"pressure":1011.57,"sea_level":1020.25,"grnd_level":1011.57,"humidity":97,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":56},"wind":{"speed":2.46,"deg":150.001},"rain":{"3h":1.84},"sys":{"pod":"n"},"dt_txt":"2017-07-02 15:00:00"},{"dt":1499018400,"main":{"temp":24.46,"temp_min":24.46,"temp_max":24.46,"pressure":1011.04,"sea_level":1019.79,"grnd_level":1011.04,"humidity":98,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.66,"deg":142.501},"rain":{"3h":6.75},"sys":{"pod":"n"},"dt_txt":"2017-07-02 18:00:00"},{"dt":1499029200,"main":{"temp":23.35,"temp_min":23.35,"temp_max":23.35,"pressure":1010.47,"sea_level":1019.19,"grnd_level":1010.47,"humidity":98,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":92},"wind":{"speed":1.77,"deg":103.001},"rain":{"3h":9},"sys":{"pod":"n"},"dt_txt":"2017-07-02 21:00:00"},{"dt":1499040000,"main":{"temp":24.81,"temp_min":24.81,"temp_max":24.81,"pressure":1011.14,"sea_level":1019.99,"grnd_level":1011.14,"humidity":98,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":88},"wind":{"speed":2.06,"deg":109.501},"rain":{"3h":0.48},"sys":{"pod":"d"},"dt_txt":"2017-07-03 00:00:00"},{"dt":1499050800,"main":{"temp":26.82,"temp_min":26.82,"temp_max":26.82,"pressure":1011.32,"sea_level":1020.12,"grnd_level":1011.32,"humidity":95,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.31,"deg":132.004},"rain":{"3h":2.87},"sys":{"pod":"d"},"dt_txt":"2017-07-03 03:00:00"},{"dt":1499061600,"main":{"temp":27.38,"temp_min":27.38,"temp_max":27.38,"pressure":1010.32,"sea_level":1018.97,"grnd_level":1010.32,"humidity":95,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":2.56,"deg":137.001},"rain":{"3h":2.73},"sys":{"pod":"d"},"dt_txt":"2017-07-03 06:00:00"},{"dt":1499072400,"main":{"temp":28.07,"temp_min":28.07,"temp_max":28.07,"pressure":1008.74,"sea_level":1017.51,"grnd_level":1008.74,"humidity":89,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":92},"wind":{"speed":3.13,"deg":140.005},"rain":{"3h":0.090000000000003},"sys":{"pod":"d"},"dt_txt":"2017-07-03 09:00:00"},{"dt":1499083200,"main":{"temp":26.58,"temp_min":26.58,"temp_max":26.58,"pressure":1009.68,"sea_level":1018.4,"grnd_level":1009.68,"humidity":92,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":80},"wind":{"speed":2.46,"deg":137.007},"rain":{"3h":2.09},"sys":{"pod":"n"},"dt_txt":"2017-07-03 12:00:00"},{"dt":1499094000,"main":{"temp":25.51,"temp_min":25.51,"temp_max":25.51,"pressure":1010.64,"sea_level":1019.36,"grnd_level":1010.64,"humidity":96,"temp_kf":0},"weather":[{"id":501,"main":"Rain","description":"中雨","icon":"10n"}],"clouds":{"all":100},"wind":{"speed":1.37,"deg":120.002},"rain":{"3h":3.1},"sys":{"pod":"n"},"dt_txt":"2017-07-03 15:00:00"},{"dt":1499104800,"main":{"temp":25.59,"temp_min":25.59,"temp_max":25.59,"pressure":1009.12,"sea_level":1017.87,"grnd_level":1009.12,"humidity":98,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":88},"wind":{"speed":1.41,"deg":121.502},"rain":{"3h":0.39},"sys":{"pod":"n"},"dt_txt":"2017-07-03 18:00:00"},{"dt":1499115600,"main":{"temp":25.26,"temp_min":25.26,"temp_max":25.26,"pressure":1008.46,"sea_level":1017.2,"grnd_level":1008.46,"humidity":98,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":64},"wind":{"speed":1.26,"deg":120.004},"rain":{"3h":0.29000000000001},"sys":{"pod":"n"},"dt_txt":"2017-07-03 21:00:00"},{"dt":1499126400,"main":{"temp":26.91,"temp_min":26.91,"temp_max":26.91,"pressure":1009.48,"sea_level":1018.17,"grnd_level":1009.48,"humidity":97,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10d"}],"clouds":{"all":68},"wind":{"speed":1.71,"deg":121},"rain":{"3h":0.030000000000001},"sys":{"pod":"d"},"dt_txt":"2017-07-04 00:00:00"},{"dt":1499137200,"main":{"temp":30.08,"temp_min":30.08,"temp_max":30.08,"pressure":1009.69,"sea_level":1018.28,"grnd_level":1009.69,"humidity":85,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"多云","icon":"03d"}],"clouds":{"all":36},"wind":{"speed":1.81,"deg":232.001},"rain":{},"sys":{"pod":"d"},"dt_txt":"2017-07-04 03:00:00"},{"dt":1499148000,"main":{"temp":31.93,"temp_min":31.93,"temp_max":31.93,"pressure":1007.58,"sea_level":1016.19,"grnd_level":1007.58,"humidity":78,"temp_kf":0},"weather":[{"id":801,"main":"Clouds","description":"晴，少云","icon":"02d"}],"clouds":{"all":12},"wind":{"speed":1.81,"deg":233.002},"rain":{},"sys":{"pod":"d"},"dt_txt":"2017-07-04 06:00:00"},{"dt":1499158800,"main":{"temp":31.91,"temp_min":31.91,"temp_max":31.91,"pressure":1005.3,"sea_level":1014.01,"grnd_level":1005.3,"humidity":76,"temp_kf":0},"weather":[{"id":802,"main":"Clouds","description":"多云","icon":"03d"}],"clouds":{"all":48},"wind":{"speed":1.81,"deg":228.51},"rain":{},"sys":{"pod":"d"},"dt_txt":"2017-07-04 09:00:00"},{"dt":1499169600,"main":{"temp":28.66,"temp_min":28.66,"temp_max":28.66,"pressure":1006.52,"sea_level":1015.13,"grnd_level":1006.52,"humidity":85,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":76},"wind":{"speed":1.52,"deg":188.503},"rain":{"3h":1.56},"sys":{"pod":"n"},"dt_txt":"2017-07-04 12:00:00"},{"dt":1499180400,"main":{"temp":27.1,"temp_min":27.1,"temp_max":27.1,"pressure":1007.57,"sea_level":1016.23,"grnd_level":1007.57,"humidity":90,"temp_kf":0},"weather":[{"id":803,"main":"Clouds","description":"多云","icon":"04n"}],"clouds":{"all":56},"wind":{"speed":1.41,"deg":175.508},"rain":{},"sys":{"pod":"n"},"dt_txt":"2017-07-04 15:00:00"},{"dt":1499191200,"main":{"temp":26.42,"temp_min":26.42,"temp_max":26.42,"pressure":1007.02,"sea_level":1015.63,"grnd_level":1007.02,"humidity":95,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":56},"wind":{"speed":1.26,"deg":160.501},"rain":{"3h":0.019999999999996},"sys":{"pod":"n"},"dt_txt":"2017-07-04 18:00:00"},{"dt":1499202000,"main":{"temp":25.46,"temp_min":25.46,"temp_max":25.46,"pressure":1006.62,"sea_level":1015.32,"grnd_level":1006.62,"humidity":97,"temp_kf":0},"weather":[{"id":500,"main":"Rain","description":"小雨","icon":"10n"}],"clouds":{"all":12},"wind":{"speed":0.96,"deg":113.504},"rain":{"3h":0.030000000000001},"sys":{"pod":"n"},"dt_txt":"2017-07-04 21:00:00"}]
	 * city : {"id":1809858,"name":"Guangzhou","coord":{"lat":23.1167,"lon":113.25},"country":"CN"}
	 */

	private String cod;
	private String message;
	/** Number of lines returned by this API call */
	private String cnt;
	private CityBean city;
	private List<ForecastBean> list;

	public String getCod() { return cod;}

	public void setCod(String cod) { this.cod = cod;}

	public String getMessage() { return message;}

	public void setMessage(String message) { this.message = message;}

	public String getCnt() { return cnt;}

	public void setCnt(String cnt) { this.cnt = cnt;}

	public CityBean getCity() { return city;}

	public void setCity(CityBean city) { this.city = city;}

	public List<ForecastBean> getList() { return list;}

	public void setList(List<ForecastBean> list) { this.list = list;}

	// <-------- interface implements -------->

	@Override public List<? extends IForecastBean> get_forecastBeanList() { return getList(); }
}