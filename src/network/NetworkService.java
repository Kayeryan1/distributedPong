package network;

import gui.Point;

public abstract class NetworkService {
		final String hostAddress;
		final int hostPort; 
		final int numPlayers;
		final int numOpponents;

		ClientSocket[] remotePlayerSockets;
		PlayerNetworkData[] playerNetworkData;

		private int localPlayerID;
		
		public NetworkService(String hostAddress, int hostPort, int numPlayers) {
			this.hostAddress = hostAddress;
			this.hostPort = hostPort;
			this.numPlayers = numPlayers;
			this.numOpponents = numPlayers - 1;

			remotePlayerSockets = new ClientSocket[numOpponents];
			playerNetworkData =  new PlayerNetworkData[numPlayers];
		}
		
		public void broadcastLocation(Point point) {
			for (ClientSocket opponent : remotePlayerSockets) {
				opponent.send(point);
			}
		}
		
		public Point[] receiveRemotePlayerLocations() {
			Point[] opponentLocations = new Point[numOpponents];
			for (int i = 0; i < numOpponents; i++) {
				opponentLocations[i] = remotePlayerSockets[i].receivePoint();
			}
			return opponentLocations;
		}

		public int getLocalPlayerID() {
			return localPlayerID;
		}

		public void setLocalPlayerID(int localPlayerID) {
			this.localPlayerID = localPlayerID;
		}
}
