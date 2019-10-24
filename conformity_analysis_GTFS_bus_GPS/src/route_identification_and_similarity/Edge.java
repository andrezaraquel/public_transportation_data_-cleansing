package route_identification_and_similarity;

public class Edge {

	private String edgeID;
	private String edgeName;
	private Double edgeDistance;
	
	public Edge(String edgeID, String edgeName, Double edgeDistance) {
		this.edgeID = edgeID;
		this.edgeName = edgeName;
		this.edgeDistance = edgeDistance;
	}

	public String getEdgeID() {
		return edgeID;
	}

	public void setEdgeID(String edgeID) {
		this.edgeID = edgeID;
	}

	public String getEdgeName() {
		return edgeName;
	}

	public void setEdgeName(String edgeName) {
		this.edgeName = edgeName;
	}

	public Double getEdgeDistance() {
		return edgeDistance;
	}

	public void setEdgeDistance(Double edgeDistance) {
		this.edgeDistance = edgeDistance;
	}
}
