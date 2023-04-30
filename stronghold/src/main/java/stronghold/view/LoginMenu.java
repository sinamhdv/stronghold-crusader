package stronghold.view;

import java.util.HashMap;

import stronghold.controller.LoginMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class LoginMenu {

	public static void run() {
		System.out.println("[LoginMenu]");
		runCheckAutoLogin();

		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(inputTokens, Command.LOGIN)) != null)
				runLogin(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.FORGOT_PASSWORD)) != null)
				runForgotPassword(matcher);
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP_MENU)) != null)
				SignupMenu.run();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
				System.out.println("EXIT!");
				break;
			}
			else
				System.out.println("Error: Invalid command");
		}
	}

	public static void runLogin(HashMap<String, String> matcher) {
		LoginMenuMessage message = LoginMenuController.login(matcher.get("username"),
				matcher.get("password"),
				matcher.get("--stay-logged-in"));
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static void runForgotPassword(HashMap<String, String> matcher) {
		System.out.println(LoginMenuController.forgotPassword(matcher.get("username")).getErrorString());
	}

	public static void runCheckAutoLogin() {
		System.out.println("Checking for auto-login...");
		LoginMenuMessage message = LoginMenuController.checkAutoLogin();
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.AUTO_LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static String askSecurityQuestion(String question) {
		// TODO: ask the security question and return the entered answer
	}

	public static String[] getNewPassword() {
		// TODO: return new password and its confirmation
	}
}
