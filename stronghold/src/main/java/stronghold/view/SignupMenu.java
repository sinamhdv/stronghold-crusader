package stronghold.view;

import java.util.HashMap;

import stronghold.controller.CentralController;
import stronghold.controller.SignupMenuController;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class SignupMenu {
	public static void run() {
		System.out.println("======[Signup Menu]======");
		
		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null)
				return;
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP)) != null)
				System.out.println(SignupMenuController.signup(matcher.get("username"), matcher.get("nickname"),
				matcher.get("password"), matcher.get("passwordConfirm"), matcher.get("email"),
				matcher.get("slogan")).getErrorString());
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
			else
				System.out.println("Incorrect password, try again :(");
		}
	}
	public static String[] securityQuestionLoop() {
		while (true) {
			System.out.println("Pick your security question:");
			for (int i = 0; i < CentralController.SECURITY_QUESTIONS.length; i++)
				System.out.println((i + 1) + ". " + CentralController.SECURITY_QUESTIONS[i]);
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.QUESTION_PICK)) != null) {
				if (matcher.get("answer").equals("") || matcher.get("answerConfirm").equals(""))
					System.out.println("Error: Empty field");
				else if (!matcher.get("answerConfirm").equals(matcher.get("answer")))
					System.out.println("Error: answer confirm must match answer");
				else if (Integer.parseInt(matcher.get("questionNumber")) < 1 ||
					Integer.parseInt(matcher.get("questionNumber")) > CentralController.SECURITY_QUESTIONS.length)
					System.out.println("Error: Incorrect question number");
				else {
					String[] result = new String[2];
					result[0] = matcher.get("questionNumber");
					result[1] = matcher.get("answer");
					return result;
				}
			}
			else {
				System.out.println("Invalid command: please pick a security question");
			}
		}
	}
	public static void showRandomSlogan(String randomSlogan) {
		System.out.println("you're random slogan is : "+randomSlogan);
	}

	public static boolean continueWithRandomUsername(String newUsername) {
		System.out.println("The entered username already exists");
		System.out.println("Do you want to continue with this suggested username?: " + newUsername);
		System.out.println("Enter your choice (y/N): ");
		String answer = MainMenu.getScanner().nextLine().toLowerCase();
		return answer.equals("y");
	}
}
