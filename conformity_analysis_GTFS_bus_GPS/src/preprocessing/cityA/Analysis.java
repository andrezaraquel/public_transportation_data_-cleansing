package preprocessing.cityA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import preprocessing.cityA.GPS;

public class Analysis {

	private static final String GPS_FILE = "";
	private static final String SHAPE_FILE = "";
	private static final String OUTPUT_GPS = "";
	private static final String OUTPUT_SHAPE = "";
	private static final String GPS_HEADER = "Unidad,Dia,Instante,Estado,Comunica,CoordX,CoordY,Linea,Ruta,Posicion,Viaje,Velocidad\n";
	private static final String SHAPE_HEADER = "route_id,shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence,shape_dist_traveled";
	private static final String FILE_SEPARATOR = ",";

	public static void main(String[] args) {

//		readFile();
		
//		List<GPS> gpsList = createGPSObjects();
//		System.out.println("GPSList size: " + gpsList.size());
		
		List<String> linesShape = readShapeLines();		
		writeLines(linesShape, SHAPE_HEADER, OUTPUT_SHAPE);
		
		
//		List<String> linesGPS = readGPSLines();		
//		writeLines(linesGPS, GPS_HEADER);
		
		System.out.println("FINISHED");
	}

	private static List<String> readShapeLines() {

		List<String> listOutput = new LinkedList<>();
		BufferedReader br_shape = null;
		FileReader fr_shape = null;
		
		try {
					
			fr_shape = new FileReader(SHAPE_FILE);
			br_shape = new BufferedReader(fr_shape);
			
			String sCurrentLine_shape = br_shape.readLine();		
			while ((sCurrentLine_shape = br_shape.readLine()) != null) {
				String route = sCurrentLine_shape.split(",")[0];
//				if (route.equals("555")) {
//					listOutput.add(sCurrentLine_shape);
//				}
				listOutput.add(sCurrentLine_shape);
			}

		} catch (IOException e) { 

			e.printStackTrace();

		} finally {

			try {

				if (br_shape != null)
					br_shape.close();

				if (fr_shape != null)
					fr_shape.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}
		}
		return listOutput;
	}

	private static void writeLines(List<String> lines, String header, String output) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {

			fw = new FileWriter(output);
			bw = new BufferedWriter(fw);
			
			bw.write(header + "\n");
			for (String s : lines) {
				bw.write(s + "\n");
			}

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

	private static List<String> readGPSLines() {
		BufferedReader br = null;
		FileReader fr = null;
		List<String> listOutput = new LinkedList<String>();

		try {
			fr = new FileReader(GPS_FILE);
			br = new BufferedReader(fr);
			String sCurrentLine = br.readLine();
//			System.out.println(sCurrentLine);

			while ((sCurrentLine = br.readLine()) != null) {
				try {
					GPS gps = new GPS(sCurrentLine,FILE_SEPARATOR);

					if (gps.getBusCode().equals("3335")) {
						listOutput.add(gps.toString());
					}
					//
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

	private static List<GPS> createGPSObjects() {
		List<GPS> outputGPS =  new LinkedList<GPS>();
		Map<String, List<GPS>> mapWithRoute = new LinkedHashMap<String, List<GPS>>();
		Map<String, List<GPS>> mapWithNoRoute = new LinkedHashMap<String, List<GPS>>();
		Integer gpsAdded = 0;
		Integer gpsRemoved = 0;
		
		Integer withNoRouteOrLine = 0;
		
		
		BufferedReader br = null;
		FileReader fr = null;
		
		BufferedReader br_shape = null;
		FileReader fr_shape = null;
		

		Map<String, Integer> mapRoutes = new HashMap<String, Integer>();
		
		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(GPS_FILE);		
			br = new BufferedReader(fr);
			
			fr_shape = new FileReader(SHAPE_FILE);
			br_shape = new BufferedReader(fr_shape);
			
			String sCurrentLine_shape = br_shape.readLine();		
			while ((sCurrentLine_shape = br_shape.readLine()) != null) {
				String route = sCurrentLine_shape.split(",")[0];
				if (!mapRoutes.containsKey(route)) {
					mapRoutes.put(route, 1);
				}
			}

			String sCurrentLine = br.readLine();
			System.out.println(sCurrentLine);
						
			while ((sCurrentLine = br.readLine()) != null) {
				try {
//					outputGPS.add(new GPS(sCurrentLine));
					GPS gps = new GPS(sCurrentLine,FILE_SEPARATOR);

//					if (mapRoutes.containsKey(gps.getRota())){
//						System.out.println(sCurrentLine);
//					}
					
					if (gps.getBusCode().equals("3335")){
						System.out.println(sCurrentLine);
					}
					
//					if ((gps.getRota().trim().equals(" ") || gps.getLinha().trim().equals(" "))
//							&& !gps.getRota().trim().equals(gps.getLinha().trim())) {
//						System.out.println(sCurrentLine);
//					}
					
//					String key_unidade = gps.getUnidade();
//						
//					if ((gps.getRota().equals(" ") || gps.getLinha().equals(" ")) && !gps.getCoordX().equals("0") && !gps.getCoordY().equals("0")) {
//						withNoRouteOrLine ++;
//						if (!mapWithNoRoute.containsKey(key_unidade)) {
//							mapWithNoRoute.put(key_unidade, new LinkedList<GPS>());
//						}
//						
//						
////						mapWithNoRoute.get(key_unidade).add(gps);
//						
//					} else {
//						if (!mapWithRoute.containsKey(key_unidade)) {
//							mapWithRoute.put(key_unidade, new LinkedList<GPS>());
//						}
//						
////						mapWithRoute.get(key_unidade).add(gps);
//					}
					
					gpsAdded++;
				} catch (Exception e) {
					gpsRemoved++;
					System.out.println(sCurrentLine);
//					System.err.println(e);
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
		
		
//		compareMaps(mapWithNoRoute, mapWithRoute);
		
		
		
		
		System.out.println("GPS ADDED: " +  gpsAdded);
		System.out.println("GPS REMOVED: " +  gpsRemoved);
		System.out.println("GPS WITH NO ROUTE OR LINE: " +  withNoRouteOrLine);
		System.out.println("% WITH NO ROUTE OR LINE: " +  (withNoRouteOrLine * 100.)/(gpsAdded + gpsRemoved));
		return outputGPS;
	}

	private static void compareMaps(Map<String, List<GPS>> mapWithNoRoute, Map<String, List<GPS>> mapWithRoute) {

		System.out.println("NO ROUTE: " + mapWithNoRoute.size());
		System.out.println("WITH ROUTE: " + mapWithRoute.size());
		
		int overlapped = 0;
		
		for (String unidade: mapWithNoRoute.keySet()) {
			if (mapWithRoute.containsKey(unidade)) {
				System.out.println("�NIBUS: " + unidade);
				overlapped++;
			}
		}
		System.out.println("overlapped: " + overlapped);
	}

	private static void readFile() {
		BufferedReader br = null;
		FileReader fr = null;
		
		Map<String, Integer> mapDays = new HashMap<String, Integer>();

		try {

			// br = new BufferedReader(new FileReader(FILENAME));
			fr = new FileReader(GPS_FILE);
			br = new BufferedReader(fr);

			String sCurrentLine = br.readLine();			
//			System.out.println(sCurrentLine);
			
			while ((sCurrentLine = br.readLine()) != null) {
//				String day = sCurrentLine.split(",")[1].split(" ")[0];
//				if (!mapDays.containsKey(day)){
//					mapDays.put(day, 1);
//				}
				System.out.println(sCurrentLine);
				break;
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
		
		for(String d : mapDays.keySet()){ 
			System.out.println(d);
			System.out.println(mapDays.get(d));
			break;
		}
		
	}
}
