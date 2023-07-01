package stronghold.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import stronghold.model.User;
import stronghold.network.Packet;
import stronghold.utils.Cryptography;

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

	private Packet receive() {
		try {
			return Packet.fromJson(sockin.readUTF());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	private void send(Packet packet) {
		try {
			sockout.writeUTF(packet.toJson());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (true) {
			Packet request = receive();
			if (user != null && !Cryptography.verifyJWT(request.getJwt())) continue;
			switch (request.getType()) {
				case SIGNUP:
					break;
				case LOGIN:
					break;
				default:
					break;
			}
		}
	}
}
