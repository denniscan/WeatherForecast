package can.dennis.weatherforecast.utils.networkhelper;

import can.dennis.weatherforecast.R;
import can.dennis.weatherforecast.utils.app.MyApp;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
/**
 * RxAndroid 网络帮助类
 * Created by Dennis Can on 2017-07-27.
 */
public class NetworkHelper {
	private final CompositeDisposable compositeDisposable;
	private final IOnNetworkStatusErrorFeedback feedback;

	public NetworkHelper(IOnNetworkStatusErrorFeedback feedback) {
		this.feedback = feedback;
		this.compositeDisposable = new CompositeDisposable();
	}

	public <T> void loadNetwork(ObservableSource<? extends T> source1, ObservableSource<? extends T> source2,
			DisposableObserver<T> disposableObserver) {
		if (source1 == null || source2 == null) {
			MyApp.showToast(R.string.networkNotAvailable_pleaseCheckNetworkStatus);
			if (feedback != null)
				feedback.onNetworkStatusError();
			return;
		}
		loadNetwork(Observable.concat(source1, source2), disposableObserver);
	}

	public <T> void loadNetwork(ObservableOnSubscribe<? extends T> observableOnSubscribe,
			DisposableObserver<T> disposableObserver) {
		if (observableOnSubscribe == null) {
			MyApp.showToast(R.string.networkNotAvailable_pleaseCheckNetworkStatus);
			if (feedback != null)
				feedback.onNetworkStatusError();
			return;
		}
		loadNetwork(Observable.create(observableOnSubscribe), disposableObserver);
	}

	public <T> void loadNetwork(Observable<? extends T> observable, DisposableObserver<T> disposableObserver) {
		if (observable == null) {
			MyApp.showToast(R.string.networkNotAvailable_pleaseCheckNetworkStatus);
			if (feedback != null)
				feedback.onNetworkStatusError();
			return;
		}
		compositeDisposable.add(observable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeWith(disposableObserver));
	}

	public void clear() { compositeDisposable.clear(); }

	// <-------- Communicator -------->

	public interface IOnNetworkStatusErrorFeedback {
		void onNetworkStatusError();
	}
}
