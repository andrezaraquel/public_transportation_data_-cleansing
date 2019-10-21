package geometries;

import java.io.Serializable;

import org.locationtech.spatial4j.context.jts.JtsSpatialContext;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class GeoObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7175535270215597873L;
	protected Geometry geometry;
	protected Integer idGeometry;
	protected String geoName;
	protected Integer idInDataset;
	
	

	public GeoObject(String geometryGIS, Integer idGeometry,
			String geoName, Integer idInDataset) throws ParseException {
		super();
		WKTReader wktReader = new WKTReader(JtsSpatialContext.GEO.getGeometryFactory());
		Geometry geometry = wktReader.read(geometryGIS);
		
		this.geometry = geometry;
		this.idGeometry = idGeometry;
		this.geoName = geoName;
		this.idInDataset = idInDataset;
	}
	
	public GeoObject(){
		
	}

	public Integer getIdGeometry() {
		return idGeometry;
	}

	public void setIdGeometry(Integer idGeometry) {
		this.idGeometry = idGeometry;
	}

	public String getGeoName() {
		return geoName;
	}

	public void setGeoName(String geoName) {
		this.geoName = geoName;
	}

	public Integer getIdInDataset() {
		return idInDataset;
	}

	public void setIdInDataset(Integer idInDataset) {
		this.idInDataset = idInDataset;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}
	
	

}

