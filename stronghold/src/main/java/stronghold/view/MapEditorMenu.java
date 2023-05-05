package stronghold.view;

import java.util.HashMap;

import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapEditorMenu {
	public static void run() {
		System.out.println("======[Map Editor]======");

		HashMap<String, String> matcher;
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
			if ((matcher = CommandParser.getMatcher(input, Command.BACK)) != null) {
				System.out.println("======[Map Management Menu]======");
				return;
			}
			else if ((matcher = CommandParser.getMatcher(input, Command.SET_TEXTURE)) != null)
				runSetTexture(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.RECTANGLE_SET_TEXTURE)) != null)
				runRectangleSetTexture(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.CLEAR)) != null)
				runClear(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_ROCK)) != null)
				runDropRock(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_TREE)) != null)
				runDropTree(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_UNIT)) != null)
				runDropUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_BUILDING)) != null)
				runDropBuilding(matcher);
			else
				System.out.println("Error: Invalid command");
		}
	}

	private static void runSetTexture(HashMap<String, String> matcher) {

	}

	private static void runRectangleSetTexture(HashMap<String, String> matcher) {

	}

	private static void runClear(HashMap<String, String> matcher) {

	}

	private static void runDropRock(HashMap<String, String> matcher) {

	}

	private static void runDropTree(HashMap<String, String> matcher) {

	}

	private static void runDropUnit(HashMap<String, String> matcher) {

	}

	private static void runDropBuilding(HashMap<String, String> matcher) {

	}
}
