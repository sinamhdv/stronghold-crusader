package stronghold.client;

import java.util.List;

import com.google.gson.Gson;

import javafx.application.Platform;
import stronghold.controller.ChatMenuController;
import stronghold.controller.GameMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.MainMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.Game;
import stronghold.model.PendingGame;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.chat.ChatData;
import stronghold.model.map.Map;
import stronghold.network.Packet;
import stronghold.network.PacketType;
import stronghold.utils.TransferSerialization;
import stronghold.view.ChatMenu;
import stronghold.view.GameMenu;
import stronghold.view.GameWaitingRoom;
import stronghold.view.LoginMenu;
import stronghold.view.MapScreen;

public class SendRequests {
	private static String jwt;

	public static SignupAndProfileMenuMessage requestSignup(String username, String nickName, String password,
			String email, String slogan, int securityQuestionIndex, String securityQuestionAnswer) {
		Packet request = new Packet(PacketType.SIGNUP, "", new String[] {
				username, nickName, password, email, slogan, Integer.toString(securityQuestionIndex),
				securityQuestionAnswer});
		ClientMain.send(request);
		Packet response = ClientMain.receive();
		return SignupAndProfileMenuMessage.valueOf(response.getDataList().get(0));
	}

	public static LoginMenuMessage requestLogin(String username, String password, boolean stayLoggedIn) {
		Packet request = new Packet(PacketType.LOGIN, "", List.of(username, password));
		ClientMain.send(request);
		Packet response = ClientMain.receive();
		jwt = response.getJwt();
		return LoginMenuMessage.valueOf(response.getDataList().get(0));
	}

	public static void requestLogout() {
		ClientMain.send(new Packet(PacketType.LOGOUT, jwt));
	}

	public static User getUserFromServer(String username) {
		ClientMain.send(new Packet(PacketType.GET_USER, jwt, username));
		Packet response = ClientMain.receive();
		if (response.getType() == PacketType.ERROR) return null;
		return new Gson().fromJson(response.getDataList().get(0), User.class);
	}

	public static void requestUpdateUser() {
		ClientMain.send(new Packet(PacketType.UPDATE_USER, jwt, new Gson().toJson(StrongHold.getCurrentUser())));
	}

	public static MainMenuMessage createGame(String mapName) {
		ClientMain.send(new Packet(PacketType.CREATE_GAME, jwt, mapName));
		Packet response = ClientMain.receive();
		MainMenuMessage message = MainMenuMessage.valueOf(response.getDataList().get(0));
		if (message != MainMenuMessage.SUCCESS) return message;
		StrongHold.setMyPlayerIndex(0);
		GameWaitingRoom.setGameId(response.getDataList().get(1));
		return message;
	}

	public static MainMenuMessage joinGame(String gameId) {
		ClientMain.send(new Packet(PacketType.JOIN_GAME, jwt, gameId));
		Packet response = ClientMain.receive();
		MainMenuMessage message = MainMenuMessage.valueOf(response.getDataList().get(0));
		if (message != MainMenuMessage.SUCCESS) return message;
		StrongHold.setMyPlayerIndex(Integer.parseInt(response.getDataList().get(1)));
		return message;
	}

	public static Game receiveGameMap() {
		Packet contentLength = ClientMain.receive();
		assert(contentLength.getType() == PacketType.CONTENT_LENGTH);
		int length = Integer.parseInt(contentLength.getDataList().get(0));
		try {
			byte[] bytes = ClientMain.getSockin().readNBytes(length);
			String gameData = new String(bytes);
			Game game = (Game) TransferSerialization.deserialize(gameData);
			game.setCurrentPlayerIndex(StrongHold.getMyPlayerIndex());
			return game;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void sendGameMap() {
		String mapData = TransferSerialization.serialize(StrongHold.getCurrentGame());
		ClientMain.send(new Packet(PacketType.SYNC_MAP, jwt, List.of(Integer.toString(mapData.length()),
			GameMenuController.getNextPlayer().getUser().getUserName())));
		try {
			ClientMain.getSockout().writeBytes(mapData);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("exception in sending map");
		}
	}

	public static void waitForGame() {
		Packet packet = ClientMain.receive();
		User[] users = new Gson().fromJson(packet.getDataList().get(0), User[].class);
		for (int i = 0; i < users.length; i++)
			StrongHold.getCurrentGame().getGovernments()[i].setUser(users[i]);
		System.out.println("game started");
		if (StrongHold.getMyPlayerIndex() == 0)
			GameMenu.getInstance().setControllable(true);
		else
			new Thread(SendRequests::waitForTurn).start();
	}

	public static void waitForTurn() {
		System.out.println("waiting for turn");
		Game game = receiveGameMap();
		StrongHold.setCurrentGame(game);
		GameMenuController.setGame(game);
		GameMenu.setGame(game);
		GameMenu.getInstance().setControllable(true);
	}

	// Chat
	public static void startChat() {
		new Thread(SendRequests::chatListener).start();
		ClientMain.send(new Packet(PacketType.START_CHAT, jwt));
	}

	public static void chatListener() {
		while (true) {
			ChatData chatData = receiveChatData();
			if (chatData == null) break;
			ChatMenuController.setChatData(chatData);
			Platform.runLater(() -> ChatMenu.getInstance().refreshScreen());
		}
	}

	private static ChatData receiveChatData() {
		Packet contentLength = ClientMain.receive();
		if (contentLength.getType() == PacketType.END_CHAT)
			return null;
		int length = Integer.parseInt(contentLength.getDataList().get(0));
		try {
			byte[] bytes = ClientMain.getSockin().readNBytes(length);
			String data = new String(bytes);
			ChatData chatData = new Gson().fromJson(data, ChatData.class);
			return chatData;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static void sendChatData(ChatData chatData) {
		String data = new Gson().toJson(chatData);
		ClientMain.send(new Packet(PacketType.UPDATE_CHAT_DATA, jwt,
			Integer.toString(data.length())));
		try {
			ClientMain.getSockout().writeBytes(data);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Exception in sending chat data");
		}
	}

	public static void endChat() {
		ClientMain.send(new Packet(PacketType.END_CHAT, jwt));
	}
}
