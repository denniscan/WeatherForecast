package can.dennis.weatherforecast.utils.network.manager;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import can.dennis.weatherforecast.exceptions.network.ResponseCodeException;
import can.dennis.weatherforecast.exceptions.network.TextException;
import can.dennis.weatherforecast.utils.A;
import can.dennis.weatherforecast.utils.constants.Constants;
import can.dennis.weatherforecast.utils.network.manager.base.BaseNetworkManager;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * Bing图片网络工具类
 * Created by Dennis Can on 2017-07-27.
 */
public class BingPicManager extends BaseNetworkManager {
	public BingPicManager(OkHttpClient okClient) { super(okClient); }

	public Function<Boolean, ObservableSource<String>> getBingPicUrl() {
		return new Function<Boolean, ObservableSource<String>>() {
			@Override public ObservableSource<String> apply(@NonNull final Boolean aBoolean) throws Exception {
				return Observable.create(new ObservableOnSubscribe<String>() {
					@Override public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
						A.log("<-------- Get bing pic url -------->");
						if (!aBoolean) {
							A.log("接收到false, return");
							e.onComplete();
							return;
						}
						Response response = okClient
								.newCall(new Request.Builder().get().url(Constants.URL_BING_PIC).build())
								.execute();
						int responseCode = response.code();
						if (responseCode == 200) {
							String string = response.body().string();
							if (!TextUtils.isEmpty(string)) {
								A.log("网络获取到BingPic地址: " + string);
								e.onNext(string);
								e.onComplete();
							} else {
								e.onError(new TextException("Response bing address is empty"));
							}
						} else {
							e.onError(new ResponseCodeException(responseCode));
						}
					}
				});
			}
		};
	}

	public Function<String, ObservableSource<Bitmap>> loadBingPic(final Activity activity) {
		return new Function<String, ObservableSource<Bitmap>>() {
			@Override public ObservableSource<Bitmap> apply(@NonNull final String picAddress) throws Exception {
				return Observable.create(new ObservableOnSubscribe<Bitmap>() {
					@Override public void subscribe(@NonNull final ObservableEmitter<Bitmap> e) throws Exception {
						activity.runOnUiThread(new Runnable() {
							@Override public void run() {
								A.log("<-------- Load bing pic -------->");
								DrawableTypeRequest<String> request = Glide.with(activity).load(picAddress);
								request.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
								request.asBitmap().into(new SimpleTarget<Bitmap>() {
									@Override public void onResourceReady(Bitmap resource,
											GlideAnimation<? super Bitmap> glideAnimation) {
										if (resource != null) {
											A.log("获取BingPic成功");
											e.onNext(resource);
											e.onComplete();
										} else {
											e.onError(new TextException("获取图像失败"));
										}
									}
								});
							}
						});
					}
				});
			}
		};
	}

//	public Observable<Bitmap> refreshBingPic(final ImageView imageView) {
//		return Observable.create(new ObservableOnSubscribe<Void>() {
//			@Override public void subscribe(@NonNull ObservableEmitter<Void> e) throws Exception {
//				Drawable drawable =
//						Drawable.createFromPath(FileUtils.getInstance().getBingImageFile().getAbsolutePath());
//				if (drawable != null)
//					imageView.setImageDrawable(drawable);
//				if (drawable == null || needToRefreshBingImage())
//					e.onNext(null);
//				e.onComplete();
//			}
//
//			private boolean needToRefreshBingImage() {
//				return //
//						true || //
////						false || //
////						NetworkStatusUtils.getInstance().isWifiApn() && //
//						notDownloadedToday() //
//						;
//			}
//
//			private boolean notDownloadedToday() {
//				Calendar todayZero = CalendarUtils.getInstance().getTodayZero();
//				Calendar refreshTime = CalendarUtils
//						.getInstance()
//						.getCalendar(PreferenceUtils.getInstance().getBingImageRefreshTime());
//				return refreshTime.before(todayZero);
//			}
//		}).flatMap(new Function<Void, ObservableSource<String>>() {
//			@Override public ObservableSource<String> apply(@NonNull Void aVoid) throws Exception {
//				return Observable.create(new ObservableOnSubscribe<String>() {
//					@Override public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
//						Response response = okClient
//								.newCall(new Request.Builder().get().url(Constants.URL_BING_PIC).build())
//								.execute();
//						int responseCode = response.code();
//						if (responseCode == 200) {
//							String string = response.body().string();
//							if (!TextUtils.isEmpty(string)) {
//								e.onNext(string);
//								e.onComplete();
//							} else {
//								e.onError(new TextException("Response string is empty"));
//							}
//						} else {
//							e.onError(new ResponseCodeException(responseCode));
//						}
//					}
//				});
//			}
//		}).flatMap(new Function<String, ObservableSource<Bitmap>>() {
//			@Override public ObservableSource<Bitmap> apply(@NonNull final String picAddress) throws Exception {
//				return Observable.create(new ObservableOnSubscribe<Bitmap>() {
//					@Override public void subscribe(@NonNull final ObservableEmitter<Bitmap> e) throws Exception {
//						DrawableTypeRequest<String> request = Glide.with(imageView.getContext()).load(picAddress);
//						request.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE);
//						request.asBitmap().into(new SimpleTarget<Bitmap>() {
//							@Override public void onResourceReady(Bitmap resource,
//									GlideAnimation<? super Bitmap> glideAnimation) {
//								if (resource != null) {
//									e.onNext(resource);
//									e.onComplete();
//								} else {
//									e.onError(new TextException("获取图像失败"));
//								}
//							}
//						});
//					}
//				});
//			}
//		});
//	}
}
