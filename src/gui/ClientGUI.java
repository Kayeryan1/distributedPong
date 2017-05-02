package gui;

import java.util.ArrayList;
import java.util.Arrays;
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

		final boolean isHost = Boolean.valueOf(getParameters().getRaw().get(0));
		final String address = String.valueOf(getParameters().getRaw().get(1));
		final int port = Integer.parseInt(getParameters().getRaw().get(2));
		int numPlayers = Integer.parseInt(getParameters().getRaw().get(3));


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
		System.out.println("hey2");
		primaryStage.show();

		initializePaddles(root, numPlayers);
		System.out.println("hey");
		startGameLoop();
	}
	
	private void startGameLoop() {
		Runnable runner = () -> {
			while (!gameOver) {

				// broadcast point with id set
				Point myPoint = paddles.get(playerNumber).getLocation();
				myPoint.playerNumber = playerNumber;
				service.broadcastLocation(myPoint);
				
				//TODO: What happens when a client sends a message, but the receiver isn't listening yet? This might be a problem.

				// wait for all locations of opponents
				Point[] locations = service.receiveRemotePlayerLocations();
				System.out.println(Arrays.toString(locations));

				// move all the remote player's paddles with new points
				for (Point point : locations) {
					int playerID = point.playerNumber;
					paddles.get(playerID).move(point);
				}
				
				// detect collisions
				
				// check for game score
				
				// move ball ...
			}
		};
		new Thread(runner).start();
	}
	
	private void initializePaddles(Pane root, int numPlayers) {
		for (int i = 0; i < numPlayers; i++) {
			int x = 0, y = 0;
			switch (i) {
			case 0:
				x = PADDLE_PADDING;
				y = WINDOW_HEIGHT/2;
				break;
			case 1:
				x = WINDOW_WIDTH - PADDLE_PADDING;
				y = WINDOW_HEIGHT/2;
				break;
			case 2: 
				x = WINDOW_WIDTH / 2;
				y = PADDLE_PADDING;
				break;
			case 3:
				x = WINDOW_WIDTH / 2;
				y = WINDOW_HEIGHT-PADDLE_PADDING;
				break;
			}
			Paddle paddle = new Paddle(
					x,
					y,
					(i < 2) ? PaddleOrientation.Vertical : PaddleOrientation.Horizontal,
					(i == playerNumber) ? true : false
			);
			paddles.add(paddle);
		}
		
		// store the paddles for game loop
		root.getChildren().addAll(paddles);			// add paddles to GUI
		paddles.get(playerNumber).requestFocus(); 	// request focus so listeners can receive key input
	}
	
	public static void main(String[] args) {
		//TODO: pass data from args 
		launch(args);
	}
}

