package route_identification_and_similarity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class GPSBoR extends BoRObject {

	
	private Double maxCosineSimilarity;
	private Double maxWeightedCosineSimilarity;
	private Double maxJaccardSimilarity;
	private Double minManhattanDistance;
	private Double minEuclideanDistance;
	
	private String routeCosineSimilar;
	private String routeWeightedCosineSimilar;	
	private String routeJaccardSimilar;
	private String routeManhattanDistant;
	private String routeEuclideanDistant;
	private String mostVotedRoute;
	
	private Set<String> realRoutes;	
	private String busID;
	private String timeInterval;
	private Double similarityWithRealRoute;
	
	public GPSBoR(String busID, Set<String> realRoutes, String timeInterval) {
		super();
		this.routeCosineSimilar = "";
		this.routeWeightedCosineSimilar = "";
		this.routeJaccardSimilar = "";
		this.routeManhattanDistant = "";
		this.routeEuclideanDistant = "";
		this.mostVotedRoute = "";
		
		this.maxCosineSimilarity = Double.MIN_VALUE;
		this.maxWeightedCosineSimilarity = Double.MIN_VALUE;
		this.maxJaccardSimilarity = Double.MIN_VALUE;
		this.minManhattanDistance = Double.MAX_VALUE;
		this.minEuclideanDistance = Double.MAX_VALUE;
		
		this.realRoutes = realRoutes;
		this.busID = busID;
		this.timeInterval = timeInterval;
	}

	public Double getSimilarityWithRealRoute() {
		return similarityWithRealRoute;
	}

	public void setSimilarityWithRealRoute(Double similarityWithRealRoute) {
		this.similarityWithRealRoute = similarityWithRealRoute;
	}

	public String getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(String timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getRouteCosineSimilar() {
		return routeCosineSimilar;
	}

	public void setRouteCosineSimilar(String routeCosineSimilar) {
		this.routeCosineSimilar = routeCosineSimilar;
	}

	public String getRouteWeightedCosineSimilar() {
		return routeWeightedCosineSimilar;
	}

	public void setRouteWeightedCosineSimilar(String routeWeightedCosineSimilar) {
		this.routeWeightedCosineSimilar = routeWeightedCosineSimilar;
	}

	public Set<String> getRealRoutes() {
		return realRoutes;
	}

	public void setRealRoutes(Set<String> realRoutes) {
		this.realRoutes = realRoutes;
	}

	public Double getMaxCosineSimilarity() {
		return maxCosineSimilarity;
	}

	public void setMaxCosineSimilarity(Double maxCosineSimilarity) {
		this.maxCosineSimilarity = maxCosineSimilarity;
	}

	public Double getMaxWeightedCosineSimilarity() {
		return maxWeightedCosineSimilarity;
	}

	public void setMaxWeightedCosineSimilarity(Double maxWeightedCosineSimilarity) {
		this.maxWeightedCosineSimilarity = maxWeightedCosineSimilarity;
	}

	public Double getMaxJaccardSimilarity() {
		return maxJaccardSimilarity;
	}

	public void setMaxJaccardSimilarity(Double maxJaccardSimilarity) {
		this.maxJaccardSimilarity = maxJaccardSimilarity;
	}

	public Double getMinManhattanDistance() {
		return minManhattanDistance;
	}

	public void setMinManhattanDistance(Double maxManhattanDistance) {
		this.minManhattanDistance = maxManhattanDistance;
	}

	public Double getMinEuclideanDistance() {
		return minEuclideanDistance;
	}

	public void setMinEuclideanDistance(Double maxEuclideanDistance) {
		this.minEuclideanDistance = maxEuclideanDistance;
	}

	public String getRouteJaccardSimilar() {
		return routeJaccardSimilar;
	}

	public void setRouteJaccardSimilar(String routeJaccardSimilar) {
		this.routeJaccardSimilar = routeJaccardSimilar;
	}

	public String getRouteManhattanDistant() {
		return routeManhattanDistant;
	}

	public void setRouteManhattanDistant(String routeManhattanDistant) {
		this.routeManhattanDistant = routeManhattanDistant;
	}

	public String getRouteEuclideanDistant() {
		return routeEuclideanDistant;
	}

	public void setRouteEuclideanDistant(String routeEuclideanDistant) {
		this.routeEuclideanDistant = routeEuclideanDistant;
	}
	
	public String getMostVotedRoute() {
		return mostVotedRoute;
	}

	public void setMostVotedRoute() {
		Map<String, Integer> routes = new HashMap<>();
		addToRoutesFoundedMap(routes, routeCosineSimilar);
		addToRoutesFoundedMap(routes, routeJaccardSimilar);
		addToRoutesFoundedMap(routes, routeManhattanDistant);
		addToRoutesFoundedMap(routes, routeEuclideanDistant);
		
		this.mostVotedRoute = findMostVotedRoute(routes);
	}

	private String findMostVotedRoute(Map<String, Integer> map) {
		Integer votes = 0; 
		String route = null;
		for ( Entry<String, Integer> entry : map.entrySet()) {
			if (entry.getValue() > votes) {
				votes = entry.getValue();
				route = entry.getKey();
			}
		}
		return route;
	}

	private void addToRoutesFoundedMap(Map<String, Integer> map, String route) {
		if (map.containsKey(route)) {
			map.put(route, map.get(route) + 1);
		} else {
			map.put(route, 1);
		}
	}

	public String getBusID() {
		return busID;
	}

	public void setBusID(String busID) {
		this.busID = busID;
	}

	public boolean cosineMacthesRealRoute() {
		for (String realRoute: getRealRoutes()) {
			if (routeCosineSimilar.equals(realRoute)) {
				return true;
			}
		}
		return false;
	}
}
