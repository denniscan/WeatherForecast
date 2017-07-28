package can.dennis.weatherforecast.utils.network.ok;

import java.util.concurrent.TimeUnit;

import can.dennis.weatherforecast.utils.network.NetworkUtils;
import okhttp3.OkHttpClient;
/**
 * OkHttpUtils
 * Created by Dennis Can on 2017-06-29.
 */
public class OkHttpUtils {
	private static OkHttpUtils instance = new OkHttpUtils();

	public static OkHttpUtils getInstance() { return instance; }

	public final OkHttpClient client;

	private OkHttpUtils() { this.client = createDefaultClientBuilder().build(); }

	private OkHttpClient.Builder createDefaultClientBuilder() {
		return new OkHttpClient.Builder()
				.connectTimeout(NetworkUtils.NETWORK_TIME_OUT, TimeUnit.SECONDS)
				.readTimeout(NetworkUtils.NETWORK_TIME_OUT, TimeUnit.SECONDS)
				.writeTimeout(NetworkUtils.NETWORK_TIME_OUT, TimeUnit.SECONDS);
	}
}
