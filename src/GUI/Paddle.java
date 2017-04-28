package GUI;

import javafx.scene.input.KeyCode;
import javafx.scene.shape.Line;

class Paddle extends Line {
	private final int width, length;
	private final PaddleOrientation orientation;
	private final Point position;
	
	private static final int MOVE_DELTA = 10;

	public Paddle(int x, int y, int length, int width, PaddleOrientation orientation) {
		super();
		this.position = new Point(x, y);
		this.width = width;
		this.length = length;
		this.orientation = orientation;

		this.setStrokeWidth(width);

		/* Key listener that will, on a directional key press, move the paddle */
		this.setOnKeyPressed((event) -> {
			KeyCode direction = event.getCode();
			if (orientation.contains(direction)) {
				move(direction);
			}
		});

		update();
	}
	
	private void update() {
		this.setStartX(position.x);
		this.setStartY(position.y - (length/2));
		this.setEndX(position.x);
		this.setEndY(position.y + (length/2));
		this.relocate(position.x, position.y);
	}
	
	private void move(double x, double y) {
		this.position.x = x;
		this.position.y = y;
		update();
	}
	
	private void move(KeyCode dir) {
		switch (dir) {
		case UP:
			move(position.x, position.y - MOVE_DELTA);
			break;
		case DOWN:
			move(position.x, position.y + MOVE_DELTA);
			break;
		case LEFT:
			move(position.x - MOVE_DELTA, position.y);
			break;
		case RIGHT:
			move(position.x + MOVE_DELTA, position.y);
			break;
		}
	}
}