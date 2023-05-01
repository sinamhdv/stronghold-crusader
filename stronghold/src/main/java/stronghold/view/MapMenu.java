package stronghold.view;

import java.util.HashMap;

import stronghold.controller.MapMenuController;
import stronghold.controller.messages.MapMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.map.MapTile;
import stronghold.utils.Miscellaneous;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapMenu {
	public static void run() {
		System.out.println("======[Map Menu]======");

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
		MapMenuMessage message = MapMenuController.checkShowMap(matcher.get("x"), matcher.get("y"));
		if (message == MapMenuMessage.SUCCESS)
			displayMap();
		else
			System.out.println(message.getErrorString());
	}

	public static void runMoveMap(HashMap<String, String> matcher) {
		MapMenuMessage message = MapMenuController.moveMap(matcher.get("up"), matcher.get("down"), matcher.get("left"), matcher.get("right"));
		if (message == MapMenuMessage.SUCCESS)
			displayMap();
		else
			System.out.println(message.getErrorString());
	}

	public static void runShowTileDetails(HashMap<String, String> matcher) {
		showTileDetails(matcher.get("x"), matcher.get("y"));
	}

	public static void displayMap() {
		int startX = MapMenuController.getCurrentX() - MapMenuController.SHOW_MAP_HEIGHT / 2;
		int startY = MapMenuController.getCurrentY() - MapMenuController.SHOW_MAP_WIDTH / 2;
		Terminal2DPrinter printer = new Terminal2DPrinter();
		for (int i = startX; i - startX < MapMenuController.SHOW_MAP_HEIGHT; i++) {
			printer.addOutput(new String[] {"-".repeat(MapMenuController.SHOW_MAP_WIDTH * 3 + 1)});
			printer.addNewLine();
			for (int j = startY; j - startY < MapMenuController.SHOW_MAP_WIDTH; j++) {
				String[] tileString = MapMenuController.getTileRepresentation(i, j);
				printer.addOutput(new String[] {"|", "|"});
				printer.addOutput(tileString);
			}
			printer.addOutput(new String[] {"|", "|"});
			printer.addNewLine();
		}
		printer.addOutput(new String[] {"-".repeat(MapMenuController.SHOW_MAP_WIDTH * 3 + 1)});
		printer.printOutput();
	}

	private static void showTileDetails(String xString, String yString) {
		MapMenuMessage errorCheck = MapMenuController.checkCoordinateErrors(xString, yString);
		if (errorCheck != MapMenuMessage.SUCCESS) {
			System.out.println(errorCheck.getErrorString());
			return;
		}
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		MapTile tile = StrongHold.getCurrentGame().getMap()[x][y];
		System.out.println("Ground Type: " + tile.getGroundType().getName());
		if (tile.getBuilding() != null)
			System.out.println("Building: " + tile.getBuilding().getName());
		HashMap<String, Integer> peopleCount = Miscellaneous.countPeopleFromArray(tile.getPeople());
		System.out.println("List of People:");
		for (String personName : peopleCount.keySet())
			System.out.println(personName + ": " + peopleCount.get(personName));
	}
}
