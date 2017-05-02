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
	
	private final int playerNumber;
	
	public ClientSocket(String address, int port, int playerNumber)  {
		this.playerNumber = playerNumber;
		try {
			socket = new Socket(address, port);
			output = new ObjectOutputStream(socket.getOutputStream());
//			output.writeObject("non-host players booper\n\0");
//			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
			//System.out.println(input.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientSocket(Socket socket, int playerNumber) {
		this.playerNumber = playerNumber;
		this.socket = socket;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject("Host players BOOP");
			System.out.println("hey");
			input = new ObjectInputStream(socket.getInputStream());
			System.out.println((String)input.readObject());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PlayerNetworkData getPlayerNetworkData() {
		return new PlayerNetworkData(playerNumber, socket.getInetAddress().toString(), socket.getPort());
	}
	
	public void send(Point point) {
		try {
			output.writeObject(point);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(PlayerNetworkData data) {
		try {
			output.writeObject(data);
			output.flush();
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
		if(input==null){
			try {
				input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PlayerNetworkData data = null;
		try {
			data = (PlayerNetworkData)input.readObject();
			System.out.println(data.toString());

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
