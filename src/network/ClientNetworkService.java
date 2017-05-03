package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ClientNetworkService extends NetworkService {
	

	public ClientNetworkService(String hostAddress, int hostPort, int numPlayers) {
		super(hostAddress, hostPort, numPlayers);
		// instantiate and connect with server
		remotePlayerSockets[0] = new ClientSocket(this.hostAddress, this.hostPort, 0);
		ClientSocket hostSocket = remotePlayerSockets[0];
		playerNetworkData[0] = new PlayerNetworkData(0, this.hostAddress, this.hostPort);
		
		// wait for network data from host
		for (int i = 0; i < this.numPlayers; i++) {
			System.out.println(i);
			PlayerNetworkData data = hostSocket.receiveData();
			System.out.printf("Iteration %d \n\t recieved address %s \n\t port %d\n", i, data.address, data.port);
			playerNetworkData[i] = data;
			if (data.address.equals(hostSocket.getLocalAddress())) {
				System.out.println("This is my playerNumber "  + i);
				this.setLocalPlayerID(data.playerNumber);
			}
		}
		
		Arrays.sort(playerNetworkData);
		
		// wait for server to signal you to connect to any players lower in id
		for (int i = 1; i < getLocalPlayerID(); i++) {
			int readyPlayerID = hostSocket.receive();
			remotePlayerSockets[readyPlayerID] = new ClientSocket(playerNetworkData[readyPlayerID].address, hostPort, getLocalPlayerID());
		}

		//Arrays.sort(remotePlayerSockets);
		
		// set up server socket for connections with other players (unless you're the last client)
		if (this.getLocalPlayerID() < (numPlayers-1)) {
			try {
				this.listener = new ServerSocket(hostPort);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// signal server you are ready to accept connections from clients above your id
		hostSocket.signal(getLocalPlayerID());
		
		// accept connections from said clients
		for (int i = 0; i < (numPlayers - (getLocalPlayerID() + 1)); i++) {
			Socket socket;
			try {
				socket = listener.accept();
				String address = socket.getInetAddress().toString();
				int clientID = -1;
				for (PlayerNetworkData data : playerNetworkData) {
					if (data.address.equals(address)) {
						clientID = data.playerNumber;
					}
				}
				
				assert clientID >= 0;

				remotePlayerSockets[clientID] = new ClientSocket(socket, clientID);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
