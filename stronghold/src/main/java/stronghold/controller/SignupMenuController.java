package stronghold.controller;

import java.util.Random;

import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.FormatValidation;
import stronghold.view.SignupMenu;

public class SignupMenuController {
	public static SignupAndProfileMenuMessage signup(String username, String nickName, String password,
			String passwordConfirmation, String email, String slogan) {
		SignupAndProfileMenuMessage errors = SignUpFactorsError(username, nickName, password, passwordConfirmation, email, slogan);
		if(errors != null)
			return errors;
		else {
			if (slogan.equals("random")) {
				String randomSlogan = generateRandomSlogan();
				slogan = randomSlogan;
				SignupMenu.showRandomSlogan(randomSlogan);
			}
			if (password.equals("random")) {
				String randomPassword = generateRandomPassword();
				if (SignupMenu.randomPasswordLoop(randomPassword)) {
					password = randomPassword;
				}
			}
			String[] securityQuestion = SignupMenu.securityQuestionLoop();
			User newUser = new User(username, password, nickName, slogan, email, 0,
					Integer.parseInt(securityQuestion[0]), securityQuestion[1]);
			StrongHold.addUser(newUser);
			return SignupAndProfileMenuMessage.SIGNUP_SUCCESSFUL;
		}
		// TODO: suggestion of random usernames
		// TODO: what if the password is not "random" and passwordConfirmation == null?
	}

	public static SignupAndProfileMenuMessage SignUpFactorsError (String username, String nickName, String password,
	String passwordConfirmation, String email, String slogan) {
		if ((slogan != null && slogan.equals("")) ||
				(passwordConfirmation != null && passwordConfirmation.equals("")) ||
				username.equals("") || password.equals("") ||
				nickName.equals("") || email.equals(""))
			return SignupAndProfileMenuMessage.EMPTY_FIELD;
		else if (!FormatValidation.checkUserName(username))
			return SignupAndProfileMenuMessage.INVALID_USERNAME;
		else if (StrongHold.getUserByName(username) != null)
			return SignupAndProfileMenuMessage.USERNAME_EXIST;
		else if (FormatValidation.checkPasswordStrength(password) != SignupAndProfileMenuMessage.PASSWORD_IS_STRONG)
			return FormatValidation.checkPasswordStrength(password);
		else if (!password.equals("random") && !password.equals(passwordConfirmation))
			return SignupAndProfileMenuMessage.PASSWORD_CONFIRMATION_IS_NOT_TRUE;
		else if (password.equals("random") && passwordConfirmation != null)
			return SignupAndProfileMenuMessage.RANDOM_PASSWORD_DESNT_HAVE_PASSWORDCONFIRMATION;
		else if (StrongHold.getUserByEmail(email.toLowerCase()) != null)
			return SignupAndProfileMenuMessage.EMAIL_EXIST;
		else if (!email.matches("[\\w\\.]+@[\\w\\.]+//.[\\w\\.]+"))
			return SignupAndProfileMenuMessage.INVALID_EMAIL;
		else return null;
	}
	private static String generateRandomPassword() {
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

	private static String generateRandomSlogan() {
		final String[] randomSlogans = { "benazam", "sag bargh nemikhone", "mekaniki dad bezan lastiketo bad bezan",
				"nahayt havafaza sakht moshak ba kaghaza", "randaton mikonm" };
		Random random = new Random();
		return randomSlogans[random.nextInt(randomSlogans.length)];
	}
	private static String suggestUserName (String RepetitiousUserName) {
		Random random = new Random();
		int upperBound = 10000;
		// TODO : add constant upperbound to jason
		while (true) {
			String suggestUserName = RepetitiousUserName + random.nextInt(upperBound);
			if (StrongHold.getUserByName(suggestUserName) != null) return suggestUserName; 
		}
		System.out.println("you can use this username");
	}
}
