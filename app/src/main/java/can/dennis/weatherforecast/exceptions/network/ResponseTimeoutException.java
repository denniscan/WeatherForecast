package can.dennis.weatherforecast.exceptions.network;

import can.dennis.weatherforecast.exceptions.network.base.BaseNetworkResponseException;
/**
 * ResponseTimeoutException
 * Created by Dennis Can on 2017-06-29.
 */
public class ResponseTimeoutException extends BaseNetworkResponseException {
	public ResponseTimeoutException() { super("连接超时"); }
}
