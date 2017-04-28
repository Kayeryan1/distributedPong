package GUI;

import java.util.HashSet;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ClientGUI extends Application {
	
	private int playerNumber = 1;
	private final static int WINDOW_WIDTH = 500;
	private final static int WINDOW_HEIGHT = 420;
	private final static int PADDLE_LENGTH = 25; 
	private final static int PADDLE_WIDTH = 3; 
	private final static int PADDLE_PADDING = 15;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Distributed Pong –– Player " + playerNumber);
		
		Paddle paddleOne = new Paddle(PADDLE_PADDING, WINDOW_HEIGHT/2, PADDLE_LENGTH, PADDLE_WIDTH, PaddleOrientation.Vertical);
		Paddle paddleTwo = new Paddle(WINDOW_WIDTH-PADDLE_PADDING, WINDOW_HEIGHT/2, PADDLE_LENGTH, PADDLE_WIDTH, PaddleOrientation.Vertical);

		Pane root = new Pane();
		root.getChildren().addAll(paddleOne, paddleTwo);
		primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.show();
		paddleOne.requestFocus();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

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

enum PaddleOrientation {
	Vertical(KeyCode.UP, KeyCode.DOWN), Horizontal(KeyCode.LEFT, KeyCode.RIGHT);
	
	private final Set directions = new HashSet<>();;
	
	private PaddleOrientation(KeyCode dir, KeyCode dir2) {
		this.directions.add(dir);
		this.directions.add(dir2);
	}
	
	public boolean contains(KeyCode dir) {
		return directions.contains(dir);
	}
}

