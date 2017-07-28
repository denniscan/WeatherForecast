package can.dennis.weatherforecast.utils.pagemanager;

import android.support.annotation.Nullable;

import java.util.Stack;

import can.dennis.weatherforecast.ui.activity.base.BaseActivity;
/**
 * Activity pages manager
 * Created by Dennis Can on 2017-06-29.
 */
public class PageManager {
	private static PageManager instance = new PageManager();

	public static PageManager getInstance() { return instance; }

	private Stack<BaseActivity> activityStack;

	private PageManager() {activityStack = new Stack<>();}

	public void addActivity(BaseActivity activity) { activityStack.add(activity); }

	public void removeActivity(BaseActivity activity) { activityStack.remove(activity); }

	@Nullable public BaseActivity getActivity(Class<? extends BaseActivity> clazz) {
		String className = clazz.getSimpleName();
		for (BaseActivity activity : activityStack)
			if (activity.getClass().getSimpleName().equals(className))
				return activity;
		return null;
	}
}
