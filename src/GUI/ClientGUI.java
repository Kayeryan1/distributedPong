package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
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
		
		Paddle paddleOne = new Paddle(PADDLE_PADDING, WINDOW_HEIGHT/2, PADDLE_LENGTH, PADDLE_WIDTH, Orientation.Vertical);
		Paddle paddleTwo = new Paddle(WINDOW_WIDTH-PADDLE_PADDING, WINDOW_HEIGHT/2, PADDLE_LENGTH, PADDLE_WIDTH, Orientation.Vertical);

		Pane root = new Pane();
		root.getChildren().addAll(paddleOne, paddleTwo);
		primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

class Paddle extends Line {
	private final int width, length;
	private final Orientation orientation;
	private final Point position;

	public Paddle(int x, int y, int length, int width, Orientation orientation) {
		super();
		this.position = new Point(x, y);
		this.width = width;
		this.length = length;
		this.orientation = orientation;

		this.setStrokeWidth(width);
		update();
	}
	
	private void update() {
		this.setStartX(position.x);
		this.setStartY(position.y - (length/2));
		this.setEndX(position.x);
		this.setEndY(position.y + (length/2));
	}
	
	private void setupListener() {
		this.setOnKeyPressed((event) -> {
			if (this.orientation == Orientation.Vertical) {
				switch (event.getCode()) {
				case UP:
					break;
				case DOWN:
					break;
				}
			} else {
				switch (event.getCode()) {
				case LEFT:
					break;
				case RIGHT:
					break;
				}
			}
		});
	}
}

enum Orientation {
	Vertical, Horizontal;
}

