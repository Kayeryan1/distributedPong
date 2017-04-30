package network;

public class ClientNetworkService extends NetworkService {

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		remotePlayerSockets[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		playerNetworkData[0] = new PlayerNetworkData(0, this.hostAddress, this.hostPort);
		
		// wait for network data
		for (int i = 0; i < this.numPlayers; i++) {
			PlayerNetworkData data = remotePlayerSockets[0].receiveData();
			if (data.address.equals(remotePlayerSockets[0].getLocalAddress())) {
				this.localPlayerID = data.playerNumber;
			} else if (i != 0) {
				remotePlayerSockets[i] = new ClientSocket(data.address, data.port, data.playerNumber);
			}
			playerNetworkData[i] = data;
		}
	}
}
