package can.dennis.weatherforecast.utils.network;

import android.app.Activity;
import android.graphics.Bitmap;

import can.dennis.weatherforecast.bean.openweather.OpenWeatherCurrentJsonBean;
import can.dennis.weatherforecast.bean.openweather.OpenWeatherForecastJsonBean;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import can.dennis.weatherforecast.utils.network.manager.BingPicManager;
import can.dennis.weatherforecast.utils.network.manager.OpenWeatherMapNetworkManager;
import can.dennis.weatherforecast.utils.network.ok.OkHttpUtils;
import can.dennis.weatherforecast.utils.networkstatus.NetworkStatusUtils;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
/**
 * Created by Dennis Can on 2017-06-29.
 */
public class NetworkUtils {
	public static final int NETWORK_TIME_OUT = 30;
	private static NetworkUtils instance = new NetworkUtils();

	public static NetworkUtils getInstance() { return instance; }

	private final OpenWeatherMapNetworkManager openWeatherManager;
	private final BingPicManager bingPicManager;

	private NetworkUtils() {
		OkHttpClient okClient = OkHttpUtils.getInstance().client;
		openWeatherManager = new OpenWeatherMapNetworkManager(okClient);
		bingPicManager = new BingPicManager(okClient);
	}

	/**
	 * 获取当前的网络状态是否可用
	 */
	public static boolean isNetworkAvailable() { return NetworkStatusUtils.getInstance().isNetworkAvailable(); }

	public ObservableSource<NetworkResponseDataPackage<OpenWeatherCurrentJsonBean>> loadOpenWeatherCurrent() {
		return openWeatherManager.loadOpenWeatherCurrent();
	}

	public ObservableSource<NetworkResponseDataPackage<OpenWeatherForecastJsonBean>> loadOpenWeatherForecast() {
		return openWeatherManager.loadOpenWeatherForecast();
	}

	public Function<Boolean, ObservableSource<String>> getBingPicUrl() { return bingPicManager.getBingPicUrl(); }

	public Function<String, ObservableSource<Bitmap>> loadBingPic(Activity activity) {
		return bingPicManager.loadBingPic(activity);
	}
}
