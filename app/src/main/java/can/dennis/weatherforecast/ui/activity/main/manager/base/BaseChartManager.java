package can.dennis.weatherforecast.ui.activity.main.manager.base;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import can.dennis.weatherforecast.utils.calendarutils.CalendarUtils;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.responseparse.ResponseParseUtils;
/**
 * Created by Dennis Can on 2017-07-28.
 */
public abstract class BaseChartManager<DS extends DataSet, E extends Entry> {
	private final static int ORG_TEXT_SIZE = 7;
	private final BarLineChartBase chart;
	private int textSize = ORG_TEXT_SIZE;

	private List<Long> dateTimeValues;
	private int dataSize;

	protected BaseChartManager(BarLineChartBase chart) {
		this.chart = chart;
		initChart();
	}

	private void initChart() {
		chart.setScaleEnabled(false);
		chart.zoom(1.1f, 1, 0, 0);
		chart.getLegend().setEnabled(false);
		chart.setDescription(null);
		chart.setClickable(false);
		chart.setDoubleTapToZoomEnabled(false);
//		chart.offsetLeftAndRight(10);
//		chart.offsetTopAndBottom(10);
//		chart.setMinOffset(10);
//		chart.setExtraTopOffset(50);
//		chart.setExtraBottomOffset(20);
//		chart.setDragOffsetX(50);
//		chart.setDragOffsetY(100);
	}

	protected DS initChart(DS dataSet, int color) {
		dataSet.setValueTextSize(textSize);
		dataSet.setColor(color);
		dataSet.setValueTextColor(color);
		return dataSet;
	}

	public void loadData(List<E> values, List<Long> dateTimeValues, int dataSize, int min, int max) {
		this.dateTimeValues = dateTimeValues;
		this.dataSize = dataSize;
		DS dataSet = initChart(getDataSet(values), getChartColor());
		initXAxis(dataSize);
		initYAxis(min, max);
		initChartShow(dataSet);
	}

	private void initXAxis(final int dataSize) {
		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(getXPosition());
//		xAxis.setLabelRotationAngle(-90);
		xAxis.setTextSize(7);

		xAxis.setAxisMinimum(0);
		xAxis.setAxisMaximum(dataSize - 1);
		xAxis.setLabelCount(dataSize); // 刻度个数
		xAxis.setGranularityEnabled(true); // 粒度（刻度密度）控制

		xAxis.setValueFormatter(new IAxisValueFormatter() {
			int lastHour;

			@Override public String getFormattedValue(float value, AxisBase axis) {
				String returnString = "";
				int intValue;
				if (value == 0f)
					intValue = 0;
				else if (value == 100f)
					intValue = dataSize;
				else
					intValue = (int) (dataSize * 100 / value);

				long dateTimeValue;
//				int intValue = (int) value;
				if (intValue == value &&
				    (dateTimeValue = dateTimeValues.get(intValue)) != ResponseParseUtils.ERROR_LONG) {
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

	private void initYAxis(int min, int max) {
		YAxis.AxisDependency showSide = getShowSide();
		if (showSide != YAxis.AxisDependency.LEFT && showSide != YAxis.AxisDependency.RIGHT)
			return;

		YAxis yAxis = chart.getAxis(showSide);
		yAxis.setAxisMinimum(min);
		yAxis.setAxisMaximum(max);

		YAxis hideYAxis = showSide != YAxis.AxisDependency.LEFT ? chart.getAxisLeft() : chart.getAxisRight();
		hideYAxis.setEnabled(false);
	}

	private void initChartShow(DS dataSet) {
		chart.setData(makeData(dataSet));
		chart.invalidate();
	}

	protected abstract DS getDataSet(List<E> values);

	protected abstract int getChartColor();

	protected abstract XAxis.XAxisPosition getXPosition();

	protected abstract YAxis.AxisDependency getShowSide();

	protected abstract ChartData<? extends IDataSet<E>> makeData(DS dataSet);
}
