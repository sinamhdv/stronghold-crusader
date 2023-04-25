package stronghold.view;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MainMenu {
	private static final Scanner scanner = new Scanner(System.in);
	public static Scanner getScanner() {
		return scanner;
	}

	public static void run() {
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(input, Command.PROFILE_MENU)) != null) {
				System.out.println("Entering profile menu...");
				ProfileMenu.run();
			}
			else if ((matcher = CommandParser.getMatcher(input, Command.LOGOUT)) != null) {
				System.out.println("logged out");
				return;
			}
			else {
				System.out.println("Invalid command");
			}
		}
	}
}
