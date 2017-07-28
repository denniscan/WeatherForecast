package can.dennis.weatherforecast.service;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import can.dennis.weatherforecast.R;
import can.dennis.weatherforecast.bean.openweather.OpenWeatherCurrentJsonBean;
import can.dennis.weatherforecast.bean.openweather.OpenWeatherForecastJsonBean;
import can.dennis.weatherforecast.service.base.BaseService;
import can.dennis.weatherforecast.ui.activity.main.MainActivity;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.app.MyApp;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.network.NetworkUtils;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import can.dennis.weatherforecast.utils.networkhelper.NetworkHelper;
import can.dennis.weatherforecast.utils.preference.PreferenceUtils;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Main Service
 * Created by Dennis Can on 2017-06-29.
 */
public class MainService extends BaseService {
	@Nullable @Override public IBinder onBind(Intent intent) { return binder; }

	@Override protected NetworkHelper.IOnNetworkStatusErrorFeedback getNetworkHelperFeedback() {
		return networkHelperFeedback;
	}

	private boolean withinAutoRefreshInterval() {
		return System.currentTimeMillis() - PreferenceUtils.getInstance().getLastCheckTime() <
		       Constants.AUTO_REFRESH_INTERVAL;
	}

	private void loadLocalData() {
		PreferenceUtils preferenceUtils = PreferenceUtils.getInstance();

		String currentJson;
		OpenWeatherCurrentJsonBean currentBean;
		if ((currentJson = preferenceUtils.getLastOpenWeatherCurrentJson()) != null &&
		    (currentBean = new Gson().fromJson(currentJson, OpenWeatherCurrentJsonBean.class)) != null)
			showCurrent(currentBean);

		String forecastJson;
		OpenWeatherForecastJsonBean forecastBean;
		if ((forecastJson = preferenceUtils.getLastOpenWeatherForecastJson()) != null &&
		    (forecastBean = new Gson().fromJson(forecastJson, OpenWeatherForecastJsonBean.class)) != null)
			showForecast(forecastBean);

		setMainActivityRefreshing(false);
	}

	private void loadNetwork() {
		NetworkUtils networkUtils = NetworkUtils.getInstance();
		networkHelper.loadNetwork(networkUtils.loadOpenWeatherCurrent(), networkUtils.loadOpenWeatherForecast(),
				new DisposableObserver<NetworkResponseDataPackage<?>>() {
					private PreferenceUtils preferenceUtils = PreferenceUtils.getInstance();

					@Override public void onNext(@NonNull NetworkResponseDataPackage<?> dataPackage) {
						if (dataPackage.bean instanceof OpenWeatherCurrentJsonBean) {
							A.log("Load current data");
							preferenceUtils.markLastOpenWeatherCurrentJson(dataPackage.json);
							showCurrent(((OpenWeatherCurrentJsonBean) dataPackage.bean));
						} else if (dataPackage.bean instanceof OpenWeatherForecastJsonBean) {
							A.log("Load forecast data");
							preferenceUtils.markLastOpenWeatherForecastJson(dataPackage.json);
							showForecast((OpenWeatherForecastJsonBean) dataPackage.bean);
						} else {
							A.log("onNext error data");
							onNetworkFailure();
						}
					}

					@Override public void onError(@NonNull Throwable e) {
						A.log("Load network failure: " + e.getMessage());
						onNetworkFailure();
					}

					@Override public void onComplete() {
						setMainActivityRefreshing(false);
						preferenceUtils.markWeatherCheckTime();
					}
				});
	}

	private void showCurrent(OpenWeatherCurrentJsonBean currentBean) {
		MainActivity activity = getMainActivity();
		if (activity != null)
			activity.onSuccessCurrent(currentBean);
	}

	private void showForecast(OpenWeatherForecastJsonBean forecastBean) {
		MainActivity activity = getMainActivity();
		if (activity != null)
			activity.onSuccessForecast(forecastBean);
	}

	private void setMainActivityRefreshing(boolean refreshing) {
		MainActivity activity = getMainActivity();
		if (activity != null)
			activity.setRefreshing(refreshing);
	}

	private void onNetworkFailure() {
		MainActivity activity = getMainActivity();
		if (activity != null)
			activity.onNetworkFailure();
		MyApp.showToast(MyApp.getContext().getString(R.string.loadNetworkFailure));
	}

	private IServiceBinder binder = new ServiceBinder();

	private class ServiceBinder extends Binder implements IServiceBinder {
		@Override public void init() {
			A.log("Call from Activity: init()");
			loadLocalData();
			if (!withinAutoRefreshInterval())
				loadNetwork();
		}

		@Override public void refresh() {
			A.log("Call from Activity: refresh()");
			loadNetwork(); }

		@Override public void cancelNetwork() {
			A.log("Call from Activity: cancelNetwork");
			networkHelper.clear(); }
	}

	public interface IServiceBinder extends IBinder {
		void init();

		void refresh();

		void cancelNetwork();
	}

	private final NetworkHelper.IOnNetworkStatusErrorFeedback networkHelperFeedback =
			new NetworkHelper.IOnNetworkStatusErrorFeedback() {
				@Override public void onNetworkStatusError() {
					MainActivity activity = getMainActivity();
					if (activity != null)
						activity.setRefreshing(false);
				}
			};
}
