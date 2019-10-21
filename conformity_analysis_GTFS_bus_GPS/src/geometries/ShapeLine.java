package geometries;

import java.util.List;

import com.vividsolutions.jts.geom.LineString;

public class ShapeLine {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private LineString lineString;
	private List<ShapePoint> listShapePoints;
	private Float distanceTraveled;
	private String route;
	private int thresholdDistance;
		
	
	public ShapeLine(String id, LineString lineString, Float distanceTraveled) {
		this.id = id;
		this.lineString = lineString;
		this.distanceTraveled = distanceTraveled;
	}
	
	public ShapeLine(String id, LineString lineString, Float distanceTraveled,
			List<ShapePoint> listShapePoints, String route, float greaterDistancePoints) {
		this.id = id;
		this.lineString = lineString;
		this.listShapePoints = listShapePoints;
		this.distanceTraveled = distanceTraveled;
		this.route = route;
	}
	
	public String getRoute() {
		return this.route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Double getDistanceTraveled() {
		return Double.valueOf(distanceTraveled);
	}

	public void setDistanceTraveled(Float distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}
	
	public int getThresholdDistance() {
		return this.thresholdDistance;
	}

	public void setThresholdDistance(int thresholdDistance) {
		this.thresholdDistance = thresholdDistance;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LineString getLineString() {
		return lineString;
	}

	public void setLineString(LineString lineString) {
		this.lineString = lineString;
	}

	public List<ShapePoint> getListShapePoints() {
		return listShapePoints;
	}

	public void setListShapePoints(List<ShapePoint> listShapePoints) {
		this.listShapePoints = listShapePoints;
	}

//	@Override
//	public String toString() {
//		return "ShapeLine [distanceTraveled=" + distanceTraveled + ", route=" + route + ", thresholdDistance="
//				+ thresholdDistance + "]";
//	}

	@Override
	public String toString() {
		return getListShapePoints().toString();
	}
}