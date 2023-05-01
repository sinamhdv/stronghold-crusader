package stronghold.controller;

import org.apache.commons.codec.digest.DigestUtils;

import stronghold.model.StrongHold;
import stronghold.model.User;

public class CentralController {
	public static final String[] SECURITY_QUESTIONS = new String[] {
		"What is your father's name?",
		"What was your first pet's name?",
		"What is your mother's last name?",
	};

	

	public static String hashPassword(String password) {
		return DigestUtils.sha256Hex(password);
	}

	public static boolean checkPassword(String username, String password) {
		User user = StrongHold.getUserByName(username);
		return hashPassword(password).equals(user.getPassword());
	}
}