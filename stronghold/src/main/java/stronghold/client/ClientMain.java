package stronghold.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javafx.application.Application;
import javafx.stage.Stage;
import stronghold.network.Packet;
import stronghold.view.LoginMenu;

public class ClientMain extends Application {
	private static final String SERVER_HOST = "127.0.0.1";
	private static final int SERVER_PORT = 12345;

	private static DataInputStream sockin;
	private static DataOutputStream sockout;

	@Override
	public void start(Stage stage) throws Exception {
		Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
		sockin = new DataInputStream(socket.getInputStream());
		sockout = new DataOutputStream(socket.getOutputStream());
		new LoginMenu().start(stage);
	}

	public static Packet receive() {
		try {
			return Packet.fromJson(sockin.readUTF());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void send(Packet packet) {
		try {
			sockout.writeUTF(packet.toJson());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
