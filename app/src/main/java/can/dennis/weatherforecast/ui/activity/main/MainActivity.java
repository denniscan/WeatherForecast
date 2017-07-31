package can.dennis.weatherforecast.ui.activity.main;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

import can.dennis.weatherforecast.R;
import can.dennis.weatherforecast.itface.IOpenWeatherCurrent;
import can.dennis.weatherforecast.itface.IOpenWeatherForecast;
import can.dennis.weatherforecast.service.MainService;
import can.dennis.weatherforecast.ui.activity.base.BaseActivity;
import can.dennis.weatherforecast.ui.activity.main.manager.ChartManager;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.calendarutils.CalendarUtils;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.fileutils.FileUtils;
import can.dennis.weatherforecast.utils.network.NetworkUtils;
import can.dennis.weatherforecast.utils.networkstatus.NetworkStatusUtils;
import can.dennis.weatherforecast.utils.preference.PreferenceUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Main Activity
 * Created by Dennis Can on 2017-06-29.
 */
public class MainActivity extends BaseActivity {
	private ImageView backgroundIV;
	private SwipeRefreshLayout swipeRefreshLayout;
	private TextView cityTV, updateTimeTV, currentTemperatureTV, weatherDescriptionTV, maxTemperatureTV,
			minTemperatureTV, pressureTV, humidityTV, visibilityTV, windSpeedTV, windDirectionTV, sunRiseTV, sunSetTV;
	private ChartManager chartManager;
	private MainService.IServiceBinder binder;
	private IOpenWeatherCurrent currentBean;
	private IOpenWeatherForecast forecastBean;

	protected void beforeStart() {
		if (Build.VERSION.SDK_INT >= 21) {
			View decorView = getWindow().getDecorView();
			decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
			getWindow().setStatusBarColor(Color.TRANSPARENT);
		}
	}

	@Override protected int getLayoutResource() { return R.layout.activity_main; }

	@Override protected void assignViews() {
		backgroundIV = findView(R.id.backgroundIV);
		swipeRefreshLayout = findView(R.id.swipeRefreshLayout);
		cityTV = findView(R.id.cityTV);
		updateTimeTV = findView(R.id.updateTimeTV);
		currentTemperatureTV = findView(R.id.currentTemperatureTV);
		weatherDescriptionTV = findView(R.id.weatherDescriptionTV);
		maxTemperatureTV = findView(R.id.maxTemperatureTV);
		minTemperatureTV = findView(R.id.minTemperatureTV);
		pressureTV = findView(R.id.pressureTV);
		humidityTV = findView(R.id.humidityTV);
		visibilityTV = findView(R.id.visibilityTV);
		windSpeedTV = findView(R.id.windSpeedTV);
		windDirectionTV = findView(R.id.windDirectionTV);
		sunRiseTV = findView(R.id.sunRiseTV);
		sunSetTV = findView(R.id.sunSetTV);
	}

	@Override protected void onViewsReady() {
		swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
		chartManager = new ChartManager(findView(R.id.chartContainer));
		bindService(new Intent(activity, MainService.class), serviceConnection, Context.BIND_AUTO_CREATE);
		loadBingPicture();
	}

	@Override protected void loadRawData() { swipeRefreshLayout.setRefreshing(true); }

	@Override protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}

	@Override public void onBackPressed() {
		if (setRefreshing(false)) {
			if (binder != null)
				binder.cancelNetwork();
			return;
		}

		super.onBackPressed();
	}

	private void loadBingPicture() {
		loadLocalBingPic()
				.flatMap(NetworkUtils.getInstance().getBingPicUrl())
				.flatMap(NetworkUtils.getInstance().loadBingPic(activity))
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<Bitmap>() {
					@Override public void accept(Bitmap bitmap) throws Exception {
						A.log("BingPic: refresh bitmap");
						backgroundIV.setImageBitmap(bitmap);
						if (FileUtils
								.getInstance()
								.saveBitmapAsFile(bitmap, FileUtils.getInstance().getBingImageFile()))
							PreferenceUtils.getInstance().markBingImageRefreshTime();
					}
				}, new Consumer<Throwable>() {
					@Override public void accept(Throwable throwable) throws Exception {
						A.log("BingPic: onError: " + throwable.getMessage());
					}
				});
	}

	private Observable<Boolean> loadLocalBingPic() {
		return Observable.create(new ObservableOnSubscribe<Boolean>() {
			@Override public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
				Drawable drawable =
						Drawable.createFromPath(FileUtils.getInstance().getBingImageFile().getAbsolutePath());
				if (drawable != null)
					backgroundIV.setImageDrawable(drawable);

				if (needToRefreshBingImage())
					e.onNext(true);

				e.onComplete();
			}

			private boolean needToRefreshBingImage() {
				return //
//						A.DEBUG || //
						NetworkStatusUtils.getInstance().isWifiApn() && notDownloadedToday();
			}

			private boolean notDownloadedToday() {
				Calendar todayZero = CalendarUtils.getInstance().getTodayZero();
				Calendar refreshTime = CalendarUtils
						.getInstance()
						.getCalendar(PreferenceUtils.getInstance().getBingImageRefreshTime());
				return refreshTime.before(todayZero);
			}
		});
	}

	private SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
		@Override public void onRefresh() {
			if (binder != null)
				binder.refresh();
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			MainActivity.this.binder = (MainService.IServiceBinder) iBinder;
			binder.init();
		}

		@Override public void onServiceDisconnected(ComponentName componentName) {
			A.log("MainActivity::ServiceConnection::onServiceDisconnected");
		}
	};

	public void onNetworkFailure() { swipeRefreshLayout.setRefreshing(false); }

	public void onSuccessCurrent(IOpenWeatherCurrent currentBean) {
		this.currentBean = currentBean;
		cityTV.setText(currentBean.get_cityName());
		updateTimeTV.setText(Constants.SHOW_DATE_FORMATTER.format(new Date()));
		currentTemperatureTV.setText(currentBean.get_currentTemperatureString());
		weatherDescriptionTV.setText(currentBean.get_weatherDescription());
		maxTemperatureTV.setText(currentBean.get_maxTemperatureString());
		minTemperatureTV.setText(currentBean.get_minTemperatureString());
		pressureTV.setText(currentBean.get_pressureString());
		humidityTV.setText(currentBean.get_humidityString());
		visibilityTV.setText(currentBean.get_visibilityString());
		windSpeedTV.setText(currentBean.get_windSpeedString());
		windDirectionTV.setText(currentBean.get_windDirectionString());
		sunRiseTV.setText(currentBean.get_sunriseString());
		sunSetTV.setText(currentBean.get_sunsetString());
	}

	public void onSuccessForecast(IOpenWeatherForecast forecastBean) {
		this.forecastBean = forecastBean;
		chartManager.loadData(forecastBean);
	}

	public boolean setRefreshing(boolean toRefresh) {
		if (swipeRefreshLayout.isRefreshing() != toRefresh) {
			swipeRefreshLayout.setRefreshing(toRefresh);
			return true;
		} else
			return false;
	}
}
