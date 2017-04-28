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

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Distributed Pong –– Player " + playerNumber);
		
		Paddle paddleOne = new Paddle(1,1,1,1);

		Line paddleTwo = new Line();
		paddleTwo.setStartX(WINDOW_WIDTH-(20 + PADDLE_WIDTH));
		paddleTwo.setStartY((WINDOW_HEIGHT/2) - (PADDLE_LENGTH/2));
		paddleTwo.setEndX(paddleTwo.getStartX());
		paddleTwo.setEndY((WINDOW_HEIGHT/2) + (PADDLE_LENGTH/2));
		
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
	private Point position;

	public Paddle(int x, int y, int length, int width) {
		super();
		this.position = new Point(x, y);
		this.width = width;
		this.length = length;

		this.setStrokeWidth(width);
		
		update();
	}
	
	private void update() {
		this.setStartX(position.x - (length/2));
		this.setStartY(position.y - (length/2));
		this.setEndX(position.x);
		this.setEndY(position.y + (length/2));
	}
}
