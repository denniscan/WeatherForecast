package can.dennis.weatherforecast.utils.closeableutils;

import java.io.Closeable;
import java.io.IOException;
/**
 * Closeable utils
 * Created by Dennis Can on 2017-07-03.
 */
public class CloseableUtils {
	private CloseableUtils() {}

	public static void close(Closeable closeable) {
		if (closeable == null)
			return;
		try {closeable.close();} catch (IOException ignored) {}
	}
}
