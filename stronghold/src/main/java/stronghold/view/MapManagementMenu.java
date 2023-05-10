package stronghold.view;

import java.util.HashMap;

import stronghold.controller.MapManagementMenuController;
import stronghold.controller.messages.MapManagementMenuMessage;
import stronghold.utils.DatabaseManager;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class MapManagementMenu {
	public static void run() {
		System.out.println("======[Map Management Menu]======");

		HashMap<String, String> matcher;
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());

			if ((matcher = CommandParser.getMatcher(input, Command.NEW_MAP)) != null)
				runNewMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.EDIT_MAP)) != null)
				runEditMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DELETE_MAP)) != null)
				runDeleteMap(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.LIST_MAPS)) != null)
				listMaps();
			else if ((matcher = CommandParser.getMatcher(input, Command.BACK)) != null) {
				System.out.println("======[Main Menu]======");
				return;
			}
			else
				System.out.println("Invalid command");
		}
	}

	private static void runNewMap(HashMap<String, String> matcher) {
		System.out.println(MapManagementMenuController.createNewMap(
			matcher.get("name"),
			Integer.parseInt(matcher.get("governmentsCount")),
			Integer.parseInt(matcher.get("width")),
			Integer.parseInt(matcher.get("height"))
		).getErrorString());
	}

	private static void runEditMap(HashMap<String, String> matcher) {
		MapManagementMenuMessage message = MapManagementMenuController.editMap(
			matcher.get("name")
		);
		System.out.println(message.getErrorString());
		if (message == MapManagementMenuMessage.LOAD_SUCCESS) {
			MapEditorMenu.run();
		}
	}

	private static void runDeleteMap(HashMap<String, String> matcher) {
		System.out.println(MapManagementMenuController.deleteMap(
			matcher.get("name")
		).getErrorString());
	}

	private static void listMaps() {
		String[] names = DatabaseManager.getAllMapNames();
		for (String name : names)
			System.out.println(name);
	}
}
