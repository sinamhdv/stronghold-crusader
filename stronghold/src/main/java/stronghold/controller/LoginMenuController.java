package stronghold.controller;

import java.time.Instant;

import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.FormatValidation;
import stronghold.view.LoginMenu;

public class LoginMenuController {
	private static int failedLoginsCount = 0;
	private static long lastFailedAttemptTime = 0;

	public static LoginMenuMessage login(String username, String password, String stayLoggedIn) {
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

	public static LoginMenuMessage checkAutoLogin() {
		String username = DatabaseManager.getAutoLoginUsername();
		if (username == null || username.equals(""))
			return LoginMenuMessage.AUTO_LOGIN_FAILED;
		User user = StrongHold.getUserByName(username);
		if (user == null)
			return LoginMenuMessage.AUTO_LOGIN_FAILED;
		StrongHold.setCurrentUser(user);
		return LoginMenuMessage.AUTO_LOGIN_SUCCESS;
	}

	public static LoginMenuMessage forgotPassword(String username) {
		User user = StrongHold.getUserByName(username);
		if (user == null)
			return LoginMenuMessage.USERNAME_DOESNT_EXIST;
		String securityQuestion = CentralController.SECURITY_QUESTIONS[user.getSecurityQuestionNumber() - 1];
		String answer = LoginMenu.askSecurityQuestion(securityQuestion);
		if (!answer.equals(user.getSecurityQuestionAnswer()))
			return LoginMenuMessage.INCORRECT_ANSWER;
		String[] newPassword = LoginMenu.getNewPassword();
		if (!newPassword[0].equals(newPassword[1]))
			return LoginMenuMessage.PASSWORD_CONFIRM_WRONG;
		SignupAndProfileMenuMessage passwordStrengthStatus = FormatValidation.checkPasswordStrength(newPassword[0]);
		if (passwordStrengthStatus != SignupAndProfileMenuMessage.PASSWORD_IS_STRONG) {
			LoginMenu.alertWeakPassword(passwordStrengthStatus);
			return LoginMenuMessage.PASSWORD_WEAK;
		}
		user.setPassword(newPassword[0]);
		return LoginMenuMessage.PASSWORD_RESET_SUCCESS;
	}
}
