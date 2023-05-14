package stronghold.utils;

import stronghold.controller.messages.SignupAndProfileMenuMessage;

public class FormatValidation {
	public static boolean isNumber(String string) {
		return string.length() < 10 && string.matches("\\-?\\d+");
	}

	public static SignupAndProfileMenuMessage checkPasswordStrength(String password) {
		if (password.length() < 6)
			return SignupAndProfileMenuMessage.PASSWORD_IS_SHORT;
		else if (!password.matches(".*[A-Z]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_UPPERCASE;
		else if (!password.matches(".*[a-z]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_LOWERCASE;
		else if (!password.matches(".*[0-9]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNET_HAVE_NUMBER;
		else if (!password.matches(".*[\\&\\!\\@\\#\\$\\%\\^\\*\\_\\-\\+\\=]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSENT_HAVE_OTHER_CHAR;
		else
			return SignupAndProfileMenuMessage.PASSWORD_IS_STRONG;
	}

	public static boolean checkUserName(String userName) {
		return userName.matches("\\w+");
	}

	public static boolean checkEmailFormat(String email) {
		return email.matches("[\\w\\.]+@[\\w\\.]+\\.[\\w\\.]+");
	}

	public static boolean checkMapNameFormat(String mapName) {
		return mapName.matches("\\w+");
	}
}
