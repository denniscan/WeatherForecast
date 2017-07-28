package can.dennis.weatherforecast.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import can.dennis.weatherforecast.utils.pagemanager.PageManager;

/**
 * Base Activity
 * Created by Dennis Can on 2017-06-29.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
	public BaseActivity activity;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PageManager.getInstance().addActivity(this);

		init();
		beforeStart();
		if (!breakStart()) {
			int layoutResID = getLayoutResource();
			if (layoutResID != 0)
				setContentView(layoutResID);
			assignViews();
			onViewsReady();
			loadRawData();
		}
	}

	@Override public void onClick(View view) {}

	@Override protected void onDestroy() {
		super.onDestroy();
		PageManager.getInstance().removeActivity(this);
	}

	private void init() { activity = this; }

	protected void beforeStart() {}

	protected boolean breakStart() { return false; }

	protected <T extends View> T findView(int resId) {return (T) findViewById(resId);}

	protected abstract int getLayoutResource();

	protected abstract void assignViews();

	protected abstract void onViewsReady();

	protected abstract void loadRawData();
}
