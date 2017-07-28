package can.dennis.weatherforecast.utils.responseparse;

/**
 * Created by Dennis Can on 2017-06-30.
 */
public class ResponseParseUtils {
	public static final int ERROR_INT = Integer.MIN_VALUE;
	public static final float ERROR_FLOAT = Float.MIN_VALUE;
	public static final long ERROR_LONG = Long.MIN_VALUE;

	private static ResponseParseUtils instance = new ResponseParseUtils();

	public static ResponseParseUtils getInstance() { return instance; }

	private ResponseParseUtils() { }

	public int parseInt(String valueString) {
		int value = ERROR_INT;
		float floatValue = parseFloat(valueString);
		if (floatValue != ERROR_FLOAT)
			try { value = (int) (floatValue + 0.5); } catch (NumberFormatException ignored) {}
		return value;
	}

	public float parseFloat(String valueString) {
		float value = ERROR_FLOAT;
		if (valueString != null)
			try {value = Float.parseFloat(valueString);} catch (NumberFormatException ignored) {}
		return value;
	}

	public long parseLong(String valueString) {
		long value = ERROR_LONG;
		if (valueString != null)
			try {value = Long.parseLong(valueString);} catch (NumberFormatException ignored) {}
		return value;
	}
}
