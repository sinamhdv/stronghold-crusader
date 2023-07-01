package stronghold.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import stronghold.model.User;

public class ClientHandler implements Runnable {
	private final Socket socket;
	private final DataInputStream sockin;
	private final DataOutputStream sockout;
	private User user;

	public ClientHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.sockin = new DataInputStream(socket.getInputStream());
		this.sockout = new DataOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		while (true) {
			
		}
	}
}
