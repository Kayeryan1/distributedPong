package network;

public class ClientNetworkService extends NetworkService {

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		remotePlayerSockets[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		
		// wait for network data
		String myAddress = remotePlayerSockets[0].getLocalAddress();
		for (int i = 0; i < this.numOpponents; i++) {
			PlayerNetworkData data = remotePlayerSockets[0].receiveData();
			playerNetworkData[i] = data;
			if (data.address.equals(myAddress)) {
				this.localPlayerID = data.playerNumber;
			}
		}
	}
}
