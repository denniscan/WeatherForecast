package can.dennis.weatherforecast.utils.networkstatus;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import can.dennis.weatherforecast.utils.app.MyApp;
/**
 * Network status utils
 * Created by Dennis Can on 2017-07-03.
 */
public class NetworkStatusUtils {
	private static NetworkStatusUtils instance = new NetworkStatusUtils();

	public static NetworkStatusUtils getInstance() { return instance; }

	private NetworkStatusUtils() { }

	public boolean isWifiApn() { return getApnType() == NetworkStatusEnum.WIFI; }

	public synchronized boolean isNetworkAvailable() { return getApnType() != NetworkStatusEnum.NoNetwork; }

	/**
	 * 获取当前的网络状态
	 */
	public NetworkStatusEnum getApnType() {
		NetworkInfo networkInfo = ((ConnectivityManager) MyApp
				.getContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		NetworkStatusEnum status = NetworkStatusEnum.NoNetwork;
		// NetworkInfo对象为空代表没有网络
		if (networkInfo != null) {
			switch (networkInfo.getType()) {
			case ConnectivityManager.TYPE_WIFI:
				status = NetworkStatusEnum.WIFI;
				break;
			case ConnectivityManager.TYPE_MOBILE:
				if (((TelephonyManager) MyApp
						.getContext()
						.getSystemService(Context.TELEPHONY_SERVICE)).isNetworkRoaming()) {
					status = NetworkStatusEnum.Mobile_2G;
				} else {
					switch (networkInfo.getSubtype()) {
					case TelephonyManager.NETWORK_TYPE_LTE: // 4G
						status = NetworkStatusEnum.Mobile_4G;
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS: // 联通3G
					case TelephonyManager.NETWORK_TYPE_HSDPA: // 联通3G
					case TelephonyManager.NETWORK_TYPE_EVDO_0: // 电信3G
						status = NetworkStatusEnum.Mobile_3G;
						break;
					case TelephonyManager.NETWORK_TYPE_GPRS: // 移动/联通2G
					case TelephonyManager.NETWORK_TYPE_EDGE: // 移动/联通2G
					case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2G
					default:
						status = NetworkStatusEnum.Mobile_2G;
						break;
					}
				}
				break;
			}
		}
		return status;
	}

	public enum NetworkStatusEnum {
		NoNetwork(0),
		WIFI(1),
		Mobile_2G(2),
		Mobile_3G(3),
		Mobile_4G(4),
		Others(5);

		private final int value;

		NetworkStatusEnum(int value) { this.value = value; }

		public int getValue() {return value;}

	}
}
