package can.dennis.weatherforecast.utils.network.manager;

import can.dennis.weatherforecast.bean.openweather.OpenWeatherCurrentJsonBean;
import can.dennis.weatherforecast.bean.openweather.OpenWeatherForecastJsonBean;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import can.dennis.weatherforecast.utils.network.manager.base.BaseNetworkManager;
import can.dennis.weatherforecast.utils.network.rx.subscriber.Subscriber;
import io.reactivex.ObservableSource;
import okhttp3.OkHttpClient;
/**
 * Created by Dennis Can on 2017-06-29.
 */
public class OpenWeatherMapNetworkManager extends BaseNetworkManager {
	public OpenWeatherMapNetworkManager(OkHttpClient okClient) { super(okClient); }

	private static final String CURRENT_URL =
			Constants.OPEN_WEATHER_CURRENT_URL_HEAD + "&id=" + Constants.OPEN_WEATHER_ID + "&appid=" +
			Constants.OPEN_WEATHER_APP_ID;
	private static final String FORECAST_URL =
			Constants.OPEN_WEATHER_FORECAST_URL_HEAD + "&id=" + Constants.OPEN_WEATHER_ID + "&appid=" +
			Constants.OPEN_WEATHER_APP_ID;

	public ObservableSource<NetworkResponseDataPackage<OpenWeatherCurrentJsonBean>> loadOpenWeatherCurrent() {
		return new ObservableBuilder<>(new CurrentSubscriber(CURRENT_URL)).build();
	}

	public ObservableSource<NetworkResponseDataPackage<OpenWeatherForecastJsonBean>> loadOpenWeatherForecast() {
		return new ObservableBuilder<>(new ForecastSubscriber(FORECAST_URL)).build();
	}

	private class CurrentSubscriber extends Subscriber<OpenWeatherCurrentJsonBean> {
		CurrentSubscriber(String url) {super(url);}
	}

	private class ForecastSubscriber extends Subscriber<OpenWeatherForecastJsonBean> {
		ForecastSubscriber(String url) {super(url);}
	}
}
