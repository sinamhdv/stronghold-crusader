package stronghold.view;

import java.util.HashMap;

import stronghold.controller.MapEditorMenuController;
import stronghold.controller.MapManagementMenuController;
import stronghold.model.StrongHold;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapEditorMenu {
	public static void run() {
		System.out.println("======[Map Editor]======");

		StrongHold.setCurrentGame(null);	// signal other functions that we're in editor mode
		MapEditorMenuController.setMap(MapManagementMenuController.getLoadedMap());
		MapEditorMenuController.setSelectedGovernment(0);

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
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_WALL)) != null)
				runDropWall(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_GOVERNMENT)) != null)
				runSelectGovernment(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_GOVERNMENT)) != null)
				showSelectedGovernment();
			else if ((matcher = CommandParser.getMatcher(input, Command.BACK)) != null)
				return;
			else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MENU)) != null) {
				MapMenu.run(MapEditorMenuController.getMap());
				System.out.println("======[Map Editor]======");
			}
			else
				System.out.println("Error: Invalid command");
		}
	}

	private static void runSetTexture(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.setTexture(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("type")
		).getErrorString());
	}

	private static void runRectangleSetTexture(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.rectangleSetTexture(
			Integer.parseInt(matcher.get("x1")),
			Integer.parseInt(matcher.get("y1")),
			Integer.parseInt(matcher.get("x2")),
			Integer.parseInt(matcher.get("y2")),
			matcher.get("type")
		).getErrorString());
	}

	private static void runClear(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.clear(
			Integer.parseInt(matcher.get("x1")),
			Integer.parseInt(matcher.get("y1")),
			Integer.parseInt(matcher.get("x2")),
			Integer.parseInt(matcher.get("y2"))
		).getErrorString());
	}

	private static void runDropRock(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.dropRock(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("direction")
		).getErrorString());
	}

	private static void runDropTree(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.dropTree(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("type")
		).getErrorString());
	}

	private static void runDropUnit(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.dropUnit(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("type"),
			Integer.parseInt(matcher.get("count"))
		).getErrorString());
	}

	private static void runDropBuilding(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.dropBuilding(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("type")
		).getErrorString());
	}

	private static void runSelectGovernment(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.selectGovernment(
			Integer.parseInt(matcher.get("government"))
		).getErrorString());
	}

	private static void showSelectedGovernment() {
		System.out.println("The selected government is: " + MapEditorMenuController.getSelectedGovernment());
	}

	private static void runDropWall(HashMap<String, String> matcher) {
		System.out.println(MapEditorMenuController.dropWall(
			Integer.parseInt(matcher.get("x1")),
			Integer.parseInt(matcher.get("y1")),
			Integer.parseInt(matcher.get("x2")),
			Integer.parseInt(matcher.get("y2"))
		).getErrorString());
	}
}
