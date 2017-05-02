package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import gui.Point;

public class ClientSocket implements Comparable<ClientSocket> {
	
	private ObjectInputStream input = null;
	private ObjectOutputStream output;
	private Socket socket;
	
	private final int localPlayerID;
	
	/** Client player's constructor for sockets */
	public ClientSocket(String address, int port, int playerNumber)  {
		this.localPlayerID = playerNumber;
		try {
			socket = new Socket(address, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** Host player's constructor for sockets */
	public ClientSocket(Socket socket, int playerNumber) {
		this.localPlayerID = playerNumber;
		this.socket = socket;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public PlayerNetworkData getPlayerNetworkData() {
		return new PlayerNetworkData(localPlayerID, socket.getInetAddress().toString(), socket.getPort());
	}
	
	public void send(Point point) {
		try {
			output.writeUnshared(point);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(PlayerNetworkData data) {
		try {
			output.writeUnshared(data);
			//output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Point receivePoint() {
		Point toReturn = null;
		try {
			toReturn = ((Point)input.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return toReturn;
	}
	
	public PlayerNetworkData receiveData() {

		if (input == null) {
			try {
				input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		PlayerNetworkData data = null;
		try {
			data = (PlayerNetworkData)input.readObject();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLocalAddress() {
		return socket.getLocalAddress().toString();
	}

	@Override
	public int compareTo(ClientSocket o) {
		Integer thisNum = localPlayerID;
		Integer otherNum = o.localPlayerID;
		return thisNum.compareTo(otherNum);
	}

}
