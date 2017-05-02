package network;

public class ClientNetworkService extends NetworkService {

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		remotePlayerSockets[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		playerNetworkData[0] = new PlayerNetworkData(0, this.hostAddress, this.hostPort);
		
		// wait for network data
		for (int i = 0; i < this.numPlayers; i++) {
			System.out.println(i);
			PlayerNetworkData data = remotePlayerSockets[0].receiveData();
			System.out.printf("Iteration %d \n\t recieved address %s \n\t port %d\n", i, data.address, data.port);
			playerNetworkData[i] = data;
			if (data.address.equals(remotePlayerSockets[0].getLocalAddress())) {
				System.out.println("This is my playerNumber "  + i);
				this.localPlayerID = data.playerNumber;
			}
		}
	}
}
