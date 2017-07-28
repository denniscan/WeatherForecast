package can.dennis.weatherforecast.exceptions.network;

import can.dennis.weatherforecast.exceptions.network.base.BaseNetworkResponseException;
/**
 * TextException
 * Created by Dennis Can on 2017-06-29.
 */
public class TextException extends BaseNetworkResponseException {
	public TextException(String message) {super(message);}
}
