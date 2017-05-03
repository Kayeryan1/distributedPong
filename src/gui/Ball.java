package gui;

import javafx.scene.shape.Circle;

class Ball extends Circle {
	private final Point position;
	private final Point velocity;
	
	public Ball(double x, double y, double velX, double velY, double radius) {
		this.position = new Point(x, y);
		this.velocity = new Point(velX, velY);
		
		this.setRadius(radius);
	}
	
	public void wallBounce(){
		this.velocity.x *= -1;
		this.velocity.y *= -1;
	}
	
	
}