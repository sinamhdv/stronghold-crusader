package stronghold.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

import javafx.application.Platform;
import stronghold.controller.ChatMenuController;
import stronghold.controller.LoginMenuController;
import stronghold.controller.MainMenuController;
import stronghold.controller.SignupMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.MainMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.Game;
import stronghold.model.PendingGame;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.chat.ChatData;
import stronghold.network.Packet;
import stronghold.network.PacketType;
import stronghold.utils.Cryptography;
import stronghold.utils.DatabaseManager;
import stronghold.utils.TransferSerialization;

public class ClientHandler implements Runnable {
	private final Socket socket;
	private final DataInputStream sockin;
	private final DataOutputStream sockout;
	private User user;
	private String jwt;
	private String currentGameId;
	private boolean isChatting;

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
			ex.printStackTrace();
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
					break;
				case UPDATE_USER:
					handleUpdateUser(request.getDataList().get(0));
					break;
				case CREATE_GAME:
					handleCreateGame(request.getDataList().get(0));
					break;
				case JOIN_GAME:
					handleJoinGame(request.getDataList().get(0));
					break;
				case SYNC_MAP:
					receiveGameMap(Integer.parseInt(request.getDataList().get(0)),
						request.getDataList().get(1));
					break;
				case START_CHAT:
					handleStartChat();
					break;
				case END_CHAT:
					handleEndChat();
					break;
				case UPDATE_CHAT_DATA:
					handleUpdateChatData(Integer.parseInt(request.getDataList().get(0)));
					break;
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
				user.setClientHandler(this);
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
		user.setClientHandler(null);
		user = null;
	}

	private void handleGetUser(String username) {
		User user = StrongHold.getUserByName(username);
		if (user == null)
			send(new Packet(PacketType.ERROR, ""));
		else
			send(new Packet(PacketType.RESPONSE, "", new Gson().toJson(user)));
	}

	private void handleUpdateUser(String jsonData) {
		User user = new Gson().fromJson(jsonData, User.class);
		synchronized (StrongHold.getUsers()) {
			StrongHold.getUsers().remove(StrongHold.getUserByName(user.getUserName()));
		}
		StrongHold.addUser(user);
	}

	private void handleCreateGame(String mapName) {
		String gameId = MainMenuController.getNewGameId();
		MainMenuMessage message = MainMenuController.serverCreateGame(mapName, user, gameId);
		Packet response = new Packet(PacketType.RESPONSE, "", message.toString());
		if (message == MainMenuMessage.SUCCESS) {
			response.addData(gameId);
			currentGameId = gameId;
		}
		send(response);
		if (message == MainMenuMessage.SUCCESS) {
			sendGameMap();
		}
	}

	private void handleJoinGame(String gameId) {
		MainMenuMessage message = MainMenuController.addPlayerToGame(gameId, user);
		Packet response = new Packet(PacketType.RESPONSE, "", message.toString());
		if (message == MainMenuMessage.SUCCESS)
			response.addData(Integer.toString(StrongHold.getPendingGameById(gameId).getPlayers().size() - 1));
		send(response);
		if (message == MainMenuMessage.SUCCESS) {
			currentGameId = gameId;
			sendGameMap();
			MainMenuController.checkStartGame(gameId);
		}
	}

	public void sendGameMap() {
		PendingGame game = StrongHold.getPendingGameById(currentGameId);
		String gameData = TransferSerialization.serialize(game.getGame());
		send(new Packet(PacketType.CONTENT_LENGTH, "", Integer.toString(gameData.length())));
		try {
			sockout.writeBytes(gameData);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception in sending map");
		}
	}

	public void signalStartGame() {
		PendingGame game = StrongHold.getPendingGameById(currentGameId);
		String jsonData = new Gson().toJson(game.getPlayers());
		send(new Packet(PacketType.RESPONSE, "", jsonData));
	}

	public void receiveGameMap(int contentLength, String nextPlayer) {
		System.out.println("next player: " + nextPlayer);
		try {
			byte[] bytes = sockin.readNBytes(contentLength);
			String mapData = new String(bytes);
			Game map = (Game) TransferSerialization.deserialize(mapData);
			StrongHold.getPendingGameById(currentGameId).setGame(map);
			StrongHold.getUserByName(nextPlayer).getClientHandler().sendGameMap();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Chat
	private void handleStartChat() {
		isChatting = true;
		ChatMenuController.setOnline(user.getUserName());
		broadcastChatData();
	}

	private void handleEndChat() {
		isChatting = false;
		ChatMenuController.setOffline(user.getUserName());
		send(new Packet(PacketType.END_CHAT, ""));
		broadcastChatData();
	}

	private void handleUpdateChatData(int length) {
		try {
			byte[] bytes = sockin.readNBytes(length);
			String data = new String(bytes);
			ChatData chatData = new Gson().fromJson(data, ChatData.class);
			ChatMenuController.setChatData(chatData);
			broadcastChatData();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void sendChatData() {
		String data = new Gson().toJson(ChatMenuController.getChatData());
		send(new Packet(PacketType.CONTENT_LENGTH, "", Integer.toString(data.length())));
		try {
			sockout.writeBytes(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception in sending chat data");
		}
	}

	private void broadcastChatData() {
		DatabaseManager.saveChatData();
		for (User user : StrongHold.getUsers())
			if (user.getClientHandler() != null && user.getClientHandler().isChatting)
				user.getClientHandler().sendChatData();
	}
}
