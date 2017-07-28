package can.dennis.weatherforecast.utils.network.rx.subscriber;

import can.dennis.weatherforecast.exceptions.network.ResponseCodeException;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * RxAndroid框架OkHttp.Get方法Response封装基类
 * Created by Dennis Can on 2017-07-27.
 */
public abstract class BaseSubscriber<T> implements ObservableOnSubscribe<NetworkResponseDataPackage<T>> {
	protected final String url;
	protected OkHttpClient okClient;

	public BaseSubscriber(String url) { this.url = url; }

	@Override public void subscribe(@NonNull ObservableEmitter<NetworkResponseDataPackage<T>> e) throws Exception {
		A.log("GET url: " + url);
		Response response = okClient.newCall(new Request.Builder().get().url(url).build()).execute();
		int responseCode = response.code();
		if (responseCode == 200) {
			onResponse(e, responseCode, response);
		} else {
			e.onError(new ResponseCodeException(responseCode));
		}
	}

	public void setOkClient(OkHttpClient okClient) { this.okClient = okClient; }

	protected abstract void onResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response);
}
