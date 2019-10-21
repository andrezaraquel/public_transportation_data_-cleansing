package core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BoRObject {
	
	private Map<String, Integer> mapEdges;
	private Map<String, Integer> bagOfRoadsMap;
	private String stringSequenceOfRoads;
	private Double rareSubsSize;
	private Double directionWeight;
	
	
	
	public Double getDirectionWeight() {
		return directionWeight;
	}


	public void setDirectionWeight(Double directionWeight) {
		this.directionWeight = directionWeight;
	}


	public String getStringSequenceOfRoads() {
		return stringSequenceOfRoads;
	}


	public void setStringSequenceOfRoads(String sequenceOfRoads) {
		this.stringSequenceOfRoads = sequenceOfRoads;
	}

	public void pupulateSequenceOfRoads(String edge) {
		this.stringSequenceOfRoads += edge;
	}

	public Double getRareSubsSize() {
		return rareSubsSize;
	}

	public void setRareSubsSize(Double rareSubsSize) {
		this.rareSubsSize = rareSubsSize;
	}

	public BoRObject() {
		mapEdges = new HashMap<String, Integer>();	
		bagOfRoadsMap  = new HashMap<>();
		this.stringSequenceOfRoads = "";
		rareSubsSize = Double.MAX_VALUE;
		this.directionWeight = Double.MIN_VALUE;
	}
	
	
	// this method modifies the bag of roads concept a little
	// instead of count the number times a object intersects the segment
	// it counts if the object intersect the segment
	public void addEdgeVisitation(String edgeId) {
		if (!mapEdges.containsKey(edgeId)) {
			mapEdges.put(edgeId, 1);
			stringSequenceOfRoads += edgeId;
		}
		
	}
	
//	public void addEdgeVisitation(String edgeId) {
//		if (!mapEdges.containsKey(edgeId)) {
//			mapEdges.put(edgeId, 0);
//		}
//		mapEdges.put(edgeId, mapEdges.get(edgeId) + 1);
//	}
	
	public Integer getMapEdgesSize() {
		return mapEdges.size();
	}
	
	public Integer getEdgeVisitations(String edgeID) {
		return mapEdges.remove(edgeID);
	}
	
	public Map<String, Integer> getBagOfRoadsMap() {
		return bagOfRoadsMap;
	}

	public void setBagOfRoadsMap(Map<String, Integer> bagOfRoadsMap) {
		this.bagOfRoadsMap = bagOfRoadsMap;
	}

	public void populateBoRMap(String edgeId) {
		Integer visitations = getEdgeVisitations(edgeId);
		
		if (visitations != null) {
			bagOfRoadsMap.put(edgeId, visitations);
		} 
	}
}
