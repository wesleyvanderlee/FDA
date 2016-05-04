
public class Averager {
	
	double sum;
	double amount;
	
	public Averager(){
		sum = 0;
		amount =0;
	}
	
	public void add(double d){
		sum += d;
		assert sum > d;
		amount ++;
	}
	
	public double getAvg(){
		assert amount > 0;
		return (sum/amount);
	}
	
}
