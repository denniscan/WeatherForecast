package can.dennis.weatherforecast.utils.network.rx.subscriber;

import java.io.IOException;

import can.dennis.weatherforecast.exceptions.network.TextException;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import io.reactivex.ObservableEmitter;
import okhttp3.Response;
/**
 * RxAndroid框架OkHttp.Get方法String Response封装基类
 * Created by Dennis Can on 2017-07-27.
 */
public abstract class StringSubscriber<T> extends BaseSubscriber<T> {
	public StringSubscriber(String url) {super(url);}

	@Override protected void onResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response) {
		try {
			String string = response.body().string();
			onStringResponse(e, responseCode, response, string);
		} catch (IOException e1) {
			e1.printStackTrace();
			e.onError(new TextException("Error on get response body string"));
		}
	}

	protected abstract void onStringResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response, String string);
}
