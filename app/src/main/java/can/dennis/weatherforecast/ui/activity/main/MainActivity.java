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

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import can.dennis.weatherforecast.R;
import can.dennis.weatherforecast.itface.IOpenWeatherCurrent;
import can.dennis.weatherforecast.itface.IOpenWeatherForecast;
import can.dennis.weatherforecast.itface.element.IForecastBean;
import can.dennis.weatherforecast.service.MainService;
import can.dennis.weatherforecast.ui.activity.base.BaseActivity;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.calendarutils.CalendarUtils;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.fileutils.FileUtils;
import can.dennis.weatherforecast.utils.network.NetworkUtils;
import can.dennis.weatherforecast.utils.networkstatus.NetworkStatusUtils;
import can.dennis.weatherforecast.utils.preference.PreferenceUtils;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Main Activity
 * Created by Dennis Can on 2017-06-29.
 */
public class MainActivity extends BaseActivity {
	private ImageView backgroundIV;
	private SwipeRefreshLayout swipeRefreshLayout;
	private TextView cityTV, updateTimeTV, currentTemperatureTV, weatherDescriptionTV, maxTemperatureTV,
			minTemperatureTV, pressureTV, humidityTV, visibilityTV, windSpeedTV, windDirectionTV, sunRiseTV, sunSetTV;
	private LineChart chart;
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
		chart = findView(R.id.chart);
	}

	@Override protected void onViewsReady() {
		swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
		init_chart();
		bindService(new Intent(activity, MainService.class), serviceConnection, Context.BIND_AUTO_CREATE);
		loadBingPicture();
	}

	@Override protected void loadRawData() { swipeRefreshLayout.setRefreshing(true); }

	@Override protected void onDestroy() {
		super.onDestroy();
		unbindService(serviceConnection);
	}

	@Override public void onBackPressed() {
		if (swipeRefreshLayout.isRefreshing())
			swipeRefreshLayout.setRefreshing(false);
		else
			super.onBackPressed();
	}

	private void init_chart() {
		chart.setScaleYEnabled(false);
		chart.getLegend().setEnabled(false);

		chart.setDescription(null);
		chart.setClickable(false);
		chart.setDoubleTapToZoomEnabled(false);
	}

	private void initXAxis(int listSize) {
		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//		xAxis.setLabelRotationAngle(-90);
		xAxis.setTextSize(7);

		xAxis.setAxisMinimum(0);
		xAxis.setAxisMaximum(listSize - 1);
		xAxis.setLabelCount(listSize); // 刻度个数
		xAxis.setGranularityEnabled(true); // 粒度（刻度密度）控制

		xAxis.setValueFormatter(new IAxisValueFormatter() {
			int lastHour;

			@Override public String getFormattedValue(float value, AxisBase axis) {
				String returnString = "";

				long dateTimeValue;
				int intValue = (int) value;
				if (intValue == value &&
				    (dateTimeValue = forecastBean.get_forecastBeanList().get(intValue).get_dateTimeValue()) !=
				    ResponseParseUtils.ERROR_LONG) {
					Date date = new Date(dateTimeValue * 1000);
					CalendarUtils calendarUtils = CalendarUtils.getInstance();
					Calendar calendar = calendarUtils.getCalendar(date);

					returnString = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
					if (intValue == 0 || calendarUtils.isZero(calendar) ||
					    calendar.get(Calendar.HOUR_OF_DAY) < lastHour)
						returnString += ("\n" + Constants.MONTH_DAY_DATE_FORMATTER.format(date));
					lastHour = calendar.get(Calendar.HOUR_OF_DAY);
				}
				return returnString;
			}
		});
	}

	private void initYAxis(float minTemperature, float maxTemperature, float maxRain) {
		YAxis yAxis = chart.getAxisLeft();
//		chart.getAxis(YAxis.AxisDependency.LEFT);
//		yAxis.setAxisMinimum((int) (minTemperature + 0.5) - 2);
//		yAxis.setAxisMaximum((int) (maxTemperature + 0.5) + 2);
	}

	private void loadBingPicture() {
		networkHelper.loadNetwork(loadLocalBingPic()
				.flatMap(NetworkUtils.getInstance().getBingPicUrl())
				.flatMap(NetworkUtils.getInstance().loadBingPic(activity)), new DisposableObserver<Bitmap>() {
			@Override public void onNext(@NonNull Bitmap bitmap) {
				A.log("BingPic: onNext: bitmap");
				backgroundIV.setImageBitmap(bitmap);
				if (FileUtils.getInstance().saveBitmapAsFile(bitmap, FileUtils.getInstance().getBingImageFile()))
					PreferenceUtils.getInstance().markBingImageRefreshTime();
			}

			@Override public void onError(@NonNull Throwable e) {
				A.log("BingPic: onError: " + e.getMessage());
			}

			@Override public void onComplete() { A.log("BingPic: onComplete"); }
		});
	}

	private Observable<Boolean> loadLocalBingPic() {
		return Observable.create(new ObservableOnSubscribe<Boolean>() {
			@Override public void subscribe(@NonNull ObservableEmitter<Boolean> e) throws Exception {
				A.log("<-------- Load local bing pic -------->");
				Drawable drawable =
						Drawable.createFromPath(FileUtils.getInstance().getBingImageFile().getAbsolutePath());
				if (drawable != null) {
					A.log("BingPic: 加载本地缓存");
					backgroundIV.setImageDrawable(drawable);
				} else {
					A.log("BingPic: 本地缓存为空");
				}
				if (needToRefreshBingImage()) {
					A.log("BingPic: 刷新网络");
					e.onNext(true);
				}
				e.onComplete();
			}

			private boolean needToRefreshBingImage() {
				return (A.DEBUG || NetworkStatusUtils.getInstance().isWifiApn()) && notDownloadedToday();
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
				binder.refresh(true);
		}
	};

	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			MainActivity.this.binder = (MainService.IServiceBinder) iBinder;
			binder.refresh(false);
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
		float maxTemperature = 0, minTemperature = 0;
		boolean initialized = false;
		List<Entry> temperatureValues = new ArrayList<>();

		float maxRain = 0;
		List<Entry> rainValues = new ArrayList<>();

		List<? extends IForecastBean> forecastBeanList = forecastBean.get_forecastBeanList();
		for (int i = 0; i < forecastBeanList.size(); i++) {
			final IForecastBean theBean = forecastBeanList.get(i);

			float temperature = theBean.get_temperatureFloat();
			temperature = temperature == ResponseParseUtils.ERROR_FLOAT ? 0 : temperature;
			temperatureValues.add(new Entry(i, temperature));
			if (!initialized) {
				maxTemperature = temperature;
				minTemperature = temperature;
				initialized = true;
			} else if (temperature > maxTemperature)
				maxTemperature = temperature;
			else if (temperature < minTemperature)
				minTemperature = temperature;

			float rain = theBean.get_rainFloat();
			rain = rain == ResponseParseUtils.ERROR_FLOAT ? 0 : rain;
			rainValues.add(new Entry(i, rain));
			if (rain > maxRain)
				maxRain = rain;
		}
		LineDataSet temperatureDataSet = new LineDataSet(temperatureValues, "温度");
		temperatureDataSet.setValueTextSize(7);
		temperatureDataSet.setColor(Color.BLUE);
		temperatureDataSet.setValueTextColor(Color.BLUE);

		LineDataSet rainDataSet = new LineDataSet(rainValues, "降雨量");
		rainDataSet.setValueTextSize(7);
		rainDataSet.setColor(Color.YELLOW);
		rainDataSet.setValueTextColor(Color.YELLOW);
//		rainDataSet.setValueFormatter(new IValueFormatter() {
//			@Override public String getFormattedValue(float value, Entry entry, int dataSetIndex,
//					ViewPortHandler viewPortHandler) {
//				A.log("value = [" + value + "], entry = [" + entry + "], dataSetIndex = [" + dataSetIndex + "], viewPortHandler = [" + viewPortHandler + "]");
//				return String.valueOf("abc"+value);
//			}
//		});

		initXAxis(forecastBeanList.size());
		initYAxis(minTemperature, maxTemperature, maxRain);
		chart.setData(new LineData(temperatureDataSet, rainDataSet));
		chart.zoom(1.5f, 0, 0, 0);
		chart.invalidate();
	}

	public void setRefreshing(boolean refreshing) { swipeRefreshLayout.setRefreshing(refreshing); }
}
