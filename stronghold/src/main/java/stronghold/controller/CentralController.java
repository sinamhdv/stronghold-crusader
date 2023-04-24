package stronghold.controller;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.commons.codec.digest.DigestUtils;

import stronghold.controller.messages.SignupAndProfileMenuMessage;

public class CentralController {
	public static SignupAndProfileMenuMessage checkPasswordStrength(String passwoed) {
		if (passwoed.length() < 6)
			return SignupAndProfileMenuMessage.PASSWORD_IS_SHORT;
		else if (!passwoed.matches(".*[A-Z]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_UPPERCASE;
		else if (!passwoed.matches(".*[a-z]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNT_HAVE_LOWERCASE;
		else if (!passwoed.matches(".*[0-9]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSNET_HAVE_NUMBER;
		else if (!passwoed.matches(".*[& ! @ # $ % ^ * _ - + =]+.*"))
			return SignupAndProfileMenuMessage.PASSWORD_DOSENT_HAVE_OTHER_CHAR;
		else
			return SignupAndProfileMenuMessage.PASSWORD_IS_STRONG;
	}

	public static boolean checkUserName(String userName) {
		Matcher matcher = Pattern.compile("[A-Za-z_]+").matcher(userName);
		return matcher.matches();
	}

	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}
}