package GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSocket {
	
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Socket socket;
	
	public ClientSocket(String address, int port) {
		try {
			socket = new Socket(address, port);
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public PlayerNetworkData getPlayerNetworkData(int playerNumber) {
		return new PlayerNetworkData(playerNumber, socket.getInetAddress().toString(), socket.getPort());
	}
	
	public ClientSocket(Socket socket) {
		this.socket = socket;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
