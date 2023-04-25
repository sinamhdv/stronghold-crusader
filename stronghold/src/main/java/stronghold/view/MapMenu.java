package stronghold.view;

import java.util.HashMap;

import stronghold.controller.MapMenuController;
import stronghold.model.StrongHold;
import stronghold.model.map.MapTile;
import stronghold.utils.FormatValidation;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapMenu {
	public static void run() {
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());
			HashMap<String, String> matcher;

			if ((matcher = CommandParser.getMatcher(input, Command.SHOW_MAP)) != null)
				runShowMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_TILE_DETAILS)) != null)
				runShowTileDetails(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.MOVE_MAP)) != null)
				runMoveMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.BACK)) != null)
				return;
			else
				System.out.println("invalid command");
		}
	}

	public static void runShowMap(HashMap<String, String> matcher) {
		String xString = matcher.get("x");
		String yString = matcher.get("y");
		if (xString == null || yString == null) {
			System.out.println("Error: please specify both x and y");
			return;
		}
		if (!FormatValidation.isNumber(xString) || !FormatValidation.isNumber(yString)) {
			System.out.println("Error: invalid numeric argument entered.");
			return;
		}
		showMap(Integer.parseInt(xString), Integer.parseInt(yString));
	}

	public static void runMoveMap(HashMap<String, String> matcher) {
		for (String key : matcher.keySet()) {
			if (!FormatValidation.isNumber(matcher.get(key))) {
				System.out.println("Error: invalid numeric argument entered.");
				return;
			}
		}
		MapMenuController.moveMap(matcher.get("up"), matcher.get("down"), matcher.get("left"), matcher.get("right"));
	}

	public static void runShowTileDetails(HashMap<String, String> matcher) {
		// TODO
	}

	public static void showMap(int x, int y) {
		MapTile[][] map = StrongHold.getCurrentGame().getMap();
		// TODO
	}
}
