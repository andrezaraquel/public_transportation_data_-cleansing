package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import geometries.IncrementedShape;

/**
 * This class separates all shapes according to its route.
 * @author andreza
 *
 */
public class SeparateShapeFile {
	
	private static String INPUT_SHAPE = "";
	private static String INPUT_HEADER = "route_id,shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled";
	private static String OUTPUT_DIR = "";
	private static String FILE_SEPARATOR = ",";
	private static String OUTPUT_HEADER = "Track,Time,Latitude,Longitude,Elevation";
	
	private static Map<String,String> mapRouteShapesIds = new HashMap<String, String>();
	
	// file name should have the route_id of the shape line
	// output file should have the following header: Track,Time,Latitude,Longitude,Elevation
	public static void main(String[] args) {
		
		Map<String, List<IncrementedShape>> mapShapes = readFile(INPUT_SHAPE, FILE_SEPARATOR);
		for (String route: mapShapes.keySet()) {
			writeLines(mapShapes.get(route), OUTPUT_HEADER, OUTPUT_DIR + mapRouteShapesIds.get(route) + ".csv");
		}
		System.out.println("Files generated!");
		
	}

	private static void writeLines(List<IncrementedShape> shapeLines, String outputHheader, String outputPath) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {

			fw = new FileWriter(outputPath);
			bw = new BufferedWriter(fw);
			
//			bw.write(outputHheader);
			for (IncrementedShape s : shapeLines) {
//				bw.newLine();
				bw.write(s.toString());
				bw.newLine();
			}
			System.out.println("File created: " + outputPath);
			
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();
			} catch (IOException ex) {
				System.err.format("IOException: %s%n", ex);
			}
		}
		
		
	}
	
	private static Map<String, List<IncrementedShape>> readFile(String filePath, String separator) {
		BufferedReader br = null;
		FileReader fr = null;
		Map<String, List<IncrementedShape>> listOutput = new HashMap<String, List<IncrementedShape>>();

		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String sCurrentLine = br.readLine();

			String currentShapeId = "";
			
			while ((sCurrentLine = br.readLine()) != null) {
				try {
					IncrementedShape shape = new IncrementedShape(sCurrentLine, separator);
					
					
					String routeID = shape.getRouteId();
					if (!listOutput.containsKey(routeID)) {
						listOutput.put(routeID, new LinkedList<IncrementedShape>());
						mapRouteShapesIds.put(routeID, "Route"+routeID+"_shapes");
					} 
					listOutput.get(routeID).add(shape);
					
									
					if (!shape.getShapeId().equals(currentShapeId)) {
						
						String newStringMapRoute = mapRouteShapesIds.get(routeID).concat("_"+shape.getShapeId());
						mapRouteShapesIds.put(routeID, newStringMapRoute);
					}
					currentShapeId = shape.getShapeId();	
					
				} catch (Exception e) {
					System.out.println(sCurrentLine);
					// System.err.println(e);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return listOutput;
	}
}
