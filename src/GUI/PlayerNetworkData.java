package GUI;

public class PlayerNetworkData {
	final int playerNumber;
	final String address;
	final int port;
	
	public PlayerNetworkData(int playerNumber, String address, int portNumber) {
		this.playerNumber = playerNumber;
		this.address = address;
		this.port = portNumber;
	}
}
