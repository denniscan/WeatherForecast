package can.dennis.weatherforecast.utils.network.manager.base;

import can.dennis.weatherforecast.utils.network.NetworkUtils;
import can.dennis.weatherforecast.utils.network.bean.datapackage.NetworkResponseDataPackage;
import can.dennis.weatherforecast.utils.network.rx.subscriber.BaseSubscriber;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import okhttp3.OkHttpClient;
/**
 * Base network manager
 * Created by Dennis Can on 2017-06-29.
 */
public abstract class BaseNetworkManager {
	protected final OkHttpClient okClient;

	protected BaseNetworkManager(OkHttpClient okClient) {this.okClient = okClient;}

	protected class ObservableBuilder<T> {
		private final BaseSubscriber<T> subscriber;

		public ObservableBuilder(BaseSubscriber<T> subscriber) {
			this.subscriber = subscriber;
			subscriber.setOkClient(okClient);
		}

		public ObservableSource<NetworkResponseDataPackage<T>> build() {
			if (!NetworkUtils.isNetworkAvailable())
				return null;

			return Observable.create(subscriber);
		}
	}
}
