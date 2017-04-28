package GUI;

import javafx.scene.shape.Rectangle;

class Ball extends Rectangle {
	private final Point position;
	private final Point velocity;
	
	public Ball(double x, double y, double velX, double velY, int length) {
		this.position = new Point(x, y);
		this.velocity = new Point(velX, velY);
		
		this.setWidth(length);
		this.setHeight(length);
	}
}