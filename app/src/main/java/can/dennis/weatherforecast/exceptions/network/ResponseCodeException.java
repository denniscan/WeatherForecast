package can.dennis.weatherforecast.exceptions.network;

/**
 * Http响应码异常
 * Created by Dennis Can on 2017-07-27.
 */
public class ResponseCodeException extends TextException {
	public ResponseCodeException(int responseCode) {super("Response code == " + responseCode);}
}
