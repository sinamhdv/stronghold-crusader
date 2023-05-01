package stronghold.view;

import java.util.HashMap;

import stronghold.controller.LoginMenuController;
import stronghold.controller.messages.LoginMenuMessage;
import stronghold.controller.messages.SignupAndProfileMenuMessage;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class LoginMenu {

	public static void run() {
		System.out.println("======[Login Menu]======");
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
				System.out.println("Exitting...");
				break;
			}
			else
				System.out.println("Error: Invalid command");
		}
	}

	public static void runLogin(HashMap<String, String> matcher) {
		LoginMenuMessage message = LoginMenuController.login(
			matcher.get("username"),
			matcher.get("password"),
			matcher.get("--stay-logged-in")
		);
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static void runForgotPassword(HashMap<String, String> matcher) {
		System.out.println(LoginMenuController.forgotPassword(
			matcher.get("username")
		).getErrorString());
	}

	public static void runCheckAutoLogin() {
		System.out.println("Checking for auto-login...");
		LoginMenuMessage message = LoginMenuController.checkAutoLogin();
		System.out.println(message.getErrorString());
		if (message == LoginMenuMessage.AUTO_LOGIN_SUCCESS)
			MainMenu.run();
	}

	public static String askSecurityQuestion(String question) {
		System.out.println("Please answer this security question:");
		System.out.println(question);
		return MainMenu.getScanner().nextLine();
	}

	public static String[] getNewPassword() {
		String[] password = new String[2];
		System.out.print("Enter your new password: ");
		password[0] = MainMenu.getScanner().nextLine();
		System.out.print("Enter the password again: ");
		password[1] = MainMenu.getScanner().nextLine();
		return password;
	}

	public static void alertWeakPassword(SignupAndProfileMenuMessage message) {
		System.out.println(message.getErrorString());
	}
}
