package gui;

import java.io.Serializable;

public class Point implements Serializable {
	private static final long serialVersionUID = -6961197475891799948L;

	double x, y;
	public int playerNumber = -1;

	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Point(" + x + ", " + y + ")";
	}
}