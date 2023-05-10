package stronghold.view;

import java.util.HashMap;

import stronghold.controller.GameMenuController;
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

		HashMap<String, String> matcher;
		while (true) {
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());

			if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY)) != null)
				showPopularity();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY_FACTORS)) != null)
				showPopularityFactors();
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
}
