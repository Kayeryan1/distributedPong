package GUI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

