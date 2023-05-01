package stronghold.view;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.controller.MainMenuController;
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
			else if ((matcher = CommandParser.getMatcher(input, Command.MAP_DESIGN_MENU)) != null)
				MapDesignMenu.run();
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
}
