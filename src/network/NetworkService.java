package network;

import gui.Point;

public abstract class NetworkService {
		final String hostAddress;
		final int hostPort; 
		final int numPlayers;
		final int numOpponents;

		ClientSocket[] opponents;
		PlayerNetworkData[] opponentData;

		int localPlayerID;
		
		public NetworkService(String hostAddress, int hostPort, int numPlayers) {
			this.hostAddress = hostAddress;
			this.hostPort = hostPort;
			this.numPlayers = numPlayers;
			this.numOpponents = numPlayers - 1;

			opponents = new ClientSocket[numOpponents];
			opponentData =  new PlayerNetworkData[numOpponents];
		}
		
		public void broadcastLocation(Point point) {
			for (ClientSocket opponent : opponents) {
				opponent.send(point);
			}
		}
		
		public Point[] receiveOpponentLocations() {
			Point[] opponentLocations = new Point[numOpponents];
			for (int i = 0; i < numOpponents; i++) {
				opponentLocations[i] = opponents[i].receivePoint();
			}
			return opponentLocations;
		}
}
