package stronghold.view;

import java.util.HashMap;

import stronghold.controller.GameMenuController;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.StrongHold;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class GameMenu {
	private static Game game;

	public static void run() {
		System.out.println("======[Game Menu]======");

		game = StrongHold.getCurrentGame();
		GameMenuController.setGame(game);

		// TODO: find a way to force each player to build their keep and generate a Lord for each player

		HashMap<String, String> matcher;
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());

			if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY)) != null)
				showPopularity();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY_FACTORS)) != null)
				showPopularityFactors();
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_WALL)) != null)
				runDropWall(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_BUILDING)) != null)
				runDropBuilding(matcher);
			else
				System.out.println("Error: Invalid command");
		}
	}

	private static void showPopularity() {
		System.out.println("Your popularity is: " + game.getCurrentPlayer().getPopularity());
	}

	private static void showPopularityFactors() {
		System.out.println("Popularity factors:");
		System.out.println("Food rate: ");
	}

	private static void runDropWall(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.dropWall(
			Integer.parseInt(matcher.get("x1")),
			Integer.parseInt(matcher.get("y1")),
			Integer.parseInt(matcher.get("x2")),
			Integer.parseInt(matcher.get("y2"))
		).getErrorString());
	}

	private static void runDropBuilding(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.dropBuilding(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y")),
			matcher.get("type")
		).getErrorString());
	}

	public static void showMapEditorError(MapEditorMenuMessage message) {
		System.out.println(message.getErrorString());
	}
}
