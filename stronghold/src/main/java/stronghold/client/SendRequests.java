package stronghold.client;

import java.util.List;

import com.google.gson.Gson;

import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.User;
import stronghold.network.Packet;
import stronghold.network.PacketType;

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
}
