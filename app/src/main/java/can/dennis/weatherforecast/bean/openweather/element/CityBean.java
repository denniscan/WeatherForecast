package can.dennis.weatherforecast.bean.openweather.element;

import can.dennis.weatherforecast.itface.element.ICityBean;

/**
 * CityBean
 * Created by Dennis Can on 2017-06-30.
 */
public class CityBean implements ICityBean {
	/**
	 * id : 1809858
	 * name : Guangzhou
	 * coord : {"lat":23.1167,"lon":113.25}
	 * country : CN
	 */

	/** City ID */
	private String id;
	/** City name */
	private String name;
	private CoordBean coord;
	/** Country code (GB, JP etc.) */
	private String country;

	public String getId() { return id;}

	public void setId(String id) { this.id = id;}

	public String getName() { return name;}

	public void setName(String name) { this.name = name;}

	public CoordBean getCoord() { return coord;}

	public void setCoord(CoordBean coord) { this.coord = coord;}

	public String getCountry() { return country;}

	public void setCountry(String country) { this.country = country;}
}