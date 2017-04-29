package GUI;

import java.io.IOException;
import java.net.ServerSocket;

public class PlayerNetworkService {
	private ClientSocket[] opponents;
	private ServerSocket listener;
	private static final int PORT = 150001;
	private static final int NUM_OPPONENTS = 1;
	
	public PlayerNetworkService(boolean isPlayerOne) {
		opponents = new ClientSocket[NUM_OPPONENTS];
		PlayerNetworkData[] assignedData = new PlayerNetworkData[NUM_OPPONENTS];
		if (isPlayerOne) {
			try {
				listener = new ServerSocket(PORT);
				for (int i = 0; i < NUM_OPPONENTS; i++) {
					opponents[i] = new ClientSocket(listener.accept());
					assignedData[i] = opponents[i].getPlayerNetworkData(i+1);
				}
				
				for (int i = 0; i < NUM_OPPONENTS; i++) {
					for (int j = 0; j < NUM_OPPONENTS; j++) {
						// send player network data to others
						opponents[i].send(assignedData[j]);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
