package stronghold.controller;

import java.util.Random;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.FormatValidation;
import stronghold.utils.Miscellaneous;
import stronghold.view.SignupMenu;
import stronghold.view.captcha.CaptchaLoop;

public class SignupMenuController {
	public static SignupAndProfileMenuMessage signup(String username, String nickName, String password,
			String email, String slogan) {
		SignupAndProfileMenuMessage message = signUpFactorsError(username, nickName, password, email, slogan);
		if (message != null)
			return message;
		String[] securityQuestion = SignupMenu.securityQuestionLoop();
		CaptchaLoop.captchaManager();
		User newUser = new User(username, password, nickName, slogan, email, 0,
				Integer.parseInt(securityQuestion[0]), securityQuestion[1]);
		StrongHold.addUser(newUser);
		return SignupAndProfileMenuMessage.SIGNUP_SUCCESSFUL;
	}

	public static SignupAndProfileMenuMessage signUpFactorsError(String username, String nickName, String password,
		String email, String slogan) {
		if ((slogan != null && slogan.equals("")) ||
			username.equals("") || password.equals("") ||
			nickName.equals("") || email.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!FormatValidation.checkUserName(username))
			return SignupAndProfileMenuMessage.INVALID_USERNAME;
		else if (StrongHold.getUserByName(username) != null)
			return SignupAndProfileMenuMessage.USERNAME_EXIST;
		else if (FormatValidation.checkPasswordStrength(password) != SignupAndProfileMenuMessage.PASSWORD_IS_STRONG)
			return FormatValidation.checkPasswordStrength(password);
		else if (StrongHold.getUserByEmail(email.toLowerCase()) != null)
			return SignupAndProfileMenuMessage.EMAIL_EXIST;
		else if (!FormatValidation.checkEmailFormat(email))
			return SignupAndProfileMenuMessage.INVALID_EMAIL;
		return null;
	}

	public static String generateRandomPassword() {
		String[] charsets = {
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ",
			"abcdefghijklmnopqrstuvwxyz",
			"0123456789",
			"&!@#$%^*_-+="
		};
		String password = "";
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 3; j++)
				password += charsets[i].charAt(Miscellaneous.RANDOM_GENERATOR.nextInt(charsets[i].length()));
		return password;
	}

	public static String generateRandomSlogan() {
		final String[] randomSlogans = { "benazam", "sag bargh nemikhone", "mekaniki dad bezan lastiketo bad bezan",
				"nahayt havafaza sakht moshak ba kaghaza", "randaton mikonm" };
		Random random = new Random();
		return randomSlogans[random.nextInt(randomSlogans.length)];
	}

	public static String suggestUsername(String repetitiousUserName) {
		for (int i = 0; ; i++) {
			String suggestUserName = repetitiousUserName + i;
			if (StrongHold.getUserByName(suggestUserName) == null) return suggestUserName; 
		}
	}
}
