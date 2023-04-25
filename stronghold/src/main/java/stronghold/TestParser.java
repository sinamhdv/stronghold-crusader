package stronghold;

import java.util.HashMap;
import java.util.Scanner;

import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class TestParser {
	public static void run() {
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
