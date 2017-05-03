package network;

import java.io.Serializable;

public class PlayerNetworkData implements Comparable<PlayerNetworkData>, Serializable {
	private static final long serialVersionUID = 5564981942720770147L;

	final int playerNumber;
	final String address;
	final int port;
	
	public PlayerNetworkData(int playerNumber, String address, int portNumber) {
		this.playerNumber = playerNumber;
		this.address = address.replace("/", "");
		this.port = portNumber;
	}

	@Override
	public int compareTo(PlayerNetworkData other) {
		Integer thisNum = playerNumber;
		Integer otherNum = other.playerNumber;
		return thisNum.compareTo(otherNum);
	}
	
	@Override
	public String toString() {
		return "PlayerNetworkData [PlayerNumber: " + playerNumber + ", Address: " + address + ", Port: " + port + "]";
	}
}
