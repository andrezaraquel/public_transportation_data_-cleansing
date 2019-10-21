package preprocessing.cg;

public class GPS {

	private static final long serialVersionUID = 1L;
	// "bus.code,latitude,longitude,timestamp,line.code,gps.id"
	// "AL300",-25.439896,-49.222006,2015-10-19 06:13:04,"022",455

	private String busCode;
	private String latitude;
	private String longitude;
	private String timeStamp;
	private String lineCode;
	private String gpsId;
	private String time;

	private String separator;

	public GPS(String stringLine, String separator) throws Exception {

		String[] fields = stringLine.concat(" ").replace(",,", ", ,").replace("\"", "").split(separator);
		if (fields.length < 17) {
			throw new Exception("Some data is missing in GPS line.");
		}

		this.busCode = fields[12];
		this.latitude = fields[16];
		this.longitude = fields[6];
		
		this.lineCode = fields[9];
		this.gpsId = fields[0];
		this.separator = separator;
		
		this.timeStamp = fields[8];
		
//		2018-12-26 05:45:15 yyyy-MM-dd'T'HH:mm:ss.SSSZ
		
		String time = timeStamp.split(" ")[1];
		this.time = time;
		String[] yearMonthDay = timeStamp.split(" ")[0].split("-");
		String day = yearMonthDay[2];
		String month = yearMonthDay[1];
		String year = yearMonthDay[0];
		this.timeStamp = year+"-"+month+"-"+day+"T"+time+"Z";		
	}
	
	public GPS(String busCode, String latitude, String longitude, String timeStamp, String lineCode, String gpsId) {
		this.busCode = busCode;
		this.latitude = latitude;
		this.longitude = longitude;
		this.timeStamp = timeStamp;
		
//		2018-12-26 05:45:15 yyyy-MM-dd'T'HH:mm:ss.SSSZ
		
		String time = timeStamp.split(" ")[1];
		String[] yearMonthDay = timeStamp.split(" ")[0].split("-");
		String day = yearMonthDay[2];
		String month = yearMonthDay[1];
		String year = yearMonthDay[0];
		this.timeStamp = year+"-"+month+"-"+day+"T"+time+"Z";		
		this.lineCode = lineCode;
		this.gpsId = gpsId;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
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

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getGpsId() {
		return gpsId;
	}

	public void setGpsId(String gpsId) {
		this.gpsId = gpsId;
	}

	public String getSEPARATOR() {
		return separator;
	}
	
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return busCode + separator + latitude + separator + longitude + separator
				+ timeStamp + separator + lineCode + separator + gpsId;
	}
	
	//Track,Time,Latitude,Longitude,Elevation
	public String toStringMM() {
		return busCode + separator +  timeStamp + separator + latitude + separator+
				longitude + separator + "0";
	}
	
	
}
