package preprocessing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

//import preprocessing.cg.GPS;
import preprocessing.cityA.GPS;
//import preprocessing.curitiba.GPS;


/**
 * This class separates all GPS files (all days passed as parameter) according to the bus that sent the GPS points.
 * @author andreza
 *
 */
public class SeparateGPSFiles {

	private static final String INPUT_DIR = "";
	private static final String OUTPUT_DIR = "";
	private static final String INPUT_HEADER = "bus.code,latitude,longitude,timestamp,line.code,gps.id";
	private static final String OUTPUT_HEADER = "Track,Time,Latitude,Longitude,Elevation";
	private static final String FILE_SEPARATOR = ",";
	private static final String FILE_NAME_SEPARATOR = ";";
//	private static final String[] TIME_SLOTS = {"00:00:00 to 06:00:00", "06:00:01 to 08:00:00", "08:00:01 to 10:00:00",
//			"10:00:01 to 12:00:00","12:00:01 to 14:00:00","14:00:01 to 16:00:00","16:00:01 to 18:00:00",
//			"18:00:01 to 20:00:00","20:00:01 to 22:00:00", "22:00:01 to 23:59:59"};

	public static void main(String[] args) {

		Map<String, List<GPS>> mapGPS = readGPSLines(INPUT_DIR, FILE_SEPARATOR);

		for (String busIdRouteTimeInterval : mapGPS.keySet()) {
			writeLines(mapGPS.get(busIdRouteTimeInterval), OUTPUT_HEADER, OUTPUT_DIR + busIdRouteTimeInterval + ".csv");
		}

		System.out.println("Files generated!");

	}

	private static void writeLines(List<GPS> linesGPS, String headerOutput, String outputPath) {
		BufferedWriter bw = null;
		FileWriter fw = null;
		try {

			fw = new FileWriter(outputPath);
			bw = new BufferedWriter(fw);

//			bw.write(headerOutput);
			for (GPS s : linesGPS) {
//				bw.newLine();
				bw.write(s.toStringMM());
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

	private static Map<String, List<GPS>> readGPSLines(String gpsDir, String separator) {
		BufferedReader br = null;
		FileReader fr = null;
		Map<String, List<GPS>> listOutput = new HashMap<String, List<GPS>>();

		final File gps_folder = new File(gpsDir);

		System.out.println("reading gps files from " + gps_folder.getAbsolutePath());
		for (final File fileEntry : gps_folder.listFiles()) {
			System.out.println("reading file: " + fileEntry.getAbsolutePath());
			if (!fileEntry.isDirectory()) {
				try {
					fr = new FileReader(fileEntry.getAbsolutePath());
					br = new BufferedReader(fr);
					String sCurrentLine = br.readLine();

					while ((sCurrentLine = br.readLine()) != null) {
						try {
							GPS gps = new GPS(sCurrentLine, separator);

							String busIDRoute = gps.getBusCode() + FILE_NAME_SEPARATOR + gps.getLineCode();
							
							if (!listOutput.containsKey(busIDRoute)) {
								listOutput.put(busIDRoute, new LinkedList<GPS>());
							}
							listOutput.get(busIDRoute).add(gps);
							
//							String busIDRouteTimeInterval = getKeyOutput(busIDRoute, gps.getTime());
//							
//							if (!listOutput.containsKey(busIDRouteTimeInterval)) {
//								listOutput.put(busIDRouteTimeInterval, new LinkedList<GPS>());
//							}
//							
//							listOutput.get(busIDRouteTimeInterval).add(gps);

						} catch (Exception e) {
//							System.out.println(sCurrentLine);
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
			}
		}

		return listOutput;
	}

//	private static String getKeyOutput(String busIDRoute, String gpsTimeString) throws ParseException {
//		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
//		Date dateGPS = formatter.parse(gpsTimeString);
//		
//		for (int i = 0; i < TIME_SLOTS.length; i++) {
//			String[] times = TIME_SLOTS[i].split(" to ");
//			Date dateA= formatter.parse(times[0]);
//			Date dateB= formatter.parse(times[1]);
//			
//			if (dateA.compareTo(dateGPS) * dateGPS.compareTo(dateB) >= 0) {
//				return busIDRoute+FILE_NAME_SEPARATOR+TIME_SLOTS[i].replace(":", "-");
//			}
//		}
//		return busIDRoute;
//	}

}
