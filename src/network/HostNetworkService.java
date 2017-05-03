package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Arrays;

public class HostNetworkService extends NetworkService {
	//private ServerSocket listener;

	public HostNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		System.out.println("Got to here 0 ");
		this.setLocalPlayerID(0);
		try {
			listener = new ServerSocket(this.hostPort);
			System.out.println("Got to here");

			playerNetworkData[0] = new PlayerNetworkData(0, hostAddress, hostPort);
			for (int i = 0; i < this.numOpponents; i++) {
				int playerNumber = i+1;
				remotePlayerSockets[i] = new ClientSocket(listener.accept(), playerNumber);
				System.out.println(remotePlayerSockets[i]);
				playerNetworkData[playerNumber] = remotePlayerSockets[i].getPlayerNetworkData();
				System.out.println("Got to here 0 for player " + playerNumber);
			}
			System.out.println("Got to here 1 ");
			
			for (int i = 0; i < this.numOpponents; i++) {
				for (int j = 0; j < this.numPlayers; j++) {
					// send player network data to others
					System.out.println("About to send: " + playerNetworkData[j].toString());
					remotePlayerSockets[i].send(playerNetworkData[j]);
					System.out.println("Got to here 1 for player " + i);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("Got to here 2 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Arrays.sort(remotePlayerSockets);
		
		/* Coordinate the connections between the remaining players */
		for (int i = 0; i < remotePlayerSockets.length; i++) {
			int idOfReadyPlayer = remotePlayerSockets[i].receive();
			for (int j = i+1; j < remotePlayerSockets.length; j++) {
				remotePlayerSockets[j].signal(idOfReadyPlayer);
			}
			
			
		}
	}
}

