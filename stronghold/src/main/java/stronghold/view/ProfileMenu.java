package stronghold.view;

import java.util.HashMap;

import stronghold.controller.ProfileMenuController;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class ProfileMenu {
	public static void run() {
		System.out.println("======[Profile Menu]======");
		
		while (true) {
			String line = MainMenu.getScanner().nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;
			if ((matcher = CommandParser.getMatcher(inputTokens, Command.BACK)) != null)
				return;
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.CHANGE_USERNAME)) != null)
				System.out.println(ProfileMenuController.changeUserName(matcher.get("username")).getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.CHANGE_NICKNAME)) != null)
				System.out.println(ProfileMenuController.changeNickName(matcher.get("nickname")).getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.CHANGE_PASSWORD)) != null)
				System.out.println(
						ProfileMenuController.changePassword(matcher.get("newPassword"), matcher.get("oldPassword")).getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.CHANGE_EMAIL)) != null)
				System.out.println(ProfileMenuController.changeEmail(matcher.get("email")).getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.CHANGE_SLOGAN)) != null)
				System.out.println(ProfileMenuController.changeSlogan(matcher.get("slogan")).getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.REMOVE_SLOGAN)) != null)
				System.out.println(ProfileMenuController.removeSlogan().getErrorString());
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.DISPLAY_HIGHSCORE)) != null)
				displayHighscore();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.DISPLAY_RANK)) != null)
				displayRank();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.DISPLAY_SLOGAN)) != null)
				displaySlogan();
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.PROFILE_DISPLAY)) != null)
				displayInfo();
			else
				System.out.println("Error: Invalid command");
		}
	}

	private static void displayHighscore() {
		System.out.println("you're highscore is: " + StrongHold.getCurrentUser().getHighScore());
	}

	private static void displayRank() {
		System.out.println("you're rank is: " + StrongHold.getRank(StrongHold.getCurrentUser()));
	}

	private static void displaySlogan() {
		if (StrongHold.getCurrentUser().getSlogan() == null)
			System.out.println("you dont have any slogans");
		else
			System.out.println("you're slogan is: " + StrongHold.getCurrentUser().getSlogan());
	}

	private static void displayInfo() {
		User user = StrongHold.getCurrentUser();
		System.out.println("Username: " + user.getUserName());
		System.out.println("Nickname: " + user.getNickName());
		System.out.println("Email: " + user.getEmail());
		System.out.println("Highscore: " + user.getHighScore());
		System.out.println("Rank: " + StrongHold.getRank(user));
		System.out.println((user.getSlogan() == null ? "No slogan" : "Slogan: " + user.getSlogan()));
	}

	public static String askNewPasswordConfirmation() {
		System.out.print("Please enter the new password again: ");
		return MainMenu.getScanner().nextLine();
	}
}
