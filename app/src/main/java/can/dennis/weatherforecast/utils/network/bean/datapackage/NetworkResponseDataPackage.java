package can.dennis.weatherforecast.utils.network.bean.datapackage;

/**
 * 网络反馈信息封装包
 * Created by Dennis Can on 2017-07-27.
 */
public class NetworkResponseDataPackage<T> {
	public final String json;
	public final T bean;

	public NetworkResponseDataPackage(String json, T bean) {
		this.json = json;
		this.bean = bean;
	}
}
