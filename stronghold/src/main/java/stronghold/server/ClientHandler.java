package stronghold.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import stronghold.controller.LoginMenuController;
import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.network.Packet;
import stronghold.network.PacketType;
import stronghold.utils.Cryptography;

public class ClientHandler implements Runnable {
	private final Socket socket;
	private final DataInputStream sockin;
	private final DataOutputStream sockout;
	private User user;
	private String jwt;

	public ClientHandler(Socket socket) throws Exception {
		this.socket = socket;
		this.sockin = new DataInputStream(socket.getInputStream());
		this.sockout = new DataOutputStream(socket.getOutputStream());
	}

	private Packet receive() {
		try {
			return Packet.fromJson(sockin.readUTF());
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
			return null;
		}
	}

	private void send(Packet packet) {
		try {
			sockout.writeUTF(packet.toJson());
		} catch (Exception ex) {
			// ex.printStackTrace();
			System.out.println("Disconnected: " + socket.getInetAddress() + ":" + socket.getPort());
		}
	}

	@Override
	public void run() {
		while (true) {
			Packet request = receive();
			if (request == null) break;
			System.out.println("message received: " + request.getType());
			if (user != null && !Cryptography.verifyJWT(request.getJwt())) {
				System.out.println("Invalid JWT");
				continue;
			}
			switch (request.getType()) {
				case SIGNUP:
					handleSignup(request.getDataList());
					break;
				case LOGIN:
					handleLogin(request.getDataList());
					break;
				case LOGOUT:
					handleLogout();
					break;
				case GET_USER:
					handleGetUser(request.getDataList().get(0));
				default:
					break;
			}
		}
	}

	private void handleSignup(ArrayList<String> data) {
		SignupAndProfileMenuMessage message = SignupMenuController.signup(data.get(0), data.get(1),
			data.get(2), data.get(3), data.get(4),
			Integer.parseInt(data.get(5)), data.get(6));
		System.out.println(data);
		Packet response = new Packet(PacketType.RESPONSE, "", message.toString());
		send(response);
	}

	private void handleLogin(ArrayList<String> data) {
		try {
			LoginMenuMessage message = LoginMenuController.login(data.get(0), data.get(1), false);
			if (message == LoginMenuMessage.LOGIN_SUCCESS) {
				jwt = Cryptography.generateJWT(data.get(0));
				Packet response = new Packet(PacketType.RESPONSE, jwt, message.toString());
				send(response);
				user = StrongHold.getUserByName(data.get(0));
			}
			else {
				Packet response = new Packet(PacketType.RESPONSE, "", message.toString());
				send(response);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleLogout() {
		System.out.println("User " + user.getUserName() + " logged out");
		user = null;
	}

	private void handleGetUser(String username) {
		User user = StrongHold.getUserByName(username);
		
	}
}
