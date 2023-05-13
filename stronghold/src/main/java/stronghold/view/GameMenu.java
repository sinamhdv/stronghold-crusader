package stronghold.view;

import java.util.HashMap;

import stronghold.controller.GameMenuController;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.people.Person;
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
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_BUILDING)) != null)
				runDropBuilding(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.CREATE_UNIT)) != null)
				runCreateUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_BUILDING)) != null)
				runSelectBuilding(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_BUILDING)) != null)
				showSelectedBuilding();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_RESOURCES_AMOUNT)) != null)
				showResourcesAmount();
			else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_UNIT)) != null)
				runSelectUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_UNITS)) != null)
				showSelectedUnits();
			else if ((matcher = CommandParser.getMatcher(input, Command.MOVE_UNIT)) != null)
				runMoveUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.PATROL_UNIT)) != null)
				runPatrolUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.NEXT_TURN)) != null)
				runNextTurn();
			else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MENU)) != null) {
				MapMenu.run(game.getMap());
				System.out.println("======[Game Menu]======");
			}
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
				+ currentPlayer.getFearFactor();
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

	private static void runSelectBuilding(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.selectBuilding(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		).getErrorString());
	}

	private static void showSelectedBuilding() {
		if (game.getSelectedBuilding() == null) {
			System.out.println("No building is selected");
			return;
		}
		System.out.println(game.getSelectedBuilding());
	}

	private static void showResourcesAmount() {
		System.out.println("Resources report:");
		for (ResourceType resourceType : ResourceType.values())
			System.out.println(resourceType.getName() + " => " + game.getCurrentPlayer().getResourceCount(resourceType));
	}

	private static void runSelectUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.selectUnit(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		).getErrorString());
	}

	private static void runMoveUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.moveUnit(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		).getErrorString());
	}

	private static void runNextTurn() {
		System.out.println(GameMenuController.nextTurn().getErrorString());
	}

	private static void showSelectedUnits() {
		if (game.getSelectedUnits().isEmpty()) {
			System.out.println("No unit is selected");
			return;
		}
		for (Person person : game.getSelectedUnits())
			System.out.println(person);
	}

	private static void runCreateUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.createUnit(
			matcher.get("type"),
			Integer.parseInt(matcher.get("count"))
		).getErrorString());
	}

	private static void runPatrolUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.patrolUnit(
			Integer.parseInt(matcher.get("x")),
			Integer.parseInt(matcher.get("y"))
		).getErrorString());
	}
}
