package gui;

import javafx.application.Platform;
import javafx.scene.shape.Circle;

class Ball extends Circle {
	final Point position;
	final Point velocity;
	final static double MAX_ANGLE = 5 * (Math.PI / 12);
	final static double BALL_SPEED = 0.1;
	
	public Ball(double x, double y, double velX, double velY, double radius) {
		this.position = new Point(x, y);
		this.velocity = new Point(velX, velY);
		
		this.setRadius(radius);
	}
	
	public void wallBounce(){
		this.velocity.x *= -1;
		this.velocity.y *= -1;
	}
	
	public void paddleBounce(Paddle paddle) {
		if (paddle.orientation == PaddleOrientation.Horizontal) {
			double intersectX = position.x;
			double normalizer = (ClientGUI.PADDLE_LENGTH/2);
			double relIntersectX = (paddle.position.y + normalizer) - intersectX;
			double shiftedIntersectX = (relIntersectX / (normalizer));
			double angle = shiftedIntersectX * MAX_ANGLE;
			double ballVx = BALL_SPEED * (-1 * Math.sin(angle));
			double ballVy = BALL_SPEED * Math.cos(angle);
			
			this.velocity.x = ballVx;
			this.velocity.y = ballVy;
		} else {
			double intersectY = position.y;
			double normalizer = (ClientGUI.PADDLE_LENGTH/2);
			double relIntersectY = (paddle.position.y + normalizer) - intersectY;
			double shiftedIntersectY = (relIntersectY / (normalizer));
			double angle = shiftedIntersectY * MAX_ANGLE;
			double ballVx = BALL_SPEED * Math.cos(angle);
			double ballVy = BALL_SPEED * (-1 * Math.sin(angle));
			
			this.velocity.x = ballVx;
			this.velocity.y = ballVy;
		}
	}
	
	void move() {
		Platform.runLater(() -> {
			this.position.x += this.velocity.x;
			this.position.y += this.velocity.y;
			update();
		});
	}
	
	private void update() {
		this.relocate(position.x, position.y);
	}
	
	
}