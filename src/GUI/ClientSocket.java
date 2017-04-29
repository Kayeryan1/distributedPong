package GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket implements Comparable<ClientSocket> {
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	
	private final int playerNumber;
	
	public ClientSocket(String address, int port, int playerNumber)  {
		this.playerNumber = playerNumber;
		try {
			socket = new Socket(address, port);
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PlayerNetworkData getPlayerNetworkData() {
		return new PlayerNetworkData(playerNumber, socket.getInetAddress().toString(), socket.getPort());
	}
	
	public ClientSocket(Socket socket, int playerNumber) {
		this.playerNumber = playerNumber;
		this.socket = socket;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: refactor these send and receive methods to just one method somehow
	public void sendPoint(Point point) {
		try {
			output.writeObject(point);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(PlayerNetworkData data) {
		try {
			output.writeObject(data);
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
		Integer thisNum = playerNumber;
		Integer otherNum = o.playerNumber;
		return thisNum.compareTo(otherNum);
	}

}
