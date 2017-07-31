package can.dennis.weatherforecast.utils;

import android.util.Log;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import can.dennis.weatherforecast.BuildConfig;
/**
 * A
 * Created by Dennis Can on 2017-06-29.
 */
public class A {
	private static final VersionTag DEFAULT_VERSION_TAG = VersionTag.Debug;
	public static final VersionTag VERSION_TAG = VersionTag.parse(BuildConfig.VERSION_TAG);
	public static final boolean DEBUG = VERSION_TAG != VersionTag.Release;

	private static final String TAG = "Dennis";

	private A() {}

	public static void log(Object... objs) {
		if (!DEBUG)
			return;
		String string;
		if (objs == null || objs.length == 0) {
			string = "empty";
		} else {
			string = getString(objs);
		}
		int maxLogSize = 999;
		for (int i = 0; i <= string.length() / maxLogSize; i++) {
			int start = i * maxLogSize;
			int end = (i + 1) * maxLogSize;
			end = end > string.length() ? string.length() : end;
			Log.i(TAG, string.substring(start, end));
		}
	}

	private static String getString(Object... objs) {
		StringBuilder sb = new StringBuilder();
		int endPoint = objs.length - 1;
		for (int i = 0; i < objs.length; i++) {
			final Object obj = objs[i];
			if (obj instanceof List) {
				sb.append(getListString((List<?>) obj));
			} else if (obj.getClass().isArray()) {
				sb.append(getArrayString(obj));
			} else {
				sb.append(obj.toString());
			}
			if (i < endPoint)
				sb.append(", ");
		}
		return sb.toString();
	}

	private static String getListString(List<?> list) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		int endPoint = list.size() - 1;
		for (int j = 0; j < list.size(); j++) {
			sb.append(list.get(j).toString());
			if (j < endPoint)
				sb.append(", ");
		}
		sb.append("}");
		return sb.toString();
	}

	private static String getArrayString(Object obj) {
		StringBuilder sb = new StringBuilder();
		if (obj instanceof short[])
			sb.append(Arrays.toString((short[]) obj));
		else if (obj instanceof int[])
			sb.append(Arrays.toString((int[]) obj));
		else if (obj instanceof long[])
			sb.append(Arrays.toString((long[]) obj));
		else if (obj instanceof float[])
			sb.append(Arrays.toString((float[]) obj));
		else if (obj instanceof double[])
			sb.append(Arrays.toString((double[]) obj));
		else if (obj instanceof byte[])
			sb.append(Arrays.toString((byte[]) obj));
		else if (obj instanceof char[])
			sb.append(Arrays.toString((char[]) obj));
		else if (obj instanceof boolean[])
			sb.append(Arrays.toString((boolean[]) obj));
		else
			sb.append(Arrays.toString((Object[]) obj));
		return sb.toString();
	}

	public static void setText(TextView textView, int valueString) { textView.setText(String.valueOf(valueString)); }

	public static void setText(TextView textView, float valueString) { textView.setText(String.valueOf(valueString)); }

	public static void setText(TextView textView, long valueString) { textView.setText(String.valueOf(valueString)); }

	public enum VersionTag {
		Debug,
		Release;

		public static VersionTag parse(String versionTag) {
			try { return valueOf(versionTag); } catch (IllegalArgumentException e) { return DEFAULT_VERSION_TAG; }
		}
	}
}
