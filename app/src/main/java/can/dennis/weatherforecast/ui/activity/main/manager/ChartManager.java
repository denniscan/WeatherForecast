package can.dennis.weatherforecast.ui.activity.main.manager;

import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import can.dennis.weatherforecast.R;
import can.dennis.weatherforecast.itface.IOpenWeatherForecast;
import can.dennis.weatherforecast.itface.element.IForecastBean;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.calendarutils.CalendarUtils;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * 表格Manager
 * Created by Dennis Can on 2017-07-28.
 */
public class ChartManager {
	private final int ORG_TEXT_SIZE = 7;
	private int textSize = ORG_TEXT_SIZE;

	private final CombinedChart chart;
	private List<? extends IForecastBean> dataList;

	private String[] xTabs;
	private int dataSize;
	private ChartTouchListener orgOnTouchListener;

	public ChartManager(View container) {
		chart = (CombinedChart) container.findViewById(R.id.chart);
		initChart();
	}

	private void initChart() {
		chart.setScaleEnabled(false);
		chart.zoom(2f, 1f, 0, 0);
		chart.getLegend().setEnabled(false);
		chart.setDescription(null);
		chart.setClickable(false);
		chart.setDoubleTapToZoomEnabled(false);
		orgOnTouchListener = chart.getOnTouchListener();
		chart.setOnTouchListener(new ChartTouchListener<CombinedChart>(chart) {
			@Override public boolean onTouch(View v, MotionEvent event) {
				orgOnTouchListener.onTouch(v, event);
				return true;
			}
		});
	}

	private int parseInt(float value) { return value == ResponseParseUtils.ERROR_FLOAT ? 0 : Math.round(value); }

	private void initChart(int minY, int maxY) {
		initXAxis();
		initYAxis(minY, maxY);
	}

	private void initXAxis() {
		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setTextSize(7);
		xAxis.setAxisMinimum(0);
		xAxis.setAxisMaximum(dataSize - 1);
		xAxis.setLabelCount(dataSize); // 刻度个数
		xAxis.setGranularityEnabled(true); // 粒度（刻度密度）控制
		xAxis.setValueFormatter(new IndexAxisValueFormatter(xTabs));
	}

	private void initYAxis(int minY, int maxY) {
		initYAxis(chart.getAxis(YAxis.AxisDependency.LEFT), minY, maxY);
//		initYAxis(chart.getAxis(YAxis.AxisDependency.RIGHT), minY, maxY);
		chart.getAxis(YAxis.AxisDependency.RIGHT).setEnabled(false);
	}

	private void initYAxis(YAxis axis, int minY, int maxY) {
		axis.setAxisMinimum(minY);
		axis.setAxisMaximum(maxY);
	}

	private CombinedData makeData(List<Entry> temperatureValues, List<BarEntry> rainValues) {
		LineDataSet temperatureDataSet = new LineDataSet(temperatureValues, "温度");
		temperatureDataSet.setColor(Color.BLUE);
		temperatureDataSet.setValueTextSize(textSize);
		temperatureDataSet.setValueTextColor(Color.BLUE);

		BarDataSet rainDataSet = new BarDataSet(rainValues, "降雨量");
		rainDataSet.setColor(Color.YELLOW);
		rainDataSet.setValueTextSize(textSize);
		rainDataSet.setValueTextColor(Color.YELLOW);

		CombinedData combinedData = new CombinedData();
		combinedData.setData(new LineData(temperatureDataSet));
		combinedData.setData(new BarData(rainDataSet));
		return combinedData;
	}

	public void loadData(IOpenWeatherForecast forecastBean) {
		dataList = forecastBean.get_forecastBeanList();
		dataSize = dataList.size();
		xTabs = new String[dataSize];

		List<Entry> temperatureValues = new ArrayList<>();
		List<BarEntry> rainValues = new ArrayList<>();
		int minT = 0, maxT = 0, maxR = 0;
		int[] temperatureTempArray = new int[dataSize];
		int[] rainTempArray = new int[dataSize];
		CalendarUtils calendarUtils = CalendarUtils.getInstance();
		int lastHour = 0;
		for (int i = 0; i < dataSize; i++) {
			final IForecastBean bean = dataList.get(i);
			final int temperature = parseInt(bean.get_temperatureFloat());
			final int rain = parseInt(bean.get_rainFloat());
			final long dateTimeValue = bean.get_dateTimeValue();

			if (dateTimeValue != ResponseParseUtils.ERROR_FLOAT) {
				final Date date = new Date(dateTimeValue * 1000);
				final Calendar calendar = calendarUtils.getCalendar(date);
				int hour = calendar.get(Calendar.HOUR_OF_DAY);
				String xTab;
				if (i == 0 || hour < lastHour || calendarUtils.isZero(calendar))
					xTab = Constants.MONTH_DAY_DATE_FORMATTER.format(date);
				else
					xTab = String.valueOf(hour);
				xTabs[i] = xTab;
				lastHour = hour;
			}

			if (i == 0) {
				minT = temperature;
				maxT = temperature;
			} else {
				if (minT > temperature)
					minT = temperature;
				if (maxT < temperature)
					maxT = temperature;
			}
			if (maxR < rain)
				maxR = rain;

			temperatureValues.add(new Entry(i, temperature));
			rainValues.add(new BarEntry(i, rain));

			temperatureTempArray[i] = temperature;
			rainTempArray[i] = rain;
		}

		A.log("Temperature", temperatureTempArray);
		A.log("Rain", rainTempArray);
		int minY = Math.min(minT, 0);
		int maxY = Math.max(maxT, maxR);

		initChart(minY, maxY);
		chart.setData(makeData(temperatureValues, rainValues));
		chart.invalidate();
	}
}
