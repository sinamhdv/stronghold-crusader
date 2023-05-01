package stronghold.controller;

import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.utils.Cryptography;

public class CentralController {
	public static final String[] SECURITY_QUESTIONS = new String[] {
		"What is your father's name?",
		"What was your first pet's name?",
		"What is your mother's last name?",
	};

	public static boolean checkPassword(String username, String password) {
		User user = StrongHold.getUserByName(username);
		return Cryptography.hashPassword(password).equals(user.getPassword());
	}
}
