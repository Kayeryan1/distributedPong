package gui;

public class Point {
	double x, y;

	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Point(" + x + ", " + y + ")";
	}
}