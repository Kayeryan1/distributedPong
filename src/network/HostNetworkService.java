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
				remotePlayerSockets[i] = new ClientSocket(listener.accept(), playerNumber);
				remotePlayerNetworkData[i] = remotePlayerSockets[i].getPlayerNetworkData();
			}
			
			for (int i = 0; i < this.numOpponents; i++) {
				for (int j = 0; j < this.numOpponents; j++) {
					// send player network data to others
					remotePlayerSockets[i].send(remotePlayerNetworkData[j]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

