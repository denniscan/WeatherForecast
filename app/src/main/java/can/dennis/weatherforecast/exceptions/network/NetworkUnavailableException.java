package can.dennis.weatherforecast.exceptions.network;

import can.dennis.weatherforecast.exceptions.network.base.BaseNetworkResponseException;
/**
 * NetworkUnavailableException
 * Created by Dennis Can on 2017-06-29.
 */
public class NetworkUnavailableException extends BaseNetworkResponseException {
	public NetworkUnavailableException() { super("网络不可用"); }
}
