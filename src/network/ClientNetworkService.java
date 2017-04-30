package network;

public class ClientNetworkService extends NetworkService {

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		opponents[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		opponentData[0] = new PlayerNetworkData(0, this.hostAddress, this.hostPort);
		
		// wait for network data
		for (int i = 0; i < this.numOpponents; i++) {
			PlayerNetworkData data = opponents[0].receiveData();
			opponentData[i+1] = data;
			if (data.address.equals(opponents[0].getLocalAddress())) {
				this.localPlayerID = data.playerNumber;
			}
		}
	}


}
