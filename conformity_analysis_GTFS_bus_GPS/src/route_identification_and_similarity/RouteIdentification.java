package route_identification_and_similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classifies the GPS files into one of the shape routes.
 * 
 * @author Andreza
 */
public class RouteIdentification {
	
//	 NOTE: This class works for CityA and Curitiba data

	// GPS and shapes files should have the format: "*_mr.txt"
	// gps file should have the bus_id and route as a name
	private static final String GPS_DIR = "";
	private static final String SHAPE_DIR = "";

	private static final String OUTPUT_FILE = "";
	private static final String CITY = "";
	private static final String OUTPUT_HEADER_FOR_ANALYSIS = "busid;found_route;similarity_found_route_gps;"
			+ "gold_standard_route;similarity_found_route_gold_standard_route;city;routesMatch";
	private static final String SEPARATOR = ";";
	private static final String INPUT_FORMAT = "_mr.txt";
	private static final String NAME_FILE_SPLIT = ";";

	private static Set<Edge> edgesSet = new HashSet<Edge>();
	private static List<GPSBoR> gpsList = new LinkedList<GPSBoR>();
	private static Map<String, ShapeBoR> shapesList = new HashMap<String, ShapeBoR>();

	public static void main(String[] args) {

		readFilesAndPopulateListsGpsAndShapes();

		System.out.println("[LOG] Populating BoR lists");
		for (Edge edge : edgesSet) {

			for (BoRObject gps_bor : gpsList) {
				gps_bor.populateBoRMap(edge.getEdgeID());
			}

			for (BoRObject shape_bor : shapesList.values()) {
				shape_bor.populateBoRMap(edge.getEdgeID());
			}

		}

		System.out.println("[LOG] Getting most similar shape");
		calculateSimilarity();
		System.out.println("[LOG] Printing Results");
		saveFileForAnalysis(OUTPUT_FILE);
		System.out.println("DONE!");

	}


	private static void readFilesAndPopulateListsGpsAndShapes() {
		final File gps_folder = new File(GPS_DIR);
		final File shape_folder = new File(SHAPE_DIR);

		for (final File fileEntry : shape_folder.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(INPUT_FORMAT)) {
				ShapeBoR shape_bor;
				try {
					// shape file should have the route as a name
					shape_bor = new ShapeBoR(removeExtension(fileEntry.getName()));
					readMRfile(fileEntry.getAbsolutePath(), shape_bor, true);
					
					shapesList.put(shape_bor.getRouteNumberOnly(), shape_bor);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		for (final File fileEntry : gps_folder.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.getName().endsWith(INPUT_FORMAT)) {
				GPSBoR gps_bor;
				try {
					// gps file should have the bus_id and route as a name
					// <busid-route>
					String[] params = removeExtension(fileEntry.getName()).split(NAME_FILE_SPLIT);

					if (shapesList.get(params[1]) != null) {
						Set<String> routes = new HashSet<String>();
						routes.add(params[1]);
//						gps_bor = new GPSBoR(params[0], routes, params[2]);
						gps_bor = new GPSBoR(params[0], routes, null);
						readMRfile(fileEntry.getAbsolutePath(), gps_bor, false);
						gpsList.add(gps_bor);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	/***
	 * Read files from GraphHopper MapMatching: Matching Result (MR) files.
	 * 
	 * @param pathFile
	 * @param bor
	 */
	private static void readMRfile(String pathFile, BoRObject bor, boolean isShapeFile) {
		BufferedReader br = null;
		FileReader fr = null;

		try {
			fr = new FileReader(pathFile);
			System.out.println("[LOG] Reading file: " + pathFile);
			br = new BufferedReader(fr);
			String sCurrentLine = br.readLine();
			String edgeIDString;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] edgeSplitted = sCurrentLine.split(SEPARATOR);
				edgeIDString = edgeSplitted[0];

				Edge newEdge = new Edge(edgeIDString, edgeSplitted[1], Double.valueOf(edgeSplitted[2]));

				// TODO comment conditional 'if' for BoR to have all edges from GPS
				// and shape MM result
				if (isShapeFile) {
					edgesSet.add(newEdge);
				}

				bor.addEdgeVisitation(newEdge.getEdgeID());
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

	}
	
	private static void calculateSimilatityWithRealRoute(GPSBoR gps_bor) {

		Double similarity = 0.;
		String routeCosineSimilar = gps_bor.getRouteCosineSimilar();
		
		for (String realRoute: gps_bor.getRealRoutes()) {
			
			try {
				similarity += Similarity.cosineSimilarity(shapesList.get(routeCosineSimilar).getBagOfRoadsMap(),
						shapesList.get(realRoute).getBagOfRoadsMap());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		gps_bor.setSimilarityWithRealRoute(similarity/gps_bor.getRealRoutes().size());
		
	}
	
	private static void saveFileForAnalysis(String outFile) {

		try (FileWriter writer = new FileWriter(outFile); BufferedWriter bw = new BufferedWriter(writer)) {

			bw.write(OUTPUT_HEADER_FOR_ANALYSIS);
			bw.newLine();
			for (GPSBoR gps_bor : gpsList) {

				calculateSimilatityWithRealRoute(gps_bor);
				
				bw.write(gps_bor.getBusID() + SEPARATOR + gps_bor.getRouteCosineSimilar() + SEPARATOR+
						 gps_bor.getMaxCosineSimilarity() + SEPARATOR + gps_bor.getRealRoutes() + SEPARATOR +
						gps_bor.getSimilarityWithRealRoute() + SEPARATOR + CITY + SEPARATOR + gps_bor.cosineMacthesRealRoute());
				bw.newLine();
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

	}

	private static void saveNewResultSimilarity (String outFile) {

			try (FileWriter writer = new FileWriter(outFile); BufferedWriter bw = new BufferedWriter(writer)) {

				Integer truePositives = 0;
				Integer losts = 0;

				bw.write("------------- Result -------------");
				bw.newLine();
				for (GPSBoR gps_bor : gpsList) {

					// calculate hits of cosine distance
					if (gps_bor.getRealRoutes().contains(gps_bor.getRouteCosineSimilar())) {
						truePositives++;
					} else {
						losts++;
					}

					
//					if (gps_bor.getRealRoutes().contains(gps_bor.getRouteCosineSimilar())) {
						bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " - Route SIM: "
								+ gps_bor.getRouteCosineSimilar() + " | SIM: " + gps_bor.getMaxCosineSimilarity());
						bw.newLine();

//					} 
					bw.newLine();
				}

				bw.write("Hits:" + truePositives);
				bw.newLine();
				bw.write("Losts:" + losts);
				bw.newLine();
				bw.write("Accuracy:" + (0.0+truePositives) / (truePositives+losts));
				bw.newLine();
				bw.write("------------- DONE -------------");

			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}

		}
		

	private static void calculateSimilarity() {
		Double similarity;
		int cont = 0;
		for (GPSBoR gps_bor : gpsList) {
			System.out.println("#" + ++cont + " de " + gpsList.size());
			for (ShapeBoR shape_bor : shapesList.values()) {
				try {
					similarity = Similarity.cosineSimilarity(gps_bor.getBagOfRoadsMap(),
							shape_bor.getBagOfRoadsMap());

					if (shape_bor.getRoute().equals("101") && gps_bor.getBusID().equals("1033")) {
						System.out.println();
					}

					if (similarity > gps_bor.getMaxCosineSimilarity()) {
						gps_bor.setMaxCosineSimilarity(similarity);
						gps_bor.setRouteCosineSimilar(shape_bor.getRouteNumberOnly());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	private static String removeExtension(String name) throws Exception {
		if (name.length() <= 7) {
			throw new Exception("Invalid name");
		}
		return name.substring(0, name.length() - 7);
	}

}
