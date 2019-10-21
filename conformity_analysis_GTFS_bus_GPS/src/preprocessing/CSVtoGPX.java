package preprocessing;

import java.io.File;

import com.github.fracpete.gpsformats4j.Convert;
import com.github.fracpete.gpsformats4j.formats.CSV;
import com.github.fracpete.gpsformats4j.formats.GPX;

public class CSVtoGPX {

	private static final String INPUT_DIR = "";
	private static final String OUTPUT_DIR = "";

	public static void main(String[] args) {

		final File gps_folder = new File(INPUT_DIR);

		for (final File fileEntry : gps_folder.listFiles()) {
			if (!fileEntry.isDirectory()) {
				try {
					
					System.out.println("Converting: " + fileEntry.getAbsolutePath());
					Convert convert = new Convert();
					convert.setInputFile(new File(fileEntry.getAbsolutePath()));
					convert.setInputFormat(CSV.class);
					String outputFile = OUTPUT_DIR + changeFormat(fileEntry.getName());
					convert.setOutputFile(new File(outputFile));
					convert.setOutputFormat(GPX.class);
					String msg = convert.execute();
					// successful if null returned:
					if (msg != null) {
						System.err.println(msg);
						
					} else {
						System.out.println("Successfully converted into: " + outputFile);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		
		System.out.println("DONE!");
	}

	private static String changeFormat(String name) throws Exception {
		if (name.length() <= 4) {
			throw new Exception("Invalid name");
		}
		return name.substring(0, name.length() - 4) + ".gpx";
	}

}
