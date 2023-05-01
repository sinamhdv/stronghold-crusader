package stronghold.controller;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.FormatValidation;

public class ProfileMenuController {
	public static SignupAndProfileMenuMessage changeUserName(String newUsername) {
		if (newUsername.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!FormatValidation.checkUserName(newUsername))
			return SignupAndProfileMenuMessage.INVALID_USERNAME;
		User previousUser = StrongHold.getUserByName(newUsername);
		if (previousUser != null && StrongHold.getCurrentUser() != previousUser)
			return SignupAndProfileMenuMessage.USERNAME_EXIST;
		StrongHold.getCurrentUser().setUserName(newUsername);
		return SignupAndProfileMenuMessage.CHANGE_USERNAME_SUCCESSFUL;
	}

	public static SignupAndProfileMenuMessage changeNickName(String newNickName) {
		if (newNickName.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else {
			StrongHold.getCurrentUser().setNickName(newNickName);
			return SignupAndProfileMenuMessage.CHANGE_NICKNAME_SUCCESSFUL;
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

		// TODO: call Captcha
		// TODO: ask the user to enter the new password again

		StrongHold.getCurrentUser().setPassword(newPassword);
		return SignupAndProfileMenuMessage.CHANGE_PASSWORD_SUCCESS;
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
		return SignupAndProfileMenuMessage.CHANGE_EMAIL_SUCCESSFUL;
	}

	public static SignupAndProfileMenuMessage changeSlogan(String slogan) {
		if (slogan.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		StrongHold.getCurrentUser().setSlogan(slogan);
		return SignupAndProfileMenuMessage.CHANGE_SLOGAN_SUCCESSFUL;
	}

	public static SignupAndProfileMenuMessage removeSlogan() {
		StrongHold.getCurrentUser().setSlogan(null);
		return SignupAndProfileMenuMessage.REMOVE_SLOGAN_SUCCESSFUL;
	}
}
