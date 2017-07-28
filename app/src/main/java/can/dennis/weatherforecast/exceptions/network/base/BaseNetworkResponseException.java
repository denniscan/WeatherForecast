package can.dennis.weatherforecast.exceptions.network.base;

import java.io.IOException;

import can.dennis.weatherforecast.utils.A;
/**
 * Base network response exception
 * Created by Dennis Can on 2017-06-29.
 */
public abstract class BaseNetworkResponseException extends IOException {
	protected BaseNetworkResponseException(String message) {
		super(message);
		A.log("Network response exception: " + message);
	}
}
