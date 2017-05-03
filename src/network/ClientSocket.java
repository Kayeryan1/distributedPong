package network;

import java.io.EOFException;
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
			//output.flush();
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
	
	public void signal(Integer sourcePlayerID) {
		try {
			output.writeUnshared(sourcePlayerID);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public int receive() {
		Integer retVal = -1;
		try {
			retVal = (Integer)input.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public Point receivePoint() {
		Point toReturn = null;
		try {
			Object obj = input.readObject();
			toReturn = ((Point)obj);
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
			Object obj = input.readObject();
			data = (PlayerNetworkData)obj;

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
		return socket.getLocalAddress().toString().replace("/", "");
	}

	@Override
	public int compareTo(ClientSocket o) {
		Integer thisNum = localPlayerID;
		Integer otherNum = o.localPlayerID;
		return thisNum.compareTo(otherNum);
	}

}
