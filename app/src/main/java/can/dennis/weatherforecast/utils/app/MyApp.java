package can.dennis.weatherforecast.utils.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.crashlogger.CrashLogger;

/**
 * My App
 * Created by Dennis Can on 2017-06-29.
 */
public class MyApp extends Application {
	public static final String DIR_IMAGE = "image";
	public static final String DIR_FILE = "file";

	public static String CACHE_PATH;
	public static String CACHE_IMAGE_PATH;
	public static String CACHE_FILE_PATH;

	private static Context context;
	private static Handler handler;
	private static Toast toast;

	@Override public void onCreate() {
		Log.i("Dennis", "VERSION_TAG: " + A.VERSION_TAG);
		super.onCreate();

		context = this;
		handler = new Handler();

		initCacheDir();
		initCrashLogger();
	}

	private void initCacheDir() {
		File externalCacheDir = getExternalCacheDir();
		if (externalCacheDir != null)
			CACHE_PATH = externalCacheDir.getAbsolutePath();
		else
			CACHE_PATH = getCacheDir().getAbsolutePath();

		File file = new File(CACHE_PATH, DIR_IMAGE);
		if (!file.exists())
			file.mkdirs();
		CACHE_IMAGE_PATH = file.getAbsolutePath();

		file = new File(CACHE_PATH, DIR_FILE);
		if (!file.exists())
			file.mkdirs();
		CACHE_FILE_PATH = file.getAbsolutePath();
	}

	private void initCrashLogger() {
		CrashLogger.getInstance().init(getContext());
	}

	public static Handler getHandler() { return handler; }

	public static Context getContext() { return context; }

	public static void showToast(int resId) { showToast(getContext().getString(resId)); }

	public static void showToast(String string) {
		if (toast == null)
			toast = Toast.makeText(context, string, Toast.LENGTH_LONG);
		else
			toast.setText(string);
		toast.show();
	}
}
