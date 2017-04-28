package GUI;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {
	private Socket socket;
	private static final String ADDRESS = "10.0.0.184";
	private static final int PORT = 15001;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	public SocketTest() {
		try {
			socket = new Socket(ADDRESS, PORT);
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void doStuff() {
		try {
			oos.writeObject("Hello, Ryan");
			Object response = ois.readObject();
			System.out.println("Response from Ryan: " + (String)response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
	}
	
}