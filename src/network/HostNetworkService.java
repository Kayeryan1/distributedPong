package network;

import java.io.IOException;
import java.net.ServerSocket;

public class HostNetworkService extends NetworkService {
	private ServerSocket listener;

	public HostNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		System.out.println("Got to here 0 ");
		this.localPlayerID = 0;
		try {
			listener = new ServerSocket(this.hostPort);
			System.out.println("Got to here");
			for (int i = 0; i < this.numOpponents; i++) {
				int playerNumber = i+1;
				remotePlayerSockets[i] = new ClientSocket(listener.accept(), playerNumber);
				System.out.println(remotePlayerSockets[i]);
				remotePlayerNetworkData[i] = remotePlayerSockets[i].getPlayerNetworkData();
				System.out.println("Got to here 0 for player " + playerNumber);
			}
			System.out.println("Got to here 1 ");
			
			for (int i = 0; i < this.numOpponents; i++) {
				for (int j = 0; j < this.numOpponents; j++) {
					// send player network data to others
					remotePlayerSockets[i].send(remotePlayerNetworkData[j]);
					System.out.println("Got to here 1 for player " + i);
				}
			}
			System.out.println("Got to here 2 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

