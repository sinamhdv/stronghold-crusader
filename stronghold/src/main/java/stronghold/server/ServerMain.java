package stronghold.server;

import java.net.ServerSocket;
import java.net.Socket;

import stronghold.utils.DatabaseManager;

public class ServerMain {
	private static final int SERVER_PORT = 12345;
	public static void run() throws Exception {
		DatabaseManager.loadUsers();
		ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Connection from " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
			new Thread(new ClientHandler(clientSocket)).start();
		}
	}
}
