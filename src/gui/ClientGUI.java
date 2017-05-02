package gui;

import java.util.ArrayList;
import java.util.List;

import com.sun.prism.paint.Color;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import network.ClientNetworkService;
import network.HostNetworkService;
import network.NetworkService;

public class ClientGUI extends Application {
	final static int PADDLE_LENGTH = 35; 
	final static int PADDLE_WIDTH = 3; 
	private final static int WINDOW_WIDTH = 500;
	private final static int WINDOW_HEIGHT = 420;
	private final static int PADDLE_PADDING = 15;	// margin between screen edge and paddles

	private final List<Paddle> paddles = new ArrayList<>();
	private NetworkService service;
	private int playerNumber = 0;
	
	private boolean gameOver = false;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//TODO: grab all these variables using getParameters()
<<<<<<< HEAD
		final boolean isHost = false;
		final String address = "10.0.0.22";
		final int port = 15001;
		final int numPlayers = 2;
		playerNumber = 1;
=======
		final boolean isHost = Boolean.valueOf(getParameters().getRaw().get(0));
		final String address = String.valueOf(getParameters().getRaw().get(1));
		final int port = Integer.parseInt(getParameters().getRaw().get(2));
		final int numPlayers = Integer.parseInt(getParameters().getRaw().get(3));
>>>>>>> 0c011516a254fdb324828edc4057b3b2314a3a53

		if (isHost) {
			service = new HostNetworkService(address, port, numPlayers);
		} else {
			service = new ClientNetworkService(address, port, numPlayers);
		}
		playerNumber = service.getLocalPlayerID();

		primaryStage.setTitle("Distributed Pong –– Player " + (playerNumber + 1));

		Pane root = new Pane();
		root.setStyle("-fx-background-color: " + Color.RED);

		primaryStage.setScene(new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT));
		primaryStage.show();

		initializePaddles(root, 2);
		
<<<<<<< HEAD
		initializePaddles(root, 2);

		
=======
>>>>>>> 0c011516a254fdb324828edc4057b3b2314a3a53
		startGameLoop();
	}
	
	private void startGameLoop() {
		while (!gameOver) {

			// broadcast point with id set
			Point myPoint = paddles.get(playerNumber).getLocation();
			myPoint.playerNumber = playerNumber;
			service.broadcastLocation(myPoint);
			
			//TODO: What happens when a client sends a message, but the receiver isn't listening yet? This might be a problem.

			// wait for all locations of opponents
			Point[] locations = service.receiveRemotePlayerLocations();

			// move all the remote player's paddles with new points
			for (Point point : locations) {
				int playerID = point.playerNumber;
				paddles.get(playerID).move(point);
			}
			
			// detect collisions
			
			// check for game score
			
			// move ball ...
		}
	}
	
	private void initializePaddles(Pane root, int numPlayers) {
		//TODO: generate paddles by numPlayers
		Paddle paddleOne = new Paddle(
				PADDLE_PADDING, 					// x position
				WINDOW_HEIGHT/2, 					// y position
				PaddleOrientation.Vertical, 		// orientation (determines which edge on screen to position at)
				true								// if true, this is the local player's paddle
		);
		Paddle paddleTwo = new Paddle(
				WINDOW_WIDTH-PADDLE_PADDING, 
				WINDOW_HEIGHT/2, 
				PaddleOrientation.Vertical, 
				false
		);
		
		// store the paddles for game loop
		paddles.add(paddleOne);
		paddles.add(paddleTwo);
		root.getChildren().addAll(paddles);			// add paddles to GUI
		paddles.get(playerNumber).requestFocus(); 	// request focus so listeners can receive key input
		System.out.println(paddles.get(playerNumber).isFocused());
	}
	
	public static void main(String[] args) {
		//TODO: pass data from args 
		launch(args);
	}
}

