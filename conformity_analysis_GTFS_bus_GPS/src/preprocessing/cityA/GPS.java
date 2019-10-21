package preprocessing.cityA;

import java.util.HashMap;
import java.util.Map;

public class GPS implements Comparable<GPS> {

	private String busCode, timestamp, estado, comunica, latitude, longitude, lineCode, rota, posicao, viagem,
			velocidade;

	private String separator;
	private String time;
	private Double distance;
	private Map<String, Double> mapDistances = new HashMap<String, Double>();
	
	public GPS(String stringLine, String separator) throws Exception {

		this.separator = separator;

		String[] fields = stringLine.concat(" ").replaceAll(",,", ", ,").split(separator);
		if (fields.length < 11) {
			throw new Exception("Some data is missing in GPS log.");
		}

		this.busCode = fields[0];
		this.timestamp = fields[1];
		String time = timestamp.split(" ")[1];
		this.time = time;
		String[] yearMonthDay = timestamp.split(" ")[0].split("-");
		String day = yearMonthDay[2];
		String month = yearMonthDay[1];
		String year = yearMonthDay[0];
		this.timestamp = year + "-" + month + "-" + day + "T" + time + "Z";

		this.estado = fields[2];
		this.comunica = fields[3];

//		UTM2Deg convert = new UTM2Deg(25, 'S', fields[4], fields[5]);
//		this.latitude = String.valueOf(convert.getLatitude());
//		this.longitude = String.valueOf(convert.getLongitude());

		this.latitude = fields[4];
		this.longitude = fields[5];
		this.lineCode = fields[6];
		this.rota = fields[7];
		this.posicao = fields[8];
		this.viagem = fields[9];
		this.velocidade = fields[10];
		this.distance = Double.MAX_VALUE;

	}
	
	public void addDistanceToSequencePoint(String timestampGPS2, Double distance) {
		mapDistances.put(timestampGPS2, distance);
	}
	

	public Double getDistanceToSequencePoint(String timestampGPS2) {
		return mapDistances.get(timestampGPS2);
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComunica() {
		return comunica;
	}

	public void setComunica(String comunica) {
		this.comunica = comunica;
	}

	public Double getLatitude() {
		return Double.valueOf(latitude);
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return Double.valueOf(longitude);
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String linha) {
		this.lineCode = linha;
	}

	public String getRoute() {
		return rota;
	}

	public void setRota(String rota) {
		this.rota = rota;
	}

	public String getPosicao() {
		return posicao;
	}

	public void setPosicao(String posicao) {
		this.posicao = posicao;
	}

	public String getViagem() {
		return viagem;
	}

	public void setViagem(String viagem) {
		this.viagem = viagem;
	}

	public String getVelocidade() {
		return velocidade;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setVelocidade(String velocidade) {
		this.velocidade = velocidade;
	}
	
	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return busCode + "," + estado + "," + comunica + "," + latitude + "," + longitude + "," + lineCode + "," + rota
				+ "," + posicao + "," + viagem + "," + velocidade + "," + separator + "," + time;
	}

	// Track,Time,Latitude,Longitude,Elevation
	public String toStringMM() {
		return busCode + separator + timestamp + separator + latitude + separator + longitude + separator + "0" + separator + rota;
	}

	public String toStringMMTimestampEmpty() {
		return busCode + separator + "" + separator + latitude + separator + longitude + separator + "0";
	}

	@Override
	public int compareTo(GPS other) {
		if (this.distance < other.distance) {
			return -1;
		}
		if (this.distance > other.distance) {
			return 1;
		}
		return 0;
	}

}
