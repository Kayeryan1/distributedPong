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
//			output.writeObject("non-host players booper\n\0");
//			output.flush();
			input = new ObjectInputStream(socket.getInputStream());
			//System.out.println(input.readObject());
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
<<<<<<< HEAD
			output.writeObject("Host players BOOP");
=======
			output.flush();
			//output.writeObject("INITIALIZE\n\0");
>>>>>>> 0c011516a254fdb324828edc4057b3b2314a3a53
			System.out.println("hey");
			//input = new ObjectInputStream(socket.getInputStream());
			//System.out.println(input.readObject());
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
<<<<<<< HEAD
		if(input==null){
			try {
				input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
=======
		if (input == null) {
			try {
				input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
>>>>>>> 0c011516a254fdb324828edc4057b3b2314a3a53
				e.printStackTrace();
			}
		}
		PlayerNetworkData data = null;
		try {
			data = (PlayerNetworkData)input.readObject();
<<<<<<< HEAD
			System.out.println(data.toString());
=======
>>>>>>> 0c011516a254fdb324828edc4057b3b2314a3a53

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
