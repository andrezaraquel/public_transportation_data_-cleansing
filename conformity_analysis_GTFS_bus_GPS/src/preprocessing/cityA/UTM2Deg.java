package preprocessing.cityA;

public class UTM2Deg {

	double latitude;
	double longitude;

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public UTM2Deg(int zone, double hem, String easting, String northing) {

		// char Letter=parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
		double Easting = Double.parseDouble(easting);
		double Northing = Double.parseDouble(northing);
		double Hem = hem;
		// if (Letter>'M')
		// Hem='N';
		// else
		// Hem='S';
		double north;
		if (Hem == 'S')
			north = Northing - 10000000;
		else
			north = Northing;

		if (Easting == 0 && Northing == 0) {
			latitude = 0;
			longitude = 0;
		} else {

			latitude = (north / 6366197.724
					/ 0.9996
					+ (1
							+ 0.006739496742 * Math.pow(
									Math.cos(north / 6366197.724 / 0.9996), 2)
							- 0.006739496742 * Math.sin(north / 6366197.724 / 0.9996)
									* Math.cos(north / 6366197.724
											/ 0.9996)
									* (Math.atan(Math
											.cos(Math
													.atan((Math.exp(
															(Easting - 500000)
																	/ (0.9996 * 6399593.625
																			/ Math.sqrt((1 + 0.006739496742
																					* Math.pow(
																							Math.cos(north / 6366197.724
																									/ 0.9996),
																							2))))
																	* (1 - 0.006739496742
																			* Math.pow(
																					(Easting - 500000)
																							/ (0.9996 * 6399593.625
																									/ Math.sqrt(
																											(1 + 0.006739496742
																													* Math.pow(
																															Math.cos(
																																	north / 6366197.724
																																			/ 0.9996),
																															2)))),
																					2)
																			/ 2
																			* Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2)
																			/ 3))
															- Math.exp(
																	-(Easting - 500000)
																			/ (0.9996 * 6399593.625
																					/ Math.sqrt((1 + 0.006739496742
																							* Math.pow(
																									Math.cos(
																											north / 6366197.724
																													/ 0.9996),
																									2))))
																			* (1 - 0.006739496742
																					* Math.pow(
																							(Easting - 500000) / (0.9996
																									* 6399593.625 / Math
																											.sqrt((1 + 0.006739496742
																													* Math.pow(
																															Math.cos(
																																	north / 6366197.724
																																			/ 0.9996),
																															2)))),
																							2)
																					/ 2
																					* Math.pow(Math.cos(north
																							/ 6366197.724 / 0.9996), 2)
																					/ 3)))
															/ 2
															/ Math.cos((north - 0.9996 * 6399593.625
																	* (north / 6366197.724 / 0.9996 - 0.006739496742
																			* 3 / 4
																			* (north / 6366197.724 / 0.9996
																					+ Math.sin(2 * north / 6366197.724
																							/ 0.9996) / 2)
																			+ Math.pow(0.006739496742 * 3 / 4, 2) * 5
																					/ 3 * (3
																							* (north / 6366197.724
																									/ 0.9996
																									+ Math
																											.sin(2 * north
																													/ 6366197.724
																													/ 0.9996)
																											/ 2)
																							+ Math.sin(2 * north
																									/ 6366197.724
																									/ 0.9996)
																									* Math.pow(
																											Math.cos(
																													north / 6366197.724
																															/ 0.9996),
																											2))
																					/ 4
																			- Math.pow(0.006739496742 * 3 / 4, 3) * 35
																					/ 27
																					* (5 * (3
																							* (north / 6366197.724
																									/ 0.9996
																									+ Math
																											.sin(2 * north
																													/ 6366197.724
																													/ 0.9996)
																											/ 2)
																							+ Math.sin(2 * north
																									/ 6366197.724
																									/ 0.9996)
																									* Math.pow(
																											Math.cos(
																													north / 6366197.724
																															/ 0.9996),
																											2))
																							/ 4
																							+ Math
																									.sin(2 * north
																											/ 6366197.724
																											/ 0.9996)
																									* Math.pow(
																											Math.cos(
																													north / 6366197.724
																															/ 0.9996),
																											2)
																									* Math.pow(
																											Math.cos(
																													north / 6366197.724
																															/ 0.9996),
																											2))
																					/ 3))
																	/ (0.9996 * 6399593.625 / Math.sqrt(
																			(1 + 0.006739496742 * Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2))))
																	* (1 - 0.006739496742
																			* Math.pow(
																					(Easting - 500000)
																							/ (0.9996 * 6399593.625
																									/ Math.sqrt(
																											(1 + 0.006739496742
																													* Math.pow(
																															Math.cos(
																																	north / 6366197.724
																																			/ 0.9996),
																															2)))),
																					2)
																			/ 2
																			* Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2))
																	+ north / 6366197.724 / 0.9996)))
											* Math.tan((north - 0.9996 * 6399593.625
													* (north / 6366197.724 / 0.9996 - 0.006739496742 * 3
															/ 4
															* (north / 6366197.724
																	/ 0.9996
																	+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
															+ Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3
																	* (north / 6366197.724 / 0.9996
																			+ Math.sin(2 * north / 6366197.724 / 0.9996)
																					/ 2)
																	+ Math.sin(2 * north / 6366197.724 / 0.9996)
																			* Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2))
																	/ 4
															- Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5
																	* (3 * (north / 6366197.724 / 0.9996
																			+ Math.sin(2 * north / 6366197.724 / 0.9996)
																					/ 2)
																			+ Math.sin(2 * north / 6366197.724 / 0.9996)
																					* Math.pow(Math.cos(north
																							/ 6366197.724 / 0.9996), 2))
																	/ 4
																	+ Math.sin(2 * north / 6366197.724 / 0.9996)
																			* Math.pow(Math
																					.cos(north / 6366197.724 / 0.9996),
																					2)
																			* Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2))
																	/ 3))
													/ (0.9996 * 6399593.625 / Math.sqrt((1 + 0.006739496742 * Math
															.pow(Math.cos(north / 6366197.724 / 0.9996), 2))))
													* (1 - 0.006739496742
															* Math.pow((Easting - 500000) / (0.9996 * 6399593.625
																	/ Math.sqrt((1 + 0.006739496742 * Math.pow(
																			Math.cos(north / 6366197.724 / 0.9996),
																			2)))),
																	2)
															/ 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
													+ north / 6366197.724 / 0.9996))
											- north / 6366197.724 / 0.9996)
									* 3 / 2)
							* (Math.atan(Math
									.cos(Math
											.atan((Math
													.exp((Easting - 500000) / (0.9996 * 6399593.625
															/ Math.sqrt((1 + 0.006739496742 * Math.pow(
																	Math.cos(north / 6366197.724 / 0.9996), 2))))
															* (1 - 0.006739496742
																	* Math.pow(
																			(Easting - 500000) / (0.9996 * 6399593.625
																					/ Math.sqrt((1
																							+ 0.006739496742 * Math.pow(
																									Math.cos(
																											north / 6366197.724
																													/ 0.9996),
																									2)))),
																			2)
																	/ 2
																	* Math.pow(Math.cos(north / 6366197.724 / 0.9996),
																			2)
																	/ 3))
													- Math.exp(
															-(Easting - 500000)
																	/ (0.9996 * 6399593.625
																			/ Math.sqrt(
																					(1 + 0.006739496742
																							* Math.pow(
																									Math.cos(
																											north / 6366197.724
																													/ 0.9996),
																									2))))
																	* (1 - 0.006739496742
																			* Math.pow(
																					(Easting - 500000)
																							/ (0.9996 * 6399593.625
																									/ Math.sqrt(
																											(1 + 0.006739496742
																													* Math.pow(
																															Math.cos(
																																	north / 6366197.724
																																			/ 0.9996),
																															2)))),
																					2)
																			/ 2
																			* Math.pow(Math.cos(
																					north / 6366197.724 / 0.9996), 2)
																			/ 3)))
													/ 2
													/ Math.cos((north - 0.9996 * 6399593.625
															* (north / 6366197.724 / 0.9996
																	- 0.006739496742 * 3 / 4
																			* (north / 6366197.724 / 0.9996 + Math.sin(
																					2 * north / 6366197.724 / 0.9996)
																					/ 2)
																	+ Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3
																			* (north / 6366197.724 / 0.9996 + Math.sin(
																					2 * north / 6366197.724 / 0.9996)
																					/ 2)
																			+ Math.sin(
																					2 * north / 6366197.724 / 0.9996)
																					* Math.pow(
																							Math.cos(north / 6366197.724
																									/ 0.9996),
																							2))
																			/ 4
																	- Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5
																			* (3 * (north / 6366197.724 / 0.9996
																					+ Math.sin(2 * north / 6366197.724
																							/ 0.9996) / 2)
																					+ Math.sin(2 * north / 6366197.724
																							/ 0.9996)
																							* Math.pow(
																									Math.cos(
																											north / 6366197.724
																													/ 0.9996),
																									2))
																			/ 4
																			+ Math.sin(2 * north / 6366197.724 / 0.9996)
																					* Math.pow(
																							Math.cos(north / 6366197.724
																									/ 0.9996),
																							2)
																					* Math.pow(Math.cos(north
																							/ 6366197.724 / 0.9996), 2))
																			/ 3))
															/ (0.9996 * 6399593.625
																	/ Math.sqrt((1 + 0.006739496742 * Math.pow(
																			Math.cos(north / 6366197.724 / 0.9996),
																			2))))
															* (1 - 0.006739496742
																	* Math.pow(
																			(Easting - 500000) / (0.9996 * 6399593.625
																					/ Math.sqrt((1
																							+ 0.006739496742 * Math.pow(
																									Math.cos(
																											north / 6366197.724
																													/ 0.9996),
																									2)))),
																			2)
																	/ 2
																	* Math.pow(Math.cos(north / 6366197.724 / 0.9996),
																			2))
															+ north / 6366197.724 / 0.9996)))
									* Math.tan((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996
											- 0.006739496742 * 3 / 4
													* (north / 6366197.724 / 0.9996
															+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
											+ Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3 * (3
													* (north / 6366197.724 / 0.9996
															+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
													/ 4
											- Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27 * (5 * (3
													* (north / 6366197.724 / 0.9996
															+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
													/ 4
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
													/ 3))
											/ (0.9996 * 6399593.625
													/ Math.sqrt((1 + 0.006739496742 * Math
															.pow(Math.cos(north / 6366197.724 / 0.9996), 2))))
											* (1 - 0.006739496742
													* Math.pow((Easting - 500000) / (0.9996 * 6399593.625
															/ Math.sqrt((1 + 0.006739496742 * Math
																	.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))),
															2)
													/ 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
											+ north / 6366197.724 / 0.9996))
									- north / 6366197.724 / 0.9996))
					* 180 / Math.PI;
			latitude = Math.round(latitude * 10000000);
			latitude = latitude / 10000000;
			longitude = Math
					.atan((Math
							.exp((Easting - 500000)
									/ (0.9996 * 6399593.625
											/ Math.sqrt((1 + 0.006739496742
													* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))))
									* (1 - 0.006739496742
											* Math.pow(
													(Easting - 500000) / (0.9996 * 6399593.625
															/ Math.sqrt((1 + 0.006739496742 * Math
																	.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))),
													2)
											/ 2
											* Math.pow(Math.cos(north / 6366197.724 / 0.9996),
													2)
											/ 3))
							- Math.exp(
									-(Easting - 500000) / (0.9996 * 6399593.625
											/ Math.sqrt((1 + 0.006739496742
													* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))))
											* (1 - 0.006739496742
													* Math.pow((Easting - 500000) / (0.9996 * 6399593.625
															/ Math.sqrt((1 + 0.006739496742 * Math
																	.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))),
															2)
													/ 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2) / 3)))
							/ 2
							/ Math.cos((north - 0.9996 * 6399593.625 * (north / 6366197.724 / 0.9996
									- 0.006739496742 * 3 / 4
											* (north / 6366197.724 / 0.9996
													+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
									+ Math.pow(0.006739496742 * 3 / 4, 2) * 5 / 3
											* (3 * (north / 6366197.724 / 0.9996
													+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
											/ 4
									- Math.pow(0.006739496742 * 3 / 4, 3) * 35 / 27
											* (5 * (3
													* (north / 6366197.724 / 0.9996
															+ Math.sin(2 * north / 6366197.724 / 0.9996) / 2)
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
													/ 4
													+ Math.sin(2 * north / 6366197.724 / 0.9996)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2)
															* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
											/ 3))
									/ (0.9996 * 6399593.625
											/ Math.sqrt((1 + 0.006739496742
													* Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))))
									* (1 - 0.006739496742
											* Math.pow(
													(Easting - 500000) / (0.9996 * 6399593.625
															/ Math.sqrt((1 + 0.006739496742 * Math
																	.pow(Math.cos(north / 6366197.724 / 0.9996), 2)))),
													2)
											/ 2 * Math.pow(Math.cos(north / 6366197.724 / 0.9996), 2))
									+ north / 6366197.724 / 0.9996))
					* 180 / Math.PI + zone * 6 - 183;
			longitude = Math.round(longitude * 10000000);
			longitude = longitude / 10000000;
		}
	}
}