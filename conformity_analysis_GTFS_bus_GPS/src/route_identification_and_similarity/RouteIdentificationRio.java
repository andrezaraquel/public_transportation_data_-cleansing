package route_identification_and_similarity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Classifies the GPS files into one of the shape routes.
 * 
 * @author Andreza
 */
public class RouteIdentificationRio {

//	 NOTE: This class works for Rio de Janeiro data as I have received it
	
	private static final String FILE_SEPARATOR = ",";
	private static final String OUTPUT_FILE = "../data/outputs/analysis/Rio.csv";
	private static final String CITY = "Rio de Janeiro";
	private static final String OUTPUT_HEADER_FOR_ANALYSIS = "busid;found_route;similarity_found_route_gps;"
			+ "gold_standard_route;similarity_found_route_gold_standard_route;city;routesMatch";
	private static final String SEPARATOR = ";";
	private static final String[] TYPE_FILE = { "GPS", "GTFS", "ROUTE" };

	// Map<EdgeId, Edge(edgeId, edgename,EdgeDistance)>
	private static Map<String, Edge> allEdgesFromMap = new HashMap<String, Edge>();
	// Map<shape_route, shapebor>
	private static Map<String, ShapeBoR> shapesMap = new HashMap<String, ShapeBoR>();
	// Map<busId, List<route>> because some gps files have more than one route
	// (the data is a bit inconsistent)
	private static Map<String, Set<String>> realRoutesMap = new HashMap<String, Set<String>>();	
	
	
	private static Set<Edge> edgesFromMapMatchShapes = new HashSet<Edge>();
	private static Set<Edge> edgesFromMapMatchShapesAndGPS = new HashSet<Edge>();
	private static List<GPSBoR> gpsList = new ArrayList<GPSBoR>();	
	
	private static Map<String, Integer> currentBagOfRoadsGPS = new HashMap<String, Integer>();
	
	

	public static void main(String[] args) {

		if (args.length < 6) {
			System.err.println(
					"Usage: <DIR_GPS_MAP_MATCHED><DIR_GTFS_MAP_MATCHED><DIR_ROUTE_MAP_MATCHED><FILE_MAP_RIO><DIR_REAL_ROUTES_FROM_GPS><OUTPUT_FILE>");
			System.exit(0);
		}


		long initTime = System.currentTimeMillis();
		
		String DIR_GPS_MAP_MATCHED = args[0];
		String DIR_GTFS_MAP_MATCHED = args[1];
		String DIR_ROUTE_MAP_MATCHED = args[2];
		String FILE_MAP_RIO = args[3];
		String DIR_REAL_ROUTES_FROM_GPS = args[4];
		String OUTPUT_FILE = args[5];

		System.out.println("[LOG] Populating allEdgesFromMap map...");
		populateAllEdgesFromMap(FILE_MAP_RIO);

		System.out.println("[LOG] Reading shapes files...");
		readFilesAndPopulateMapOfShapes(DIR_GTFS_MAP_MATCHED, TYPE_FILE[1]);		
//		readFilesAndPopulateMapOfShapes(DIR_ROUTE_MAP_MATCHED, TYPE_FILE[2]);
		
		System.out.println("[LOG] shapes number: " + shapesMap.size());
		System.out.println("[LOG] Populating BoR Shapes lists...");		
		for (Edge edge : edgesFromMapMatchShapes) {
			for (BoRObject shape_bor : shapesMap.values()) {
				shape_bor.populateBoRMap(edge.getEdgeID());
			} 
		}
		System.out.println("[LOG] Shapes BOR size: "  + edgesFromMapMatchShapes.size());

		System.out.println("[LOG] Populating realRoutesMap map...");
		populateRealRoutesMap(DIR_REAL_ROUTES_FROM_GPS);

		System.out.println("[LOG] Reading GPS files...");
		readFilesAndPopulateListOfGps(DIR_GPS_MAP_MATCHED, TYPE_FILE[0]);

		System.out.println("[LOG] Printing Results...");
//		saveResultSimilarities(OUTPUT_FILE);
		saveFileForAnalysis(OUTPUT_FILE);
		
//		System.out.println("[LOG] Printing Results...");
//		saveResultsMostVotedRoute(OUTPUT_FILE);
		System.out.println("DONE!");

		long endTime = System.currentTimeMillis();
		System.out.println("Execution time: " + (endTime-initTime)/1000 + " seconds");
		
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
	
	private static void calculateSimilatityWithRealRoute(GPSBoR gps_bor) {

		Double similarity = 0.;
		String routeCosineSimilar = gps_bor.getRouteCosineSimilar();
		
		for (String realRoute: gps_bor.getRealRoutes()) {
			
			try {
				similarity += Similarity.cosineSimilarity(shapesMap.get(routeCosineSimilar).getBagOfRoadsMap(),
						shapesMap.get(realRoute).getBagOfRoadsMap());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		gps_bor.setSimilarityWithRealRoute(similarity/gps_bor.getRealRoutes().size());
		
	}


	private static void saveResultsMostVotedRoute(String outFile) {
		try (FileWriter writer = new FileWriter(outFile);
	             BufferedWriter bw = new BufferedWriter(writer)) {

			Integer truePositives = 0;
			Integer losts = 0;

			bw.write("------------- Result -------------");
			bw.newLine();
			for (GPSBoR gps_bor : gpsList) {

				// calculate hits of cosine distance
				if (gps_bor.getRealRoutes().contains(gps_bor.getMostVotedRoute())) {
					truePositives++;
				} else {
					losts++;
				}

				bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " | Most Voted: " + gps_bor.getMostVotedRoute());
				bw.newLine();
				bw.newLine();
			}

			bw.write("Hits:" + truePositives);
			bw.newLine();
			bw.write("Losts:" + losts);
			bw.newLine();
			bw.newLine();
			
			bw.write("Accuracy:" + (0.0 + truePositives) / (truePositives + losts));
			bw.newLine();
			bw.write("------------- DONE -------------");
			
			System.out.println("Accuracy:" + (0.0 + truePositives) / (truePositives + losts));

	        } catch (IOException e) {
	            System.err.format("IOException: %s%n", e);
	        }
	}


	private static void populateAllEdgesFromMap(String fileMapRio) {

		try (FileReader reader = new FileReader(fileMapRio); BufferedReader br = new BufferedReader(reader)) {

			String line;
			String[] lineSplitted;
			String edgeID;
			while ((line = br.readLine()) != null) {
				lineSplitted = line.concat(" ").split(FILE_SEPARATOR);

				if (lineSplitted[0].equals("R")) {
					// R,road segment ID,source cross point ID, destination
					// cross point ID, max speed (km/h), length (m)
					edgeID = lineSplitted[1];
					allEdgesFromMap.put(edgeID, new Edge(edgeID, "", Double.valueOf(lineSplitted[5])));
				}
			}

		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}

	}

	private static void populateRealRoutesMap(String dirRealRoutesFromGps) {
		final File gps_folder = new File(dirRealRoutesFromGps);

		BufferedReader br = null;
		FileReader fr = null;
		Set<String> routesFromGPSFile;
		for (final File fileEntry : gps_folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				routesFromGPSFile = new HashSet<String>();
				try {
					fr = new FileReader(fileEntry.getAbsolutePath());
					br = new BufferedReader(fr);

					String firstLine = br.readLine();

					String sCurrentLine;
					String nextRouteFounded;
					while ((sCurrentLine = br.readLine()) != null) {
						nextRouteFounded = sCurrentLine.concat(" ").split(FILE_SEPARATOR)[5];
						if (!nextRouteFounded.equals(" ")) {
							try {
								// remove blank spaces
								nextRouteFounded = nextRouteFounded.replace(" ", "");
								nextRouteFounded = String.format("%03d", Integer.parseInt(nextRouteFounded));
							} catch (Exception e) {
								// do nothing
							}

							// only add routes that have a correspondent shape
							if (shapesMap.get(nextRouteFounded) != null) {
								routesFromGPSFile.add(nextRouteFounded);
							}
						}
					}

					String[] lineSplitted = firstLine.concat(" ").split(FILE_SEPARATOR);
					// track,timestamp,lon,lat,busId,route
					// Ex: 1,2016-02-15
					// 08:19:50,-43.256401,-22.920759,A27501,439

					if (!routesFromGPSFile.isEmpty()) {
						realRoutesMap.put(lineSplitted[4], routesFromGPSFile);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}

	private static void saveResultSimilarities(String outFile) {

        try (FileWriter writer = new FileWriter(outFile);
             BufferedWriter bw = new BufferedWriter(writer)) {

            

		Integer cosineTruePositives = 0;
		Integer cosineLosts = 0;
		Integer jaccardTruePositives = 0;
		Integer jaccardLosts = 0;
		Integer manhattanTruePositives = 0;
		Integer manhattanLosts = 0;
		Integer euclideanTruePositives = 0;
		Integer euclideanLosts = 0;
		
		

		bw.write("------------- Result -------------");
		bw.newLine();
		for (GPSBoR gps_bor : gpsList) {

			// calculate hits of cosine distance
			if (gps_bor.getRealRoutes().contains(gps_bor.getRouteCosineSimilar())) {
				cosineTruePositives++;
			} else {
				cosineLosts++;
			}

			// calculate hits of Jaccard similarity 
			if (gps_bor.getRealRoutes().contains(gps_bor.getRouteJaccardSimilar())) {
				jaccardTruePositives++;
			} else {
				jaccardLosts++;
			}
			
			if (gps_bor.getRealRoutes().contains(gps_bor.getRouteEuclideanDistant())) {
				euclideanTruePositives++;
			} else {
				euclideanLosts++;
			}
			
			if (gps_bor.getRealRoutes().contains(gps_bor.getRouteManhattanDistant())) {
				manhattanTruePositives++;
			} else {
				manhattanLosts++;
			}

			bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " - Cosine SIM: "
					+ gps_bor.getRouteCosineSimilar() + " | Cosine SIM: " + gps_bor.getMaxCosineSimilarity());
			bw.newLine();
			
			bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " - Jaccard SIM: "
					+ gps_bor.getRouteJaccardSimilar() + " | Jaccard SIM: "
					+ gps_bor.getMaxJaccardSimilarity());			
			bw.newLine();
			
			bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " - Manhattan SIM: "
					+ gps_bor.getRouteManhattanDistant() + " | Manhattan SIM: " + gps_bor.getMinManhattanDistance());
			bw.newLine();
			
			bw.write(gps_bor.getBusID() + "-" + gps_bor.getRealRoutes() + " - Euclidean Route SIM: "
					+ gps_bor.getRouteEuclideanDistant() + " | Euclidean SIM: "
					+ gps_bor.getMinEuclideanDistance());
			bw.newLine();
			
			bw.newLine();
		}

		bw.write("Cosine hits:" + cosineTruePositives);
		bw.newLine();
		bw.write("Cosine losts:" + cosineLosts);
		bw.newLine();
		bw.write("Jaccard hits:" + jaccardTruePositives);
		bw.newLine();
		bw.write("Jaccard losts:" + jaccardLosts);
		bw.newLine();
		bw.write("Manhattan hits:" + manhattanTruePositives);
		bw.newLine();
		bw.write("Manhattan losts:" + manhattanLosts);
		bw.newLine();
		bw.write("Euclidean hits:" + euclideanTruePositives);
		bw.newLine();
		bw.write("Euclidean losts:" + euclideanLosts);
		bw.newLine();
		bw.newLine();
		
		
		
		
		bw.write("Cosine accuracy:" + (0.0 + cosineTruePositives) / (cosineTruePositives + cosineLosts));
		bw.newLine();
		bw.write("Jaccard accuracy:"
				+ (0.0 + jaccardTruePositives) / (jaccardTruePositives + jaccardLosts));
		bw.newLine();
		bw.write("Manhattan accuracy:"
				+ (0.0 + manhattanTruePositives) / (manhattanTruePositives + manhattanLosts));
		bw.newLine();
		bw.write("Euclidean accuracy:"
				+ (0.0 + euclideanTruePositives) / (euclideanTruePositives + euclideanLosts));
		bw.newLine();
		
		bw.write("------------- DONE -------------");
		
		
		System.out.println("Cosine accuracy:" + (0.0 + cosineTruePositives) / (cosineTruePositives + cosineLosts));
		System.out.println("Jaccard accuracy:"
				+ (0.0 + jaccardTruePositives) / (jaccardTruePositives + jaccardLosts));
		System.out.println("Manhattan accuracy:" + (0.0 + manhattanTruePositives) / (manhattanTruePositives + manhattanLosts));
		System.out.println("Euclidean accuracy:"
				+ (0.0 + euclideanTruePositives) / (euclideanTruePositives + euclideanLosts));

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
		
	}

	private static void calculateSimilarities(GPSBoR gps_bor) {
		Double cosineSimilarity;
//		Double jaccardSimilarity;
//		Double manhattanDistance;
//		Double euclideanDistance;
		
		resetAndPopulateCurrentGPSBoR(gps_bor);
		
		for (ShapeBoR shape_bor : shapesMap.values()) {
			try {
				
				
				cosineSimilarity = Similarity.cosineSimilarity(currentBagOfRoadsGPS, shape_bor.getBagOfRoadsMap());
//				jaccardSimilarity = Similarity.jaccardSimilarity(currentBagOfRoadsGPS, shape_bor.getBagOfRoadsMap());
//				manhattanDistance = Similarity.manhattanDistance(currentBagOfRoadsGPS, shape_bor.getBagOfRoadsMap());
//				euclideanDistance = Similarity.euclideanDistance(currentBagOfRoadsGPS, shape_bor.getBagOfRoadsMap());
//				

				if (cosineSimilarity > gps_bor.getMaxCosineSimilarity()) {
					gps_bor.setMaxCosineSimilarity(cosineSimilarity);
					gps_bor.setRouteCosineSimilar(shape_bor.getRoute());
				}

//				if (gps_bor.getMaxJaccardSimilarity() < jaccardSimilarity) {
//					gps_bor.setMaxJaccardSimilarity(jaccardSimilarity);
//					gps_bor.setRouteJaccardSimilar(shape_bor.getRoute());
//				}
//				
//				if (manhattanDistance < gps_bor.getMinManhattanDistance()) {
//					gps_bor.setMinManhattanDistance(manhattanDistance);
//					gps_bor.setRouteManhattanDistant(shape_bor.getRoute());
//				}
//
//				if (euclideanDistance < gps_bor.getMinEuclideanDistance()) {
//					gps_bor.setMinEuclideanDistance(euclideanDistance);
//					gps_bor.setRouteEuclideanDistant(shape_bor.getRoute());
//				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
	}

	private static void resetAndPopulateCurrentGPSBoR(GPSBoR gps_bor) {
		currentBagOfRoadsGPS = new HashMap<String, Integer>();
		Integer visitations;
		for (Edge edge : edgesFromMapMatchShapesAndGPS) {
			visitations = gps_bor.getEdgeVisitations(edge.getEdgeID());
			if (visitations != null) {
				currentBagOfRoadsGPS.put(edge.getEdgeID(), visitations);
			} 
		}
		
	}

	private static void readFilesAndPopulateMapOfShapes(String dirGtfsMapMatched, String typeRoute) {

		final File shape_folder = new File(dirGtfsMapMatched);
		BufferedReader br = null;
		FileReader fr = null;

		for (final File fileEntry : shape_folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				try {
					fr = new FileReader(fileEntry.getAbsolutePath());
					br = new BufferedReader(fr);
					ShapeBoR shape_bor;

					// shape file should have the route as a name
					shape_bor = new ShapeBoR(removeExtension(fileEntry.getName(), typeRoute));
					
					String currentLine = br.readLine();
					while(currentLine != null) {
						addEdgeVisitationsShapes(shape_bor, currentLine);
						currentLine = br.readLine();
					}
					shapesMap.put(shape_bor.getRoute(), shape_bor);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void readFilesAndPopulateListOfGps(String dirGpsMapMatched, String typeGPS) {
		final File gps_folder = new File(dirGpsMapMatched);
		BufferedReader br = null;
		FileReader fr = null;

		
		GPSBoR gps_bor;
		for (final File fileEntry : gps_folder.listFiles()) {
			
			resetVariablesToNewGPS();
			
			if (!fileEntry.isDirectory()) {
				try {
					fr = new FileReader(fileEntry.getAbsolutePath());
					br = new BufferedReader(fr);
					

					// gps file should have the bus_id as a name <busid.csv>
					String busId = removeExtension(fileEntry.getName(), typeGPS);
					
					Set<String> realRoutes = realRoutesMap.remove(busId);
					if (realRoutes != null && !realRoutes.isEmpty()) {
						gps_bor = new GPSBoR(busId, realRoutes, "");
						String currentLine = br.readLine();
						while(currentLine != null) {
							addEdgeVisitationsGPS(gps_bor, currentLine);
							currentLine = br.readLine();
						}
						
						// some gps files from map matching doesn't get any road segments
						if (gps_bor.getMapEdgesSize() > 0) {
							gpsList.add(gps_bor);
							compareShapesAndGPSBoR(gps_bor);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void compareShapesAndGPSBoR(GPSBoR gps_bor) {
		calculateSimilarities(gps_bor);
		gps_bor.setMostVotedRoute();
	}

	private static void resetVariablesToNewGPS() {
		edgesFromMapMatchShapesAndGPS = edgesFromMapMatchShapes;
		
	}
	
	private static void addEdgeVisitationsGPS(BoRObject bor, String firstLine) {

		String[] lineSplitted = firstLine.split(FILE_SEPARATOR);
		Edge newEdge;

		// [0] = file name and should be discarded
		for (int i = 1; i < lineSplitted.length; i++) {
			newEdge = allEdgesFromMap.get(lineSplitted[i]);

			boolean isEdgeNew = edgesFromMapMatchShapesAndGPS.add(newEdge);

			// TODO: deixei esse if para o vetor de GPS ficar apenas do tamanho do de shapes
			if (edgesFromMapMatchShapes.contains(newEdge)) {
				bor.addEdgeVisitation(newEdge.getEdgeID());
			}
		 // TODO: deixei esse if para o vetor de GPS ficar apenas do tamanho do de shapes
//			bor.addEdgeVisitation(newEdge.getEdgeID());
		}
	}
	
	private static void addEdgeVisitationsShapes(BoRObject bor, String firstLine) {

		String[] lineSplitted = firstLine.split(FILE_SEPARATOR);
		Edge newEdge;

		// [0] = file name and should be discarded
		for (int i = 1; i < lineSplitted.length; i++) {
			newEdge = allEdgesFromMap.get(lineSplitted[i]);

			boolean isEdgeNew = edgesFromMapMatchShapes.add(newEdge);

			bor.addEdgeVisitation(newEdge.getEdgeID());
		}
	}

	private static String removeExtension(String name, String typeFile) throws Exception {
		if (name.length() <= 4) {
			throw new Exception("Invalid name");
		}
		if (typeFile.equals("GPS") || typeFile.equals("GTFS")) {
			// file name is like: A27501.csv; where A27501 is the bus ID, for
			// GPS type file, or the route id for GTFS type file
			return name.substring(0, name.length() - 4);
		}

		if (name.length() <= 10) {
			throw new Exception("Invalid name");
		}
		if (typeFile.equals("ROUTE")) {
			// name file is lile: gtfs_linha6-shapes.csv; where 6 is the route
			// id for route files
			return String.format("%03d", Integer.parseInt(name.substring(10, name.length() - 11)));
		}

		return name.substring(0, name.length() - 7);
	}

}