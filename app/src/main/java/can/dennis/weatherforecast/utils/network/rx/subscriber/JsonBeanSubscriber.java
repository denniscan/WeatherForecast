package can.dennis.weatherforecast.utils.network.rx.subscriber;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import can.dennis.weatherforecast.exceptions.network.ParseBeanException;
import can.dennis.weatherforecast.exceptions.network.TextException;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import io.reactivex.ObservableEmitter;
import okhttp3.Response;
public abstract class JsonBeanSubscriber<T> extends StringSubscriber<T> {
	private T bean;

	public JsonBeanSubscriber(String url) {super(url);}

	@Override protected void onStringResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response, String string) {
		if (string != null) {
//			A.log("json: " + string);
			try {
				bean = parseResponse(string);
				onJsonBeanResponse(e, responseCode, response, string, bean);
			} catch (Throwable throwable) {
				throwable.printStackTrace();
				e.onError(new ParseBeanException());
			}
		} else {
			e.onError(new TextException("ResponseString == null"));
		}
	}

	/**
	 * 返回gson类型
	 */
	private T parseResponse(String rawJsonData) throws Throwable {
		Type superclass = getClass().getGenericSuperclass();
		if (superclass instanceof Class)
			throw new IllegalArgumentException("Missing type parameter.");
		ParameterizedType parameterized = (ParameterizedType) superclass;
		Type type = $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
		T t = new Gson().fromJson(rawJsonData, type);
		return t;
	}

	protected abstract void onJsonBeanResponse(ObservableEmitter<NetworkResponseDataPackage<T>> e, int responseCode,
			Response response, String string, T bean);
}
