package stronghold.controller;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.DatabaseManager;
import stronghold.utils.FormatValidation;
import stronghold.view.ProfileMenu;
import stronghold.view.captcha.CaptchaLoop;

public class ProfileMenuController {
	public static SignupAndProfileMenuMessage changeUserName(String newUsername) {
		if (newUsername.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!FormatValidation.checkUserName(newUsername))
			return SignupAndProfileMenuMessage.INVALID_USERNAME;
		if (StrongHold.getUserByName(newUsername) != null)
			return SignupAndProfileMenuMessage.USERNAME_EXIST;
		StrongHold.getCurrentUser().setUserName(newUsername);
		String autoLoginUsername = DatabaseManager.getAutoLoginUsername();
		if (autoLoginUsername != null && !autoLoginUsername.equals(""))
			DatabaseManager.saveStayLoggedIn(StrongHold.getCurrentUser());
		return SignupAndProfileMenuMessage.SUCCESS;
	}

	public static SignupAndProfileMenuMessage changeNickName(String newNickName) {
		if (newNickName.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else {
			StrongHold.getCurrentUser().setNickName(newNickName);
			return SignupAndProfileMenuMessage.SUCCESS;
		}
	}

	public static SignupAndProfileMenuMessage changePassword(String newPassword, String oldPassword) {
		if (newPassword.equals("") || oldPassword.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!CentralController.checkPassword(StrongHold.getCurrentUser().getUserName(), oldPassword))
			return SignupAndProfileMenuMessage.OLD_PASSWORD_WRONG;
		else if (oldPassword.equals(newPassword))
			return SignupAndProfileMenuMessage.OLD_AND_NEW_PASSWORD_ARE_EQUAL;
		else if (!FormatValidation.checkPasswordStrength(newPassword)
				.equals(SignupAndProfileMenuMessage.PASSWORD_IS_STRONG))
			return FormatValidation.checkPasswordStrength(newPassword);

		CaptchaLoop.captchaManager();

		String confirmation = ProfileMenu.askNewPasswordConfirmation();
		if (!confirmation.equals(newPassword))
			return SignupAndProfileMenuMessage.PASSWORD_IS_SHORT;

		StrongHold.getCurrentUser().setPassword(newPassword);
		return SignupAndProfileMenuMessage.SUCCESS;
	}

	public static SignupAndProfileMenuMessage changeEmail(String newEmail) {
		if (newEmail.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!FormatValidation.checkEmailFormat(newEmail))
			return SignupAndProfileMenuMessage.INVALID_EMAIL;
		User previousUser = StrongHold.getUserByEmail(newEmail);
		if (previousUser != null && previousUser != StrongHold.getCurrentUser())
			return SignupAndProfileMenuMessage.EMAIL_EXIST;
		StrongHold.getCurrentUser().setEmail(newEmail);
		return SignupAndProfileMenuMessage.SUCCESS;
	}

	public static SignupAndProfileMenuMessage changeSlogan(String slogan) {
		if (slogan != null && slogan.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		StrongHold.getCurrentUser().setSlogan(slogan);
		return SignupAndProfileMenuMessage.SUCCESS;
	}

	public static void changeAvatar(int indexOfOvatar) {
		StrongHold.getCurrentUser().setIndexOfOvatar(indexOfOvatar);
	}

	
}
