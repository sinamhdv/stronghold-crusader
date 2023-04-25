package stronghold.view;

import java.util.HashMap;

import stronghold.controller.SignupMenuController;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class SignupMenu {
	public static void run() {
		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null)
				return;
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP)) != null)
				System.out.println(SignupMenuController.signup(matcher.get("username"), matcher.get("nickname"),
						matcher.get("password"), matcher.get("passwordConfirm"), matcher.get("email"),
						matcher.get("slogan")).eror);
			else	
				System.out.println("invalid command");
		}
	}
	public static boolean randomPasswordLoop(String randomPassword) {
		String enterPassword = "";
		while (true) {
			System.out.println("Your random password is: " + randomPassword);
			System.out.println("Please re-enter your password here: ");
			enterPassword = MainMenu.getScanner().nextLine();
			if (enterPassword.equals(randomPassword))
				return true;
		}
	}
	public static String[] securityQuestionLoop() {
		while (true) {
			System.out.println("Pick your security question: 1. What is my father's name? " +
					"2. What was my first pet's name? " + "3. What is my mother's last name?");
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.QUESTION_PICK)) != null) {
				if (matcher.get("answerConfirm").equals(matcher.get("answer"))
						&& (Integer.parseInt(matcher.get("questionNumber")) == 1
								|| Integer.parseInt(matcher.get("questionNumber")) == 2
								|| Integer.parseInt(matcher.get("questionNumber")) == 3)) {
					String[] result = new String[3];
					result[0] = matcher.get("questionNumber");
					result[1] = matcher.get("answer");
					return result;
				}
			}
		}
	}
}
