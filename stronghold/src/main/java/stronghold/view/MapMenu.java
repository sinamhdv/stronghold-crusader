package stronghold.view;

import java.util.HashMap;

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

	
}
