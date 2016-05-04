import java.util.ArrayList;

public class Polynomial extends ArrayList<Double> {
		private static final long serialVersionUID = 1692843494322684190L;

		public Polynomial(final int p) {
			super(p);
		}

		public double getY(final double x) {
			double ret = 0;
			for (int p = 0; p < size(); p++) {
				ret += get(p) * (Math.pow(x, p));
			}
			return ret;
		}

		
		public ArrayList<Double> getParam(){
			return (ArrayList<Double>) this;
		}
		
		@Override
		public String toString() {
			final StringBuilder ret = new StringBuilder();
			for (int x = size() - 1; x > -1; x--) {
				ret.append(get(x) + (x > 0 ? "x^" + x + " + " : ""));
			}
			return ret.toString();
		}
	}