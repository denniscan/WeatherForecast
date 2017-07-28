package can.dennis.weatherforecast.service.base;

import android.app.Service;

import can.dennis.weatherforecast.ui.activity.base.BaseActivity;
import can.dennis.weatherforecast.ui.activity.main.MainActivity;
import can.dennis.weatherforecast.utils.networkhelper.NetworkHelper;
import can.dennis.weatherforecast.utils.pagemanager.PageManager;
public abstract class BaseService extends Service {
	protected NetworkHelper networkHelper;

	@Override public void onCreate() {
		super.onCreate();
		networkHelper = new NetworkHelper(getNetworkHelperFeedback());
	}

	@Override public void onDestroy() {
		super.onDestroy();
		networkHelper.clear();
	}

	protected MainActivity getMainActivity() { return (MainActivity) getActivity(MainActivity.class); }

	protected BaseActivity getActivity(Class<? extends BaseActivity> clazz) {
		return PageManager.getInstance().getActivity(clazz);
	}

	protected abstract NetworkHelper.IOnNetworkStatusErrorFeedback getNetworkHelperFeedback();
}
