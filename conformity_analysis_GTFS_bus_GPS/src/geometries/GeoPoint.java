package geometries;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Point;

public abstract class GeoPoint extends GeoObject implements Serializable, Comparable<GeoPoint>{

	//shape: 3217,-25.4757686477818,-49.2923877163312,3281146,24.441
	//stopLine: 3167295,"06:11:12","06:11:12",25681,2,1,"Terminal Campina do Siqueira",0,
	//301,"022","INTER 2 (HORÃRIO)",3,"FF0000","EstaÃ§Ã£o Tubo Santa QuitÃ©ria",-25.459129997717,-49.302406792031,3217
	//gpsLine: "AL300",-25.440416,-49.220878,2015-10-19 06:13:33,"022"
	
	private static final long serialVersionUID = 1L;
	private String latitude;
	private String longitude;
	
	public GeoPoint(String latitude, String longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public String getBlockingKey(int range){
		String latitudePart = this.getLatitude();
		if(latitudePart.length() < range) {
			for (int i = 0; i < range - this.getLatitude().length(); i++) {
				latitudePart += "*";
			}
		}
		
		if (this.getLatitude().startsWith("-")) {
			latitudePart = latitudePart.substring(0, range);
		} else {
			latitudePart = latitudePart.substring(0, range-1);
		}
		
		String longitudePart = this.getLongitude();
		if(longitudePart.length() < range) {
			for (int i = 0; i < range - this.getLongitude().length(); i++) {
				longitudePart += "*";
			}
		}
		
		if (this.getLongitude().startsWith("-")) {
			longitudePart = longitudePart.substring(0, range);
		} else {
			longitudePart = longitudePart.substring(0, range-1);
		}
		
		return latitudePart + longitudePart;
	}
	
	public static float getDistanceInMeters(double lat1, double lng1, double lat2, double lng2) {
		final double earthRadius = 6371000; // meters
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		float dist = (float) (earthRadius * c);

		return dist;
	}
	
	public static float getDistanceInMeters(Point point1, Point point2) {
		return getDistanceInMeters(point1.getX(), point1.getY(), point2.getX(), point2.getY());
	}
	
	public static float getDistanceInMeters(GeoPoint point1, GeoPoint point2) {
		Double p1Lat = Double.valueOf(point1.getLatitude());
		Double p1Lon = Double.valueOf(point1.getLongitude());
		Double p2Lat = Double.valueOf(point2.getLatitude());
		Double p2Lon = Double.valueOf(point2.getLongitude());
		return getDistanceInMeters(p1Lat, p1Lon, 
				p2Lat, p2Lon);
	}
}
