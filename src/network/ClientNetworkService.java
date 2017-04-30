package network;

public class ClientNetworkService extends NetworkService {

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		remotePlayerSockets[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		remotePlayerNetworkData[0] = new PlayerNetworkData(0, this.hostAddress, this.hostPort);
		
		// wait for network data
		for (int i = 1; i <= this.numOpponents; i++) {
			PlayerNetworkData data = remotePlayerSockets[0].receiveData();
			remotePlayerNetworkData[i] = data;
			if (data.address.equals(remotePlayerSockets[0].getLocalAddress())) {
				this.localPlayerID = data.playerNumber;
			}
		}
	}
}
