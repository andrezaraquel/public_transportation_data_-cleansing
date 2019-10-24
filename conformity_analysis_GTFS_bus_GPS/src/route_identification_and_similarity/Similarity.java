package route_identification_and_similarity;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Similarity {

	public static void main(String[] args) throws Exception {

		int[] gps = { 10 };
		int[] shapeLaranja = { 1 };
		int[] shapeRoxo = { 1 };
		int[] shapeVerde = { 9 };

		double[] z = { 1 };
		double[] w = { 1 };

		System.out.println(weightedCosineSimilarity(gps, shapeVerde, z, w));

		// System.out.println(cosineSimilarity(gps, shapeVerde));

	}

	/**
	 * @param A:
	 *            An integer array.
	 * @param B:
	 *            An integer array.
	 * @return: Cosine similarity.
	 * @throws Exception
	 */
	public static double cosineSimilarity(int[] A, int[] B) throws Exception {
		if (A == null || B == null || A.length == 0 || B.length == 0 || A.length != B.length) {
			throw new Exception("List size problem");
		}

		double sumProduct = 0;
		double sumASq = 0;
		double sumBSq = 0;
		for (int i = 0; i < A.length; i++) {
			sumProduct += A[i] * B[i];
			sumASq += A[i] * A[i];
			sumBSq += B[i] * B[i];
		}
		if (sumASq == 0 || sumBSq == 0) {
			return 0.0;
		}
		return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
	}

	public static double cosineSimilarity(Map<String, Integer> A, Map<String, Integer> B) throws Exception {

		Double sumProduct = 0.;
		Double sumASq = 0.;
		Double sumBSq = 0.;

		HashSet<String> result = new HashSet<String>(A.keySet());

		result.addAll(B.keySet());

		for (String key : result) {

			Integer valueA = A.containsKey(key) ? A.get(key) : 0;
			Integer valueB = B.containsKey(key) ? B.get(key) : 0;

			sumProduct += valueA * valueB;
			sumASq += valueA * valueA;
			sumBSq += valueB * valueB;

		}
		return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));

	}

	/**
	 * @param A:
	 *            An integer list.
	 * @param B:
	 *            An integer list.
	 * @return: Cosine similarity.
	 * @throws Exception
	 */
	public static double cosineSimilarity(List<Integer> A, List<Integer> B) throws Exception {
		if (A == null || B == null || A.size() == 0 || B.size() == 0 || A.size() != B.size()) {
			throw new Exception("List size problem");
		}

		double sumProduct = 0;
		double sumASq = 0;
		double sumBSq = 0;
		for (int i = 0; i < A.size(); i++) {
			sumProduct += A.get(i) * B.get(i);
			sumASq += A.get(i) * A.get(i);
			sumBSq += B.get(i) * B.get(i);
		}
		if (sumASq == 0 || sumBSq == 0) {
			return 0.0;
		}
		return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
	}

	/**
	 * @param A:
	 *            An integer array.
	 * @param B:
	 *            An integer array.
	 * @return: Cosine similarity.
	 * @throws Exception
	 */
	public static double weightedCosineSimilarity(int[] A, int[] B, double[] z, double[] w) throws Exception {
		if (A == null || B == null || A.length == 0 || B.length == 0 || A.length != B.length) {
			throw new Exception("List size problem");
		}

		double sumProduct = 0;
		double sumASq = 0;
		double sumBSq = 0;
		for (int i = 0; i < A.length; i++) {
			sumProduct += z[i] * A[i] * w[i] * B[i];
			sumASq += z[i] * A[i] * A[i];
			sumBSq += w[i] * B[i] * B[i];
		}
		if (sumASq == 0 || sumBSq == 0) {
			return 0.0;
		}
		return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
	}

	/**
	 * @param A:
	 *            An integer list.
	 * @param B:
	 *            An integer list.
	 * @return: Euclidean distance.
	 * @throws Exception
	 */
	public static double euclideanDistance(Map<String, Integer> A, Map<String, Integer> B) throws Exception {

		HashSet<String> unionSet = new HashSet<String>(A.keySet());

		unionSet.addAll(B.keySet());

		Integer valueA;
		Integer valueB;
		Double sum = 0.;
		for (String key : unionSet) {
			valueA = A.containsKey(key) ? A.get(key) : 0;
			valueB = B.containsKey(key) ? B.get(key) : 0;

			sum += Math.pow(valueA - valueB, 2);
		}

		return Math.sqrt(sum);
	}

	/**
	 * @param A:
	 *            An integer list.
	 * @param B:
	 *            An integer list.
	 * @return: Manhattan distance.
	 * @throws Exception
	 */
	public static double manhattanDistance(Map<String, Integer> A, Map<String, Integer> B) throws Exception {

		HashSet<String> unionSet = new HashSet<String>(A.keySet());

		unionSet.addAll(B.keySet());

		Integer valueA;
		Integer valueB;
		Double sum = 0.;
		for (String key : unionSet) {
			valueA = A.containsKey(key) ? A.get(key) : 0;
			valueB = B.containsKey(key) ? B.get(key) : 0;

			sum += Math.abs(valueA - valueB);
		}

		return sum;
	}

	
	
	/**
	 * @param A:
	 *            An integer list.
	 * @param B:
	 *            An integer list.
	 * @return: Jaccard Similarity.
	 * @throws Exception
	 */
	public static Double jaccardSimilarity(Map<String, Integer> A, Map<String, Integer> B) throws Exception {

		Set<String> unionSet = new HashSet<String>(A.keySet());
		unionSet.addAll(B.keySet());

		Set<String> intersectionSet = new HashSet<String>(A.keySet());
		intersectionSet.retainAll(B.keySet());

		return ((double)intersectionSet.size()) / unionSet.size();
	}

	public static List<Double> CosineAndWeightedCosineSimilarity(Map<String, Integer> A, Map<String, Integer> B,
			Map<String, Double> z) throws Exception {

		Double sumProduct_w = 0.;
		Double sumASq_w = 0.;
		Double sumBSq_w = 0.;

		Double sumProduct = 0.;
		Double sumASq = 0.;
		Double sumBSq = 0.;

		HashSet<String> result = new HashSet<String>(A.keySet());

		result.addAll(B.keySet());

		for (String key : result) {
			// not weighted

			Integer valueA = A.containsKey(key) ? A.get(key) : 0;
			Integer valueB = B.containsKey(key) ? B.get(key) : 0;
			Double valueZ = z.containsKey(key) ? z.get(key) : 0;

			sumProduct += valueA * valueB;
			sumASq += valueA * valueA;
			sumBSq += valueB * valueB;

			// weighted
			sumProduct_w += valueZ * valueA * valueZ * valueB;
			sumASq_w += valueZ * valueA * valueA;
			sumBSq_w += valueZ * valueB * valueB;

		}

		List<Double> output = new LinkedList<>();
		if (sumASq == 0 || sumBSq == 0) {
			output.add(0.0);
		} else {
			output.add(sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq)));
		}

		if (sumASq_w == 0 || sumBSq_w == 0) {
			output.add(0.0);
		} else {

			Double resultA = (sumProduct_w / (Math.sqrt(sumASq_w) * Math.sqrt(sumBSq_w))) * 2;
			if (resultA > 1) {
				resultA = 1.;
			}

			output.add(resultA);
		}

		// not weighted similarity and weighted similarity
		return output;
	}

	public static List<Double> CosineAndWeightedCosineSimilarity(Map<String, Integer> A, Map<String, Integer> B,
			Map<String, Double> z, Map<String, Double> w) throws Exception {

		Double sumProduct_w = 0.;
		Double sumASq_w = 0.;
		Double sumBSq_w = 0.;

		Double sumProduct = 0.;
		Double sumASq = 0.;
		Double sumBSq = 0.;

		HashSet<String> result = new HashSet<String>(A.keySet());

		result.addAll(B.keySet());

		for (String key : result) {
			// not weighted

			Integer valueA = A.containsKey(key) ? A.get(key) : 0;
			Integer valueB = B.containsKey(key) ? B.get(key) : 0;
			Double valueZ = z.containsKey(key) ? z.get(key) : 0;
			Double valueW = w.containsKey(key) ? w.get(key) : 0;

			sumProduct += valueA * valueB;
			sumASq += valueA * valueA;
			sumBSq += valueB * valueB;

			// weighted
			sumProduct_w += valueZ * valueA * valueW * valueB;
			sumASq_w += valueZ * valueA * valueA;
			sumBSq_w += valueW * valueB * valueB;

		}

		List<Double> output = new LinkedList<>();
		if (sumASq == 0 || sumBSq == 0) {
			output.add(0.0);
		} else {
			output.add(sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq)));
		}

		if (sumASq_w == 0 || sumBSq_w == 0) {
			output.add(0.0);
		} else {
			output.add(sumProduct_w / (Math.sqrt(sumASq_w) * Math.sqrt(sumBSq_w)));
		}

		// not weighted similarity and weighted similarity
		return output;
	}

	/***
	 * 
	 * @param A:
	 *            An integer array.
	 * @param B:
	 *            An integer array.
	 * @param z
	 *            weight referring to number of shapes.
	 * @param w
	 *            weight referring to length of roads.
	 * @return Weighted cosine similarity.
	 * @throws Exception
	 */
	public static List<Double> CosineAndWeightedCosineSimilarity(List<Integer> A, List<Integer> B, List<Double> z,
			List<Double> w) throws Exception {
		if (A == null || B == null || A.size() == 0 || B.size() == 0 || A.size() != B.size() || z.size() != A.size()
				|| w.size() != A.size()) {
			throw new Exception("List size problem");
		}

		Double sumProduct_w = 0.;
		Double sumASq_w = 0.;
		Double sumBSq_w = 0.;

		Double sumProduct = 0.;
		Double sumASq = 0.;
		Double sumBSq = 0.;
		for (int i = 0; i < A.size(); i++) {
			// not weighted
			sumProduct += A.get(i) * B.get(i);
			sumASq += A.get(i) * A.get(i);
			sumBSq += B.get(i) * B.get(i);

			// weighted
			sumProduct_w += z.get(i) * A.get(i) * w.get(i) * B.get(i);
			sumASq_w += z.get(i) * A.get(i) * A.get(i);
			sumBSq_w += w.get(i) * B.get(i) * B.get(i);

		}

		List<Double> output = new LinkedList<>();
		if (sumASq == 0 || sumBSq == 0) {
			output.add(0.0);
		} else {
			output.add(sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq)));
		}

		if (sumASq_w == 0 || sumBSq_w == 0) {
			output.add(0.0);
		} else {
			output.add(sumProduct_w / (Math.sqrt(sumASq_w) * Math.sqrt(sumBSq_w)));
		}

		// not weighted similarity and weighted similarity
		return output;
	}

	/***
	 * 
	 * @param A:
	 *            An integer array.
	 * @param B:
	 *            An integer array.
	 * @param z
	 *            weight referring to number of shapes.
	 * @param w
	 *            weight referring to length of roads.
	 * @return Weighted cosine similarity.
	 * @throws Exception
	 */
	public static Double weightedCosineSimilarity(List<Integer> A, List<Integer> B, List<Double> z, List<Double> w)
			throws Exception {
		if (A == null || B == null || A.size() == 0 || B.size() == 0 || A.size() != B.size() || z.size() != A.size()
				|| w.size() != A.size()) {
			throw new Exception("List size problem");
		}

		Double sumProduct = 0.;
		Double sumASq = 0.;
		Double sumBSq = 0.;
		for (int i = 0; i < A.size(); i++) {
			sumProduct += z.get(i) * A.get(i) * w.get(i) * B.get(i);
			sumASq += z.get(i) * A.get(i) * A.get(i);
			sumBSq += w.get(i) * B.get(i) * B.get(i);

		}

		if (sumASq == 0 || sumBSq == 0) {
			return 0.;
		}
		return sumProduct / (Math.sqrt(sumASq) * Math.sqrt(sumBSq));
	}

}
