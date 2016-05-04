import java.io.PrintWriter;
import java.util.Random;

public class CreateData {
	public static final int T = 1000;
	public static final int range_under = 0;
	public static final int range_upper = 3;
	String id;
	String name;
	
	public static void main(String[] args){
		new CreateData();
	}
	
	public CreateData(){
		String timestamp = System.currentTimeMillis() + "";
		this.id = timestamp.substring(timestamp.length()-4,timestamp.length());
		this.name = "data-" + id + ".txt";
		try{
			this.createData();
			System.out.println("Data successfully created in: " + this.name);
		}catch(Exception e){
			System.err.println("Data not succesfull created");
		}
	}
		
	public void createData() throws Exception{
		PrintWriter writer = new PrintWriter(this.name, "UTF-8");
		String line;
		for (int i = range_under; i <= range_upper; i++) {
			line = i + "";
			for (int a = 0; a < T; a++) {
				line += "\t" + f(i);
			}
			writer.println(line);
		}
		writer.close();
	}

	public static double f(double x) {
		return fw(x) + variance();
	}
	
	public static double fw(double x) {
		return (x/4) + Math.sin(x); // data-2351.txt
//		return Math.pow(Math.E,x); //data-8914.txt
//		return (0.25 * x ) + 2;    //data-0015.txt
	}

	public static double variance() {
		// Return random point between -1.0 and 1.0
		// return (Math.random() * 2) -1
		
		// Return Gaussian distributed point between -1.0 and 1.0
		return new Random().nextGaussian();
		
	}
}
