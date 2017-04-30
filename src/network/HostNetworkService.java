package network;

import java.io.IOException;
import java.net.ServerSocket;

public class HostNetworkService extends NetworkService {
	private ServerSocket listener;

	public HostNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		this.localPlayerID = 0;
		try {
			listener = new ServerSocket(this.hostPort);
			for (int i = 0; i < this.numOpponents; i++) {
				int playerNumber = i+1;
				opponents[i] = new ClientSocket(listener.accept(), playerNumber);
				opponentData[i] = opponents[i].getPlayerNetworkData();
			}
			
			for (int i = 0; i < this.numOpponents; i++) {
				for (int j = 0; j < this.numOpponents; j++) {
					// send player network data to others
					opponents[i].send(opponentData[j]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

