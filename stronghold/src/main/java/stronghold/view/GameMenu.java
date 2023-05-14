package stronghold.view;

import java.util.ArrayList;
import java.util.HashMap;

import stronghold.controller.GameMenuController;
import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.people.Person;
import stronghold.model.people.PersonType;
import stronghold.utils.PopularityFormulas;
import stronghold.view.parser.Command;
import stronghold.view.parser.CommandParser;

public class GameMenu {
	private static Game game;

	private static void printMenuPrompt() {
		TerminalColor.setColor(TerminalColor.BLACK, TerminalColor.GREEN);
		System.out.print("game menu(" + game.getCurrentPlayerIndex() + ")> ");
		TerminalColor.resetColor();
	}

	public static void run() {
		game = StrongHold.getCurrentGame();
		GameMenuController.setGame(game);

		HashMap<String, String> matcher;
		while (true) {
			printMenuPrompt();
			String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());

			if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY)) != null)
				showPopularity();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY_FACTORS)) != null)
				showPopularityFactors();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FOOD_LIST)) != null)
				showFoodList();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FOOD_RATE)) != null)
				foodRateShow();
			else if ((matcher = CommandParser.getMatcher(input, Command.SET_FOOD_RATE)) != null)
				runSetFoodRate(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_TAX_RATE)) != null)
				taxRateShow();
			else if ((matcher = CommandParser.getMatcher(input, Command.SET_TAX_RATE)) != null)
				runSetTaxRate(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FEAR_RATE)) != null)
				fearRateShow();
			else if ((matcher = CommandParser.getMatcher(input, Command.SET_FEAR_RATE)) != null)
				runSetFearRate(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DROP_BUILDING)) != null)
				runDropBuilding(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.CREATE_UNIT)) != null)
				runCreateUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_BUILDING)) != null)
				runSelectBuilding(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_BUILDING)) != null)
				showSelectedBuilding();
			else if ((matcher = CommandParser.getMatcher(input, Command.OPEN_GATE)) != null)
				runOpenGate();
			else if ((matcher = CommandParser.getMatcher(input, Command.CLOSE_GATE)) != null)
				runCloseGate();
			else if ((matcher = CommandParser.getMatcher(input, Command.REPAIR)) != null)
				runRepair();
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_RESOURCES_AMOUNT)) != null)
				showResourcesAmount();
			else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_UNIT)) != null)
				runSelectUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_UNITS)) != null)
				showSelectedUnits();
			else if ((matcher = CommandParser.getMatcher(input, Command.DIG_TUNNEL)) != null)
				runDigTunnel(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.MOVE_UNIT)) != null)
				runMoveUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.PATROL_UNIT)) != null)
				runPatrolUnit(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.STOP_UNIT)) != null)
				runStopUnit();
			else if ((matcher = CommandParser.getMatcher(input, Command.ATTACK)) != null)
				runAttack(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.SET_STANCE)) != null)
				runSetStance(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.NEXT_TURN)) != null)
				if (runNextTurn()) return;
			else if ((matcher = CommandParser.getMatcher(input, Command.BUILD_SIEGE_EQUIPMENT)) != null)
				runBuildSiegeEquipment(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.DIG_MOAT)) != null)
				runDigMoat(matcher);
			else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MENU)) != null)
				MapMenu.run(game.getMap());
			else if ((matcher = CommandParser.getMatcher(input, Command.MARKET_MENU)) != null)
				MarketMenu.run();
			else if ((matcher = CommandParser.getMatcher(input, Command.TRADE_MENU)) != null)
				TradeMenu.run();
			else
				System.out.println("Error: Invalid command");
		}
	}

	private static void showPopularity() {
		System.out.println("Your popularity is: " + game.getCurrentPlayer().getPopularity());
	}

	private static void showPopularityFactors() {
		Government currentPlayer = game.getCurrentPlayer();
		System.out.println("Popularity factors:");
		System.out.println("Food influencing: " + currentPlayer.getFoodPopularityInfluence());
		System.out.println("Tax influencing: " + PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate()));
		System.out.println("Religion influencing: " + currentPlayer.getReligionPopularityInfluence());
		System.out.println("Fear influencing: " + currentPlayer.getFearFactor());
		int sumOfInfluencing = currentPlayer.getFoodPopularityInfluence() +
				PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate()) +
				currentPlayer.getReligionPopularityInfluence() +
				currentPlayer.getFearFactor();
		System.out.println("Sum of your influencing : " + sumOfInfluencing);
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

	private static void showFoodList() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		ResourceType[] foodTypes = ResourceType.foodTypes;
		for (int i = 0; i < foodTypes.length; i++) {
			System.out.println(
					"your " + foodTypes[i].getName() + " property: " + currentPlayer.getResourceCount(foodTypes[i]));
		}
	}

	private static void runSetFearRate(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.setFearRate(
				Integer.parseInt(matcher.get("rate"))).getErrorString());
	}

	private static void runSetFoodRate(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.setFoodRate(
				Integer.parseInt(matcher.get("rate"))).getErrorString());
	}

	private static void runSetTaxRate(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.setTaxRate(
				Integer.parseInt(matcher.get("rate"))).getErrorString());
	}

	private static void foodRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your food rate: " + currentPlayer.getFoodRate());
	}

	private static void taxRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your tax rate: " + currentPlayer.getTaxRate());
	}

	private static void fearRateShow() {
		Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
		System.out.println("your fear rate: " + currentPlayer.getFearFactor());
	}

	private static void runSelectBuilding(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.selectBuilding(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y"))).getErrorString());
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
			System.out
					.println(resourceType.getName() + " => " + game.getCurrentPlayer().getResourceCount(resourceType));
	}

	private static void runSelectUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.selectUnit(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y"))).getErrorString());
	}

	private static void runMoveUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.moveUnit(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y"))).getErrorString());
	}

	private static boolean runNextTurn() {
		GameMenuMessage message = GameMenuController.nextTurn();
		System.out.println(message.getErrorString());
		if (message == GameMenuMessage.END_GAME) return true;
		else if (message != GameMenuMessage.SUCCESS) return false;
		TerminalColor.setColor(TerminalColor.BLACK, TerminalColor.CYAN);
		System.out.print("======[Player #" + game.getCurrentPlayerIndex() + "]======");
		TerminalColor.resetColor();
		System.out.println();
		return false;
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
				Integer.parseInt(matcher.get("count"))).getErrorString());
	}

	private static void runPatrolUnit(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.patrolUnit(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y"))).getErrorString());
	}

	private static void runOpenGate() {
		System.out.println(GameMenuController.changeGateState(false).getErrorString());
	}

	private static void runCloseGate() {
		System.out.println(GameMenuController.changeGateState(true).getErrorString());
	}

	private static void runBuildSiegeEquipment(HashMap<String, String> matcher) {
		System.out.println();
	}

	private static void runSetStance(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.setStance(matcher.get("stanceType")).getErrorString());
	}

	private static void runAttack(HashMap<String, String> matcher) {
		System.out.println(GameMenuController.attack(
				Integer.parseInt(matcher.get("x")),
				Integer.parseInt(matcher.get("y"))
		).getErrorString());
	}

	private static void runStopUnit() {
		System.out.println(GameMenuController.stopUnit().getErrorString());
	}

	public static void showWinner(Government winner) {
		if (winner == null)
			System.out.println("The game didn't have a winner");
		else
			System.out.println("The winner is: " + winner.getUser().getUserName());
	}

	private static void runRepair() {
		System.out.println(GameMenuController.repair().getErrorString());
	}

	private static void runDigMoat(HashMap<String, String> matcher) {
		ArrayList<Person> peopleClone = game.getSelectedUnits();
		Government currentPlayer = game.getCurrentPlayer();
		for(Person  person: peopleClone){
			if(person.getOwner() == currentPlayer ){
				if(person.canDigMoats())
					System.out.println(person.digMoat(matcher.get("direction")));
				else
					System.out.println("This person can't dig moat");
			}
		}
	}

	private static void runDigTunnel(HashMap<String, String> matcher) {
		ArrayList<Person> peopleClone = game.getSelectedUnits();
		Government currentPlayer = game.getCurrentPlayer();
		for(Person  person: peopleClone){
			if(person.getOwner() == currentPlayer ){
				if(person.getType() == PersonType.TUNNELER)
					System.out.println(person.digTunnel());
				else
					System.out.println("This person is not tunneler");
			}
		}
	}
}
