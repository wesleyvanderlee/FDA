import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
	public static final String baseDirectory = "C:/Users/Abstract Build/workspace/FDA/";
	public static final String filename = "data-5145.txt";
	public static final int dataSamplesN = 1000;

	public static ArrayList<Polynomial>[] bestFits;

	public static void main(String[] args) throws Exception {
		ArrayList<ArrayList<Polynomial>> polys = getPolynomials();
		int degree = 1;
		for (ArrayList<Polynomial> degreeOfPolynomial : polys) {
			
			double bias = 0;
			for (int x = 0; x <= CreateData.range_upper; x++) {
				bias += Math.pow((CreateData.fw(x) - getAverage(degreeOfPolynomial,x)),2);
			}
			
			Averager ubervarianceAvg = new Averager();
			for (Polynomial pl : degreeOfPolynomial) {
				Averager varianceAvg = new Averager();
				varianceAvg.add(0); // because variance is divided by N-1
				for (int x = 0; x <= CreateData.range_upper; x++) {
					double local_variance = Math.pow(pl.getY(x) - CreateData.fw(x),2);
					varianceAvg.add(local_variance);
				}
				ubervarianceAvg.add(varianceAvg.getAvg());
			}
			
			PolynomialFitter pf = new PolynomialFitter(degree);
			for(int o=0;o<=CreateData.range_upper;o++){
				pf.addPoint(o, getAverage(degreeOfPolynomial,o));
			}
			double variance =  ubervarianceAvg.getAvg();
			System.out.println("Degree: "+ degree + " MSE: " + round(variance + bias,3));
			System.out.println("Bias2: " + round(bias,3) + " Variance: " + round(variance,3));
			degree++;
		}
	}

	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
	
	public static double getAverage(ArrayList<Polynomial> pol, int x) {
		Averager g = new Averager();
		for (Polynomial p : pol) {
			g.add(p.getY(x));
		}
		return g.getAvg();
	}

	public static ArrayList<Polynomial> getPolinomialsForDegree(int degree) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(baseDirectory + filename));
		String sCurrentLine;
		PolynomialFitter[] polynomialFitters =  new PolynomialFitter[dataSamplesN + 1];
		// Initiate all polynomials
		for (int teller = 0; teller < polynomialFitters.length; teller++) {
			polynomialFitters[teller] = new PolynomialFitter(degree);
		}
		while ((sCurrentLine = br.readLine()) != null) {
			String[] points = sCurrentLine.split("\t");
			int x = Integer.parseInt(points[0]);
			for (int i = 1; i < polynomialFitters.length; i++) {
				double y = Double.parseDouble(points[i]);
				polynomialFitters[i].addPoint(x, y);
			}
		}
		br.close();

		ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
		for (PolynomialFitter p : polynomialFitters) {
			Polynomial temp = p.getBestFit();
			if (!temp.toString().startsWith("0.0x"))
				polynomials.add(temp);
		}
		return polynomials;
	}

	public static ArrayList<ArrayList<Polynomial>> getPolynomials() {
		ArrayList<ArrayList<Polynomial>> bestFits = new ArrayList<ArrayList<Polynomial>>();
		for (int i = 1; i <= 3; i++) {
			try {
				bestFits.add(getPolinomialsForDegree(i));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bestFits;
	}
}
