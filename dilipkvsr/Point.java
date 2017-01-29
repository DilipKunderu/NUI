import java.io.Serializable;

public class Point implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public double X;
	public double Y;
	public int ID;

	public Point(double x, double y, int id){
		X = x; 
		Y = y; 
		ID = id; 
	}
}
