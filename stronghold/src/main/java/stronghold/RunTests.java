package stronghold;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.controller.DatabaseManager;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.view.Terminal2DPrinter;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class RunTests {
	public static void run() {
		
	}

	public static void testTerminal2DPrinter() {
		Terminal2DPrinter printer = new Terminal2DPrinter();
		int width = 10;
		int height = 5;
		for (int i = 0; i < height; i++) {
			printer.addOutput(new String[] {"-".repeat(3 * width + 1)});
			printer.addNewLine();
			printer.addOutput(new String[] {"|", "|"});
			for (int j = 0; j < width; j++) {
				printer.addOutput(new String[] {"##", "##"});
				printer.addOutput(new String[] {"|", "|"});
			}
			printer.addNewLine();
		}
		printer.addOutput(new String[] {"-".repeat(3 * width + 1)});
		printer.printOutput();
	}

	public static void testDB() {
		System.out.println(StrongHold.getUsers());
		User user = new User("sina", "pwd", "\\mmd\"", "haha", "abcd@exmaple.com", 10, 1, "hello");
		StrongHold.addUser(user);
		DatabaseManager.updateUser(user);
		DatabaseManager.loadUsers();
		user = StrongHold.getUsers().get(0);
		System.out.println(user.getEmail());
		System.out.println(user.getNickName());
	}

	public static void testParser() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String line = scanner.nextLine();
			String[] inputTokens = CommandParser.splitTokens(line);
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(inputTokens, Command.SIGNUP)) != null) {
				System.out.println(matcher.get("username"));
				System.out.println(matcher.get("password"));
				System.out.println(matcher.get("passwordConfirm"));
				System.out.println(matcher.get("email"));
				System.out.println(matcher.get("nickname"));
				System.out.println(matcher.get("slogan"));
			}
			else if ((matcher = CommandParser.getMatcher(inputTokens, Command.EXIT)) != null) {
				System.out.println("Bye!");
				break;
			}
			else {
				System.out.println("Error: Invalid command");
			}
		}
		scanner.close();
	}
}
