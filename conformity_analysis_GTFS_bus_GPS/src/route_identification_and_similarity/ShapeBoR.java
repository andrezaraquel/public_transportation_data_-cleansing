package core;

public class ShapeBoR extends BoRObject {
	
	private String route;
	
	public ShapeBoR(String route) {
		super();
		this.route = route;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getRouteNumberOnly() {
		return route.substring(route.indexOf("e")+1, route.indexOf("_"));
	}
	
}
