package can.dennis.weatherforecast.exceptions.network;

import can.dennis.weatherforecast.exceptions.network.base.BaseNetworkResponseException;
/**
 * CancelNetworkResponseException
 * Created by Dennis Can on 2017-06-29.
 */
public class CancelNetworkResponseException extends BaseNetworkResponseException {
	public CancelNetworkResponseException() { super("用户取消"); }
}
