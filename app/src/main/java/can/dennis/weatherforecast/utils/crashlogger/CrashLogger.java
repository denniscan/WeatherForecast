package can.dennis.weatherforecast.utils.crashlogger;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import can.dennis.weatherforecast.utils.app.MyApp;
import can.dennis.weatherforecast.utils.pagemanager.PageManager;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,由该类来接管程序,并记录发送错误报告.
 *
 * @author way
 */
public class CrashLogger implements UncaughtExceptionHandler {
	private UncaughtExceptionHandler mDefaultHandler; // 系统默认的UncaughtException处理类
	private static CrashLogger INSTANCE = new CrashLogger(); // CrashHandler实例
	private Context mContext; // 程序的Context对象 

	/** 保证只有一个CrashHandler实例 */
	private CrashLogger() {}

	/** 获取CrashHandler实例 ,单例模式 */
	public static CrashLogger getInstance() { return INSTANCE; }

	/**
	 * 初始化
	 */
	public void init(Context context) {
		mContext = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler(); // 获取系统默认的UncaughtException处理器 
		Thread.setDefaultUncaughtExceptionHandler(this); // 设置该CrashHandler为程序的默认处理器 
	}

	/**
	 * 当UncaughtException发生时会转入该重写的方法来处理
	 */
	@Override public void uncaughtException(Thread thread, Throwable ex) {
		PageManager.getInstance().finishAllActivities();
		handleException(ex);
		if (mDefaultHandler != null)
			mDefaultHandler.uncaughtException(thread, ex);
		else {
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
/*
		if (!handleException(ex) && mDefaultHandler != null) { // 如果自定义的没有处理则让系统默认的异常处理器来处理
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			 try {
			 	Thread.sleep(3000); // 如果处理了，让程序继续运行3秒再退出，保证文件保存并上传到服务器
			 } catch (InterruptedException e) {
			 	e.printStackTrace();
			 }
			// 退出程序
			AppManager.finishAllActivity();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
*/
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 *
	 * @param ex
	 * 		异常信息
	 *
	 * @return true 如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(Throwable ex) {
		if (ex == null)
			return false;
		new Thread() {
			public void run() {
				Looper.prepare();
				Toast.makeText(mContext, "很抱歉，程序出现异常，即将退出", Toast.LENGTH_SHORT).show();
				Looper.loop();
			}
		}.start();
		// 收集设备参数信息
		Map<String, String> exceptionInfoMap = collectDeviceInfo(mContext);
		// 保存日志文件
		saveCrashInfo2File(ex, exceptionInfoMap);
		try { Thread.sleep(2000); } catch (InterruptedException ignored) { }
		return true;
	}

	/**
	 * 收集设备参数信息
	 */
	private Map<String, String> collectDeviceInfo(Context context) {
		Map<String, String> exceptionInfoMap = new ArrayMap<>();
		try {
			PackageManager pm = context.getPackageManager(); // 获得包管理器 
			PackageInfo pi =
					pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES); // 得到该应用的信息，即主Activity 
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				exceptionInfoMap.put("versionName", versionName);
				exceptionInfoMap.put("versionCode", versionCode);
				exceptionInfoMap.put("sdkInt", Build.VERSION.SDK_INT + "");
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		Field[] fields = Build.class.getDeclaredFields(); // 反射机制 
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				exceptionInfoMap.put(field.getName(), field.get("").toString());
				// A.log(field.getName() + ":" + field.get(""));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return exceptionInfoMap;
	}

	@Nullable private String saveCrashInfo2File(Throwable ex, Map<String, String> exceptionInfoMap) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : exceptionInfoMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			sb.append(key).append("=").append(value).append("\r\n");
		}
		Writer writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		ex.printStackTrace(pw);
		Throwable cause = ex.getCause();
		// 循环着把所有的异常信息写入writer中
		while (cause != null) {
			cause.printStackTrace(pw);
			cause = cause.getCause();
		}
		pw.close(); // 记得关闭
		String result = writer.toString();
		sb.append(result);
		// 保存文件
		long timeStamp = System.currentTimeMillis();
		String time = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		String fileName = "crash_" + time + "_" + timeStamp + ".log";
		try {
			File dir = new File(MyApp.CACHE_FILE_PATH, "crash");
			if (!dir.exists())
				dir.mkdir();
			FileOutputStream fos = new FileOutputStream(new File(dir, fileName));
			fos.write(sb.toString().getBytes());
			fos.close();
			return fileName;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}