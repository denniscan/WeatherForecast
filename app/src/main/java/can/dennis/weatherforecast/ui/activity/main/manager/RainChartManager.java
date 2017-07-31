package can.dennis.weatherforecast.ui.activity.main.manager;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.List;

import can.dennis.weatherforecast.ui.activity.main.manager.base.BaseChartManager;
/**
 * Created by Dennis Can on 2017-07-28.
 */
class RainChartManager extends BaseChartManager<BarDataSet, BarEntry> {
	private final BarChart chart;

	public RainChartManager(BarChart chart) {
		super(chart);
		this.chart = chart;
	}

	@Override protected BarDataSet getDataSet(List<BarEntry> values) { return new BarDataSet(values, "降雨量"); }

	@Override protected int getChartColor() { return Color.YELLOW; }

	@Override protected XAxis.XAxisPosition getXPosition() { return XAxis.XAxisPosition.BOTTOM; }

	@Override protected YAxis.AxisDependency getShowSide() {		return YAxis.AxisDependency.RIGHT;	}

	@Override protected ChartData<? extends IDataSet<BarEntry>> makeData(BarDataSet dataSet) {
		return new BarData(dataSet);
	}

//	public void loadData(List<BarEntry> rainValues, List<Long> dateTimeValues, int dataSize, int maxR) {
//		BarDataSet rainDataSet = initChart(new BarDataSet(rainValues, "降雨量"), Color.YELLOW);
////		rainDataSet.setValueFormatter(new IValueFormatter() {
////			@Override public String getFormattedValue(float value, Entry entry, int dataSetIndex,
////					ViewPortHandler viewPortHandler) {
////				A.log("value = [" + value + "], entry = [" + entry + "], dataSetIndex = [" + dataSetIndex + "], viewPortHandler = [" + viewPortHandler + "]");
////				return String.valueOf("abc"+value);
////			}
////		});
//		initRainChart(rainDataSet);
//	}
}
