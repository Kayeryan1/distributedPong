package GUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;

public class PlayerNetworkService {
	//TODO: these should be passed into the command line
	private static final String HOST_ADDRESS = "10.0.0.184";
	private static final int HOST_PORT = 150001;
	private static final int NUM_PLAYERS = 2;
	private static final int NUM_OPPONENTS = NUM_PLAYERS - 1;

	private ClientSocket[] opponents;
	PlayerNetworkData[] opponentData;
	private ServerSocket listener;

	private int myPlayerNumber = 0;
	
	// TODO: probably refactor this into two services: a host service and a player service
	public PlayerNetworkService(boolean isPlayerOne) {
		opponents = new ClientSocket[NUM_OPPONENTS];
		opponentData =  new PlayerNetworkData[NUM_OPPONENTS];
		if (isPlayerOne) {
			try {
				listener = new ServerSocket(HOST_PORT);
				for (int i = 0; i < NUM_OPPONENTS; i++) {
					int playerNumber = i+1;
					opponents[i] = new ClientSocket(listener.accept(), playerNumber);
					opponentData[i] = opponents[i].getPlayerNetworkData();
				}
				
				for (int i = 0; i < NUM_OPPONENTS; i++) {
					for (int j = 0; j < NUM_OPPONENTS; j++) {
						// send player network data to others
						opponents[i].send(opponentData[j]);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// instantiate and connect with server
			opponents[0] = new ClientSocket(HOST_ADDRESS, HOST_PORT, 0);
			opponentData[0] = new PlayerNetworkData(0, HOST_ADDRESS, HOST_PORT);
			
			// wait for network data
			for (int i = 0; i < NUM_OPPONENTS; i++) {
				PlayerNetworkData data = opponents[0].receiveData();
				opponentData[i+1] = data;
				if (data.address.equals(opponents[0].getLocalAddress())) {
					myPlayerNumber = data.playerNumber;
				}
			}
			
			// sort by player numbers
			Arrays.sort(opponents);
			Arrays.sort(opponentData);
			
			//TODO: make sure this isn't out of bounds
			// now test any initial communicatoin here
			for (PlayerNetworkData data : opponentData) {
				if (data.playerNumber != myPlayerNumber) {
					opponents[data.playerNumber].sendPoint(new Point(5,5));
				}
			}

			/* wait for a bunch? */
			for (PlayerNetworkData data : opponentData) {
				if (data.playerNumber != myPlayerNumber) {
					opponents[data.playerNumber].receivePoint();
				}
			}
		}
	}
}
