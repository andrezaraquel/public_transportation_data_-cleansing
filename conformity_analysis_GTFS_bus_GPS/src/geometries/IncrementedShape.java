package geometries;

/**
 * This class is to represent shape points after MergeGTFSFiles class was executed.
 * @author andreza
 */
public class IncrementedShape {
		
		private String routeId,shapeId,latitude,longitude,sequence,distTraveled;
		private String separator;

		public IncrementedShape(String shapeLine, String separator) throws Exception {
			
			String[] shapeSplitted = shapeLine.split(separator);
			
			if (shapeSplitted.length < 6) {
				throw new Exception("Shape line should have the fields: 'route_id,shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled'");
			}
			
			this.routeId = shapeSplitted[0];
			this.shapeId = shapeSplitted[1];
			this.latitude = shapeSplitted[2];
			this.longitude = shapeSplitted[3];
			this.sequence = shapeSplitted[4];
			this.distTraveled = shapeSplitted[5];
			this.separator = separator;
		}
		
		public String getRouteId() {
			return routeId;
		}


		public void setRouteId(String routeId) {
			this.routeId = routeId;
		}


		public String getShapeId() {
			return shapeId;
		}


		public void setShapeId(String shapeId) {
			this.shapeId = shapeId;
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


		public String getSequence() {
			return sequence;
		}


		public void setSequence(String sequence) {
			this.sequence = sequence;
		}


		public String getDistTraveled() {
			return distTraveled;
		}


		public void setDistTraveled(String distTraveled) {
			this.distTraveled = distTraveled;
		}


		@Override
		public String toString() {
			//output for MM algorithm: Track,Time,Latitude,Longitude,Elevation
			return routeId + separator + "0" + separator + latitude + separator + longitude + separator + "0";  
		}
		
	}

