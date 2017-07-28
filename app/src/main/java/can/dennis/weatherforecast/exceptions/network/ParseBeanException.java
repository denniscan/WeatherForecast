package can.dennis.weatherforecast.exceptions.network;

import can.dennis.weatherforecast.exceptions.network.base.BaseNetworkResponseException;
/**
 * ParseBeanException
 * Created by Dennis Can on 2017-06-29.
 */
public class ParseBeanException extends BaseNetworkResponseException {
	public ParseBeanException() {super("数据解析错误");}
}
