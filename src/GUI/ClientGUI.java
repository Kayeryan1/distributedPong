package GUI;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientGUI extends Application {
	
	private int playerNumber = 1;
	private final static int WINDOW_WIDTH = 500;
	private final static int WINDOW_HEIGHT = 420;
	final static int PADDLE_LENGTH = 35; 
	final static int PADDLE_WIDTH = 3; 
	private final static int PADDLE_PADDING = 15;	// margin between screen edge and paddles

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Distributed Pong –– Player " + playerNumber);
		
		Paddle paddleOne = new Paddle(
				PADDLE_PADDING, 					// x position
				WINDOW_HEIGHT/2, 					// y position
				PaddleOrientation.Vertical, 		// orientation (determines which edge on screen to position at)
				true								// if true, this is the machine's player's paddle
		);
		Paddle paddleTwo = new Paddle(
				WINDOW_WIDTH-PADDLE_PADDING, 
				WINDOW_HEIGHT/2, 
				PaddleOrientation.Vertical, 
				false
		);

		Pane root = new Pane();
		root.setStyle("-fx-background-color: " + Color.RED);
		root.getChildren().addAll(paddleOne, paddleTwo);

		primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.show();

		paddleOne.requestFocus();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

