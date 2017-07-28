package can.dennis.weatherforecast.utils.network.rx.subscriber;

import can.dennis.weatherforecast.exceptions.network.ParseBeanException;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import io.reactivex.ObservableEmitter;
import okhttp3.Response;
/**
 * Created by Dennis Can on 2017-07-27.
 */
public abstract class Subscriber<T> extends JsonBeanSubscriber<T> {
	public Subscriber(String url) {super(url);}

	@Override protected void onJsonBeanResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response, String string, T bean) {
		if (bean != null) {
			e.onNext(new NetworkResponseDataPackage<>(string, bean));
			e.onComplete();
		} else {
			e.onError(new ParseBeanException());
		}
	}
}
