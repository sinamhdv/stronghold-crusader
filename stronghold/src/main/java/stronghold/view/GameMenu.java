package stronghold.view;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import stronghold.controller.GameMenuController;
import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.people.Person;
import stronghold.utils.PopularityFormulas;
import stronghold.utils.ViewUtils;

public class GameMenu extends Application {
	private static Game game;
	private static GameMenu instance;

	@FXML
	private GridPane grid;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private BorderPane borderPane;
	@FXML
	private ToolBar toolBar;
	@FXML
	private ScrollPane mainPane;
	@FXML
	private ImageView minimap;

	public GameMenu() {
		game = StrongHold.getCurrentGame();
		GameMenuController.setGame(game);
		instance = this;
	}

	public static GameMenu getInstance() {
		return instance;
	}

	ScrollPane getScrollPane() {
		return scrollPane;
	}
	BorderPane getBorderPane() {
		return borderPane;
	}
	GridPane getGrid() {
		return grid;
	}

	@Override
	public void start(Stage stage) throws Exception {
		borderPane = FXMLLoader.load(GameMenu.class.getResource("/fxml/GameMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {
		scrollPane.setPannable(true);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(MapScreen.GRID_GAPS);
		grid.setVgap(MapScreen.GRID_GAPS);
		grid.setGridLinesVisible(true);
		displayFullMap();
		addKeyListeners();
		setupToolBar();
		scrollPane.requestFocus();
	}

	private void displayFullMap() {
		for (int i = 0; i < game.getMap().getHeight(); i++) {
			for (int j = 0; j < game.getMap().getWidth(); j++) {
				grid.add(MapScreen.getTileRepresentation(i, j), j, i);
			}
		}
	}

	private void addKeyListeners() {
		scrollPane.setOnKeyPressed(event -> {
			switch (event.getCode()) {
				case EQUALS:
					MapScreen.zoomHandler(1.02);
					break;
				case MINUS:
					MapScreen.zoomHandler(1/1.02);
					break;
				default:
					break;
			}
		});
	}

	private void setupToolBar() {
		toolBar.setPrefSize(ViewUtils.getScreenWidth(), ViewUtils.getScreenHeight() / 5.0);
		toolBar.setBackground(new Background(new BackgroundImage(
			new Image(GameMenu.class.getResource("/images/ui/toolbar.png").toExternalForm()),
			BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.CENTER,
			new BackgroundSize(toolBar.getPrefWidth(), toolBar.getPrefHeight(),
			false, false, false, false))));
		mainPane.setPrefSize(toolBar.getPrefWidth() * 0.7, toolBar.getPrefHeight() * 0.8);
		mainPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		mainPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		minimap.setImage(GameToolBar.getMinimapImage(game.getMap()));
		minimap.setFitHeight(mainPane.getPrefHeight());
		minimap.setFitWidth(minimap.getFitHeight());
		GameToolBar.setMinimapMouseHandler(minimap, scrollPane);
	}

	// public Group getGridCell(int x, int y) {
	// 	return (Group) grid.getChildren().get(x * game.getMap().getWidth() + y);
	// }

	// public static void run() {
	// 	game = StrongHold.getCurrentGame();
	// 	GameMenuController.setGame(game);

	// 	HashMap<String, String> matcher;
	// 	while (true) {
	// 		printMenuPrompt();
	// 		String[] input = CommandParser.splitTokens(MainMenu.getScanner().nextLine());

	// 		if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY)) != null)
	// 			showPopularity();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_POPULARITY_FACTORS)) != null)
	// 			showPopularityFactors();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FOOD_LIST)) != null)
	// 			showFoodList();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FOOD_RATE)) != null)
	// 			foodRateShow();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SET_FOOD_RATE)) != null)
	// 			runSetFoodRate(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_TAX_RATE)) != null)
	// 			taxRateShow();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SET_TAX_RATE)) != null)
	// 			runSetTaxRate(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_FEAR_RATE)) != null)
	// 			fearRateShow();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SET_FEAR_RATE)) != null)
	// 			runSetFearRate(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.DROP_BUILDING)) != null)
	// 			runDropBuilding(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.CREATE_UNIT)) != null)
	// 			runCreateUnit(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_BUILDING)) != null)
	// 			runSelectBuilding(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_BUILDING)) != null)
	// 			showSelectedBuilding();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.OPEN_GATE)) != null)
	// 			runOpenGate();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.CLOSE_GATE)) != null)
	// 			runCloseGate();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.REPAIR)) != null)
	// 			runRepair();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_RESOURCES_AMOUNT)) != null)
	// 			showResourcesAmount();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SELECT_UNIT)) != null)
	// 			runSelectUnit(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SHOW_SELECTED_UNITS)) != null)
	// 			showSelectedUnits();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.DIG_TUNNEL)) != null)
	// 			runDigTunnel();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.MOVE_UNIT)) != null)
	// 			runMoveUnit(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.PATROL_UNIT)) != null)
	// 			runPatrolUnit(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.STOP_UNIT)) != null)
	// 			runStopUnit();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.ATTACK)) != null)
	// 			runAttack(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.SET_STANCE)) != null)
	// 			runSetStance(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.DISBAND)) != null)
	// 			runDisband();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.NEXT_TURN)) != null) {
	// 			if (runNextTurn()) return;
	// 		} else if ((matcher = CommandParser.getMatcher(input, Command.BUILD_SIEGE_EQUIPMENT)) != null)
	// 			runBuildSiegeEquipment(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.CHEAT_GOLD)) != null)
	// 			runCheatGold(matcher);
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.DEBUG_MODE)) != null)
	// 			runToggleDebugMode();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.MAP_MENU)) != null)
	// 			MapMenu.run(game.getMap());
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.MARKET_MENU)) != null)
	// 			MarketMenu.run();
	// 		else if ((matcher = CommandParser.getMatcher(input, Command.TRADE_MENU)) != null)
	// 			TradeMenu.run();
	// 		else
	// 			System.out.println("Error: Invalid command");
	// 	}
	// }

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
		System.out.println(GameMenuController.buildSiegeEquipment(
			matcher.get("equipment")
		).getErrorString());
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

	private static void runDisband() {
		System.out.println(GameMenuController.disbandUnit().getErrorString());
	}

	private static void runDigTunnel() {
		System.out.println(GameMenuController.digTunnel().getErrorString());
	}

	private static void runCheatGold(HashMap<String, String> matcher) {
		game.getCurrentPlayer().setGold(Integer.parseInt(matcher.get("gold")));
	}

	private static void runToggleDebugMode() {
		GameMenuController.setDebugMode(!GameMenuController.getDebugMode());
		System.out.println("DEBUG MODE: " + GameMenuController.getDebugMode());
	}
}
