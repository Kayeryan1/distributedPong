package GUI;

public class PlayerNetworkData implements Comparable<PlayerNetworkData> {
	final int playerNumber;
	final String address;
	final int port;
	
	public PlayerNetworkData(int playerNumber, String address, int portNumber) {
		this.playerNumber = playerNumber;
		this.address = address;
		this.port = portNumber;
	}

	@Override
	public int compareTo(PlayerNetworkData other) {
		Integer thisNum = playerNumber;
		Integer otherNum = other.playerNumber;
		return thisNum.compareTo(otherNum);
	}
}
