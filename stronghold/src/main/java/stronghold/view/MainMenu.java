package stronghold.view;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.controller.MainMenuController;
import stronghold.controller.messages.MainMenuMessage;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MainMenu {
	private static final Scanner scanner = new Scanner(System.in);
	public static Scanner getScanner() {
		return scanner;
	}

	public static void run() {
		System.out.println("======[Main Menu]======");

		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(input, Command.PROFILE_MENU)) != null)
				ProfileMenu.run();
			else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MANAGEMENT_MENU)) != null)
				MapManagementMenu.run();
			else if ((matcher = CommandParser.getMatcher(input, Command.START_GAME)) != null)
				runStartGame(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.LOGOUT)) != null) {
				MainMenuController.logout();
				System.out.println("logged out");
				return;
			}
			else if ((matcher = CommandParser.getMatcher(input, Command.EXIT)) != null) {
				System.out.println("Exitting...");
				MainMenuController.exit();
			}
			else
				System.out.println("Invalid command");
		}
	}

	private static void runStartGame(HashMap<String, String> matcher) {
		MainMenuMessage message = MainMenuController.startGame(matcher.get("map"));
		System.out.println(message.getErrorString());
		if (message == MainMenuMessage.SUCCESS) {
			GameMenu.run();
		}
	}

	public static String[] askPlayersNames(int count) {
		String[] usernames = new String[count];
		System.out.println("The selected map needs " + count + " players");
		for (int i = 0; i < count; i++) {
			System.out.print("- Enter the username of player #" + i + ": ");
			usernames[i] = MainMenu.getScanner().nextLine();
		}
		return usernames;
	}
}
