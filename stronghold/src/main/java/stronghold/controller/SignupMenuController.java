package stronghold.controller;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.view.SignupMenu;

public class SignupMenuController {
	public SignupAndProfileMenuMessage signup(String username, String nickName, String password,
			String passwordConfirmation, String email, String slogan) {
		if (username == null || password == null || email == null || nickName == null || slogan.equals("")
				|| username.equals("") || password.equals("") || nickName.equals("") || email.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!CentralController.checkUserName(username))
			return SignupAndProfileMenuMessage.INVALID_USERNAME;
		else if (StrongHold.getUserByName(username) != null)
			return SignupAndProfileMenuMessage.USERNAME_EXIST;
		else if (CentralController.checkPasswordStrength(password) != SignupAndProfileMenuMessage.PASSWORD_IS_STRONG)
			return CentralController.checkPasswordStrength(password);
		else if (!password.equals(passwordConfirmation))
			return SignupAndProfileMenuMessage.PASSWORD_CONFIRMATION_IS_NOT_TRUE;
		else if (StrongHold.getUserByName(email.toLowerCase()) != null)
			return SignupAndProfileMenuMessage.EMAIL_EXIST;
		else if (!email.matches("[\\w\\.]+@[\\w\\.]+//.[\\w\\.]+"))
			return SignupAndProfileMenuMessage.INVALID_EMAIL;
		else {
			if (password.equals("random")) {
				String randomPassword = generateRandomPassword();
				if (SignupMenu.randomPasswordLoop(randomPassword)) {
					password = randomPassword;
				}
			}
			String[] securityQuestion = SignupMenu.securityQuestionLoop();
			User newUser = new User(username, password, nickName, slogan, email, 0,
					Integer.parseInt(securityQuestion[0]), securityQuestion[1]);
			StrongHold.getUsers().add(newUser);
			return SignupAndProfileMenuMessage.SIGNUP_SUCCESSFUL;
		}

	}

	private String generateRandomPassword() {
		String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				+ "0123456789"
				+ "abcdefghijklmnopqrstuvxyz";
		StringBuilder randompassword = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			int index = (int) (alphaNumericString.length() * Math.random());
			randompassword.append(alphaNumericString.charAt(index));
		}
		return randompassword.toString() + "@";
	}
}
