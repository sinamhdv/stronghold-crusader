package stronghold.controller;

import java.time.Instant;

import stronghold.controller.messages.LoginMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;

public class LoginMenuController {
	private static int failedLoginsCount = 0;
	private static long lastFailedAttemptTime = 0;

	public static LoginMenuMessage login(String username, String password, String stayLoggedIn) {
		if (username == null || password == null)
			return LoginMenuMessage.SPECIFY_REQUIRED_FIELDS;
		if (Instant.now().getEpochSecond() - lastFailedAttemptTime < 5 * failedLoginsCount)
			return LoginMenuMessage.TRY_AFTER_DELAY;
		User user = StrongHold.getUserByName(username);
		if (user == null)
			return LoginMenuMessage.USERNAME_NOT_FOUND;
		else if (!CentralController.checkPassword(username, password)) {
			failedLoginsCount++;
			lastFailedAttemptTime = Instant.now().getEpochSecond();
			return LoginMenuMessage.INCORRECT_PASSWORD;
		}
		StrongHold.setCurrentUser(user);
		failedLoginsCount = 0;
		lastFailedAttemptTime = 0;
		if (stayLoggedIn != null)
			DatabaseManager.saveStayLoggedIn(user);
		return LoginMenuMessage.LOGIN_SUCCESS;
	}

	public static LoginMenuMessage forgotPassword(String username) {
		if (StrongHold.getUserByName(username) == null) {
			System.out.println(LoginMenuMessage.USERNAME_NOT_FOUND);
		} else {
			User user = StrongHold.getUserByName(username);


		}
	}
}