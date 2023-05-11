package stronghold.view;

import java.util.HashMap;

import stronghold.controller.GameMenuController;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class GameMenu {
	private static Game game;

	public static void run() {
		System.out.println("======[Game Menu]======");

		game = StrongHold.getCurrentGame();
		GameMenuController.setGame(game);

		// TODO: find a way to force each player to build their keep and generate a Lord
		// for each player

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
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		int sumOfInfluencing = GameMenuController.getPopularityInfluencingFood(currentPlayer.getFoodRate())
							 + GameMenuController.getTaxPopularityInfluencing(currentPlayer.getTaxRate()) 
							 +  currentPlayer.getFearFactor();
		System.out.println("Popularity factors:");
		System.out.println(
				"Food influencing : " + GameMenuController.getPopularityInfluencingFood(currentPlayer.getFoodRate()));
		System.out.println(
				"Tax influencing : " + GameMenuController.getTaxPopularityInfluencing(currentPlayer.getTaxRate()));
		System.out.println("Fear influencing : " + currentPlayer.getFearFactor());
		System.out.println("Religion influencing : ");
		// TODO: get religion influencing after handel church
		System.out.println("Sum of your influencing : " + sumOfInfluencing);
	}

	private static void runDropWall(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.dropWall(
				Integer.parseInt(matcher.get("x1")),
				Integer.parseInt(matcher.get("y1")),
				Integer.parseInt(matcher.get("x2")),
				Integer.parseInt(matcher.get("y2"))).getErrorString());
	}

	private static void runDropBuilding(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.dropBuilding(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y")),
				matcher.get("type")).getErrorString());
	}

	public static void showMapEditorError(MapEditorMenuMessage message) {
		System.out.println(message.getErrorString());
	}

	public static void showFoodList() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		ResourceType[] food = new ResourceType[] { ResourceType.APPLE, ResourceType.CHEESE, ResourceType.MEAT,
				ResourceType.BREAD };
		for (int i = 0; i < 4; i++) {
			System.out.println("your " + food[i].getName() + " property : " + currentPlayer.getResourceCount(food[i]));
		}
	}

	public static void foodRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your food rate : " + currentPlayer.getFoodRate());
	}

	public static void taxRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your tax rate : " + currentPlayer.getTaxRate());
	}

	public static void fearRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your fear rate : " + currentPlayer.getFearFactor());
	}
}
