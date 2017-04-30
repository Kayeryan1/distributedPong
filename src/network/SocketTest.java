package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketTest {
	private Socket socket;
	private static final String HOST_ADDRESS = "10.0.0.184";
	private static final int PORT = 15001;
	
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	private ServerSocket ss;
	
	public SocketTest() {
		serverSetup();
	}
	
	public void serverSetup() {
		try {
			ss = new ServerSocket(PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			socket = ss.accept();
			System.out.printf("Server connected to socket: %d\n\n", socket.getPort());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void doStuff() {
		try {
			oos.writeObject("Hello, Ryan");
			Thread.sleep(2200);
			Object response = ois.readObject();
			System.out.println("Response from Ryan: " + (String)response);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void serverDoStuff() {
		try {
			System.out.println("!!!!!!!!here1!!!!!");
			System.out.println((String) ois.readObject());
			System.out.println("!!!!!!!!here2!!!!!");

			oos.writeObject("Hello, michael");
			System.out.println("!!!!!!!!here3!!!!!");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		/* Client */
		SocketTest socket = new SocketTest();
		socket.doStuff();
		/* Server
		SocketTest st = new SocketTest();
		st.serverDoStuff();
		*/
	}
	
}