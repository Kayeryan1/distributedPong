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
	final static int WINDOW_WIDTH = 500;
	final static int WINDOW_HEIGHT = 420;
	final static int PADDLE_PADDING = 15;	// margin between screen edge and paddles
	final static int BALL_RADIUS = PADDLE_LENGTH/7;

	private final List<Paddle> paddles = new ArrayList<>();
	private Ball pongBall;
	private NetworkService service;
	private int playerNumber = 0;
	private int lastBounceID = -1;
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
		this.pongBall = initializeBall(root);
		System.out.println("hey");
		startGameLoop();
	}
	
	private void startGameLoop() {
		Runnable gameLoop = () -> {
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
				processBallBounces();
				detectWallBounces();
				
				// check for game score
				
				// move ball ...
				pongBall.move();
			}
		};
		new Thread(gameLoop).start();
	}
	
	private void processBallBounces() {
		double ballX = pongBall.position.x;
		double ballY = pongBall.position.y;
		int i = 0;
		for (Paddle paddle : paddles) {
			double paddleX = paddle.position.x;
			double paddleY = paddle.position.y;
			if (paddle.orientation == PaddleOrientation.Horizontal) {
				if (Math.abs(ballX - paddleX) <= (PADDLE_LENGTH/2 + (BALL_RADIUS * 1)) && Math.abs(paddleY-ballY) <= BALL_RADIUS) {
					if (lastBounceID != i) {
						pongBall.paddleBounce(paddle);
					}
					lastBounceID = i;
				}
			} else {
				if (Math.abs(ballY - paddleY) <= ((PADDLE_LENGTH/2) + (BALL_RADIUS * 1)) && Math.abs(paddleX-ballX) <= BALL_RADIUS) {
					if (lastBounceID != i) {
						pongBall.paddleBounce(paddle);
					}
					lastBounceID = i;
				}
			}
			i++;
		}
	}
	
	public void detectWallBounces(){
		double ballX = pongBall.position.x;
		double ballY = pongBall.position.y;
		
		if( ballX <= 0 ){
			pongBall.velocity.x = Math.abs(pongBall.velocity.x);
		}else if(ballX >= ClientGUI.WINDOW_WIDTH){
			pongBall.velocity.x = -1*(Math.abs(pongBall.velocity.x));
		}else if( ballY <= 0 ){
			pongBall.velocity.y = Math.abs(pongBall.velocity.y);
		}else if(ballY >= ClientGUI.WINDOW_HEIGHT){
			pongBall.velocity.y = -1*(Math.abs(pongBall.velocity.y));
		}
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
	
	public Ball initializeBall(Pane root) {
		double ballSpeed = 0.75;
		if (paddles.size() == 4) {
			ballSpeed = 3;
		}
		double initialXVelocity = -1 * ballSpeed;
		int initialYVelocity = 0;
		Ball ball = new Ball(WINDOW_WIDTH/2, WINDOW_HEIGHT/2, initialXVelocity, initialYVelocity, BALL_RADIUS);
		ball.setSpeed(ballSpeed);
		root.getChildren().add(ball);
		return ball;
	}
	

	
	public static void main(String[] args) {
		//TODO: pass data from args 
		launch(args);
	}
}

