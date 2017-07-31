package can.dennis.weatherforecast.ui.activity.main.manager;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.List;

import can.dennis.weatherforecast.ui.activity.main.manager.base.BaseChartManager;
/**
 * Created by Dennis Can on 2017-07-28.
 */
class TemperatureChartManager extends BaseChartManager<LineDataSet, Entry> {
	private final LineChart chart;

	public TemperatureChartManager(LineChart chart) {
		super(chart);
		this.chart = chart;
	}

	@Override protected LineDataSet getDataSet(List<Entry> values) { return new LineDataSet(values, "温度"); }

	@Override protected int getChartColor() { return Color.BLUE; }

	@Override protected XAxis.XAxisPosition getXPosition() { return XAxis.XAxisPosition.TOP; }


	@Override protected YAxis.AxisDependency getShowSide() {		return YAxis.AxisDependency.LEFT;	}

	@Override protected ChartData<? extends IDataSet<Entry>> makeData(LineDataSet dataSet) {
		return new LineData(dataSet);
	}

//	private void initTemperatureChart(LineDataSet dataSet, int dataSize, int minTemperature, int maxTemperature) {
//		initTemperatureYAxis(minTemperature, maxTemperature);
//		chart.setData(new LineData(dataSet));
//		chart.invalidate();
//	}
//
//	private void initTemperatureYAxis(int minTemperature, int maxTemperature) {
//		YAxis yAxis = chart.getAxisLeft();
////		chart.getAxis(YAxis.AxisDependency.LEFT);
//		yAxis.setAxisMinimum(minTemperature + 2);
//		yAxis.setAxisMaximum(maxTemperature + 2);
//	}
}
