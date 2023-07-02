package stronghold.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import stronghold.client.SendRequests;
import stronghold.controller.GameMenuController;
import stronghold.controller.messages.GameMenuMessage;
import stronghold.model.Game;
import stronghold.model.Government;
import stronghold.model.ResourceType;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.Stockpile;
import stronghold.model.people.Person;
import stronghold.utils.AssetImageLoader;
import stronghold.utils.FormatValidation;
import stronghold.utils.Miscellaneous;
import stronghold.utils.PopularityFormulas;
import stronghold.utils.ViewUtils;

public class GameMenu extends Application {
	private static Game game;
	private static GameMenu instance;

	public static final Image[] popularityFaceEmojies = new Image[] {
		new Image(GameMenu.class.getResource("/images/ui/sad.png").toExternalForm()),
		new Image(GameMenu.class.getResource("/images/ui/poker.png").toExternalForm()),
		new Image(GameMenu.class.getResource("/images/ui/smile.png").toExternalForm()),
		new Image(GameMenu.class.getResource("/images/ui/sad-red.png").toExternalForm()),
		new Image(GameMenu.class.getResource("/images/ui/smile-green.png").toExternalForm())
	};

	@FXML
	private GridPane grid;
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private BorderPane borderPane;
	@FXML
	private Pane toolBar;
	@FXML
	private ScrollPane mainScrollPane;
	@FXML
	private StackPane mainPane;
	@FXML
	private ImageView minimap;
	@FXML
	private ImageView popularityFace;
	@FXML
	private Label popularityLabel;
	@FXML
	private Label goldLabel;
	@FXML
	private Label populationLabel;
	@FXML
	private Label attackLabel;
	@FXML
	private HBox buildingCategoryButtonsBox;
	@FXML
	private Label errorText;
	@FXML
	private CheckBox gateOpenCheckBox;
	@FXML
	private TextField unitXInput;
	@FXML
	private TextField unitYInput;
	@FXML
	private ComboBox<String> unitStanceCombo;
	@FXML
	private Button startTurnButton;

	// Main Pane Boxes
	@FXML
	private HBox governmentReportBox;
	@FXML
	private HBox repairBox;
	@FXML
	private HBox stockpileReportBox;
	@FXML
	private HBox unitCreationBox;
	@FXML
	private HBox unitCommandsBox;
	@FXML
	private HBox clipboardBox;
	private HBox[] buildingCategoryBoxes;

	private final Clipboard clipboard = Clipboard.getSystemClipboard();
	private final ClipboardContent clipboardContent = new ClipboardContent();

	private Rectangle[] aboveAll;

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
	StackPane getMainPane() {
		return mainPane;
	}

	BooleanProperty isControllable = new SimpleBooleanProperty(false);
	public void setControllable(boolean value) {
		isControllable.set(value);
	}
	public boolean isControllable() {
		return isControllable.get();
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
		clipboard.setContent(clipboardContent);
		scrollPane.setPannable(true);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setVbarPolicy(ScrollBarPolicy.NEVER);
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(MapScreen.GRID_GAPS);
		grid.setVgap(MapScreen.GRID_GAPS);
		displayFullMap();
		setupToolBar();
		addKeyListeners();
		setupControlBlockers();
		borderPane.requestFocus();
	}

	private void setupControlBlockers() {
		aboveAll = new Rectangle[3];
		aboveAll[0] = new Rectangle(0, 0,
			ViewUtils.getScreenWidth() - 110, ViewUtils.getScreenHeight());
		aboveAll[1] = new Rectangle(aboveAll[0].getWidth(), 0,
			110, ViewUtils.getScreenHeight() - 80);
		aboveAll[2] = new Rectangle(aboveAll[0].getWidth(), aboveAll[1].getHeight(),
			110, 80);
		for (Rectangle rectangle : aboveAll) {
			rectangle.setManaged(false);
			rectangle.setFill(Color.GREEN);
			rectangle.setOpacity(0.2);
			borderPane.getChildren().add(rectangle);
			rectangle.toFront();
		}
		startTurnButton.setVisible(false);
		isControllable.addListener((observable, oldValue, newValue) -> {
			aboveAll[2].setMouseTransparent(newValue.booleanValue());
			aboveAll[2].setOpacity(newValue.booleanValue() ? 0 : 0.2);
			startTurnButton.setVisible(newValue.booleanValue());
			if (!newValue.booleanValue()) {
				for (int i = 0; i < 2; i++) {
					aboveAll[i].setMouseTransparent(false);
					aboveAll[i].setOpacity(0.2);
				}
			}
		});
	}

	public void startTurnButtonHandler(MouseEvent event) {
		for (Rectangle rectangle : aboveAll) {
			rectangle.setMouseTransparent(true);
			rectangle.setOpacity(0);
		}
		startTurnButton.setVisible(false);
		MapScreen.refreshAll();
		updateToolBarReport();
	}

	private Scene savedScene;
	public void showSavedScene() {
		Stage stage = LoginMenu.getStage();
		stage.setScene(savedScene);
		stage.setFullScreen(true);
		stage.show();
		borderPane.requestFocus();
	}

	public void displayFullMap() {
		for (int i = 0; i < game.getMap().getHeight(); i++) {
			for (int j = 0; j < game.getMap().getWidth(); j++) {
				grid.add(MapScreen.getTileRepresentation(i, j), j, i);
			}
		}
	}

	private void addKeyListeners() {
		borderPane.setOnKeyPressed(event -> {
			if (!isControllable()) return;
			switch (event.getCode()) {
				case EQUALS:
					MapScreen.zoomHandler(1.02);
					break;
				case MINUS:
					MapScreen.zoomHandler(1/1.02);
					break;
				case D:
					runDisband(null);
					break;
				case S:
					runStopUnit(null);
					break;
				case C:
					runCheatGold();
					break;
				case A:
					runAttack(null);
					break;
				case P:
					runPatrolUnit(null);
					break;
				case M:
					runMoveUnit(null);
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
		mainScrollPane.setPrefSize(toolBar.getPrefWidth() * 0.7, toolBar.getPrefHeight() * 0.8);
		mainScrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		mainScrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		minimap.setImage(GameToolBar.getMinimapImage(game.getMap()));
		minimap.setFitHeight(mainScrollPane.getPrefHeight());
		minimap.setFitWidth(minimap.getFitHeight());
		GameToolBar.setMinimapMouseHandler(minimap, scrollPane);
		updateToolBarReport();
		setupBuildingCategories();
		setupUnitCommandBox();
		GameToolBar.clearMainPane();
	}

	private void setupUnitCommandBox() {
		unitStanceCombo.getItems().addAll("STANDING", "OFFENSIVE", "DEFENSIVE");
		unitStanceCombo.getSelectionModel().select(0);
		unitStanceCombo.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			if (unitStanceCombo.getSelectionModel().getSelectedItem() == null) return;
			runSetStance(unitStanceCombo.getSelectionModel().getSelectedItem());
		});
	}

	public void reportButtonHandler(MouseEvent event) {
		refreshGovernmentReport();
		MapScreen.clearAreaSelection();
		GameToolBar.clearMainPane();
		governmentReportBox.setVisible(true);
		governmentReportBox.setManaged(true);
	}

	public void updateToolBarReport() {
		Government player = game.getCurrentPlayer();
		popularityLabel.setText("Popularity(" + game.getCurrentPlayerIndex() + "): " + player.getPopularity());
		goldLabel.setText("Gold: " + player.getGold());
		populationLabel.setText("Population: " +
			player.getPopulation() + "/" + player.getMaxPopulation());
		if (player.getPopularity() < 30) popularityFace.setImage(popularityFaceEmojies[0]);
		else if (player.getPopularity() < 50) popularityFace.setImage(popularityFaceEmojies[1]);
		else popularityFace.setImage(popularityFaceEmojies[2]);
	}

	private void refreshInfoBox(VBox infoBox) {
		Government currentPlayer = game.getCurrentPlayer();
		infoBox.getChildren().clear();
		infoBox.getChildren().add(GameToolBar.getPopularityFactorLine(
			"Food influence: ", currentPlayer.getFoodPopularityInfluence()));
		infoBox.getChildren().add(GameToolBar.getPopularityFactorLine(
			"Tax influence: ", PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate())));
		infoBox.getChildren().add(GameToolBar.getPopularityFactorLine(
			"Religion influence: ", currentPlayer.getReligionPopularityInfluence()));
		infoBox.getChildren().add(GameToolBar.getPopularityFactorLine(
			"Fear influence: ", currentPlayer.getFearFactor()));
		int sumOfInfluencing = currentPlayer.getFoodPopularityInfluence() +
				PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate()) +
				currentPlayer.getReligionPopularityInfluence() +
				currentPlayer.getFearFactor();
		infoBox.getChildren().add(GameToolBar.getPopularityFactorLine(
			"Total: ", sumOfInfluencing));
	}

	public void refreshGovernmentReport() {
		Government currentPlayer = game.getCurrentPlayer();
		governmentReportBox.getChildren().clear();
		VBox infoBox = new VBox(10);
		infoBox.setPadding(new Insets(20));
		refreshInfoBox(infoBox);
		governmentReportBox.getChildren().add(infoBox);
		HBox optionsBox = new HBox(20);
		optionsBox.setPadding(new Insets(20));
		Slider foodSlider = new Slider(-2, 2, currentPlayer.getFoodRate());
		foodSlider.setShowTickLabels(true);
		foodSlider.setShowTickMarks(true);
		foodSlider.setMinorTickCount(0);
		foodSlider.setMajorTickUnit(1);
		foodSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int value = (int)(newValue.doubleValue() + 0.5 * (newValue.doubleValue() > 0 ? 1 : -1));
			if (value != currentPlayer.getFoodRate()) {
				currentPlayer.setFoodRate(value);
				refreshInfoBox(infoBox);
			}
		});
		optionsBox.getChildren().add(new VBox(10, new Label("Food rate:"), foodSlider));
		Slider taxSlider = new Slider(-3, 8, currentPlayer.getTaxRate());
		taxSlider.setShowTickLabels(true);
		taxSlider.setShowTickMarks(true);
		taxSlider.setMinorTickCount(0);
		taxSlider.setMajorTickUnit(1);
		taxSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int value = (int)(newValue.doubleValue() + 0.5 * (newValue.doubleValue() > 0 ? 1 : -1));
			if (value != currentPlayer.getTaxRate()) {
				currentPlayer.setTaxRate(value);
				refreshInfoBox(infoBox);
			}
		});
		optionsBox.getChildren().add(new VBox(10, new Label("Tax rate:"), taxSlider));
		Slider fearSlider = new Slider(-5, 5, currentPlayer.getFearFactor());
		fearSlider.setShowTickLabels(true);
		fearSlider.setShowTickMarks(true);
		fearSlider.setMinorTickCount(0);
		fearSlider.setMajorTickUnit(1);
		fearSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int value = (int)(newValue.doubleValue() + 0.5 * (newValue.doubleValue() > 0 ? 1 : -1));
			if (value != currentPlayer.getFearFactor()) {
				currentPlayer.setFearFactor(value);
				refreshInfoBox(infoBox);
			}
		});
		optionsBox.getChildren().add(new VBox(10, new Label("Fear factor:"), fearSlider));
		governmentReportBox.getChildren().add(optionsBox);
	}

	private void setupBuildingCategories() {
		int index = 0;
		for (Node node : buildingCategoryButtonsBox.getChildren()) {	// XXX: no button children except category buttons
			if (!(node instanceof Button)) continue;
			int tempIndex = index;
			((Button)node).setOnMouseClicked(event -> { loadBuildingsCategory(tempIndex); });
			index++;
		}
		buildingCategoryBoxes = new HBox[buildingCategoryButtonsBox.getChildren().size()];
		for (int i = 0; i < buildingCategoryBoxes.length; i++) {
			buildingCategoryBoxes[i] = new HBox(20);
			buildingCategoryBoxes[i].setVisible(false);
			buildingCategoryBoxes[i].setManaged(false);
			buildingCategoryBoxes[i].setAlignment(Pos.CENTER_LEFT);
			mainPane.getChildren().add(buildingCategoryBoxes[i]);
		}
		fillCategoryList(0, new String[] {"keep", "stockpile", "low wall", "stairs",
			"horizontal small gate", "horizontal large gate", "small turret", "large turret",
			"lookout tower", "square tower", "round tower", "killing pit", "draw bridge"});
		fillCategoryList(1, new String[] {"european barracks", "arabian barracks",
			"armory", "engineers guild", "stable"});
		fillCategoryList(2, new String[] {"apple farm", "dairy farm", "wheat farm",
			"grape farm", "hunter", "woodcutter", "iron mine", "stone mine", "ox tether"});
		fillCategoryList(3, new String[] {"armorer", "bow fletcher", "crossbow fletcher",
			"mace blacksmith", "sword blacksmith", "pike poleturner", "spear poleturner", "tanner"});
		fillCategoryList(4, new String[] {"bakery", "brewery", "granary", "inn", "market",
			"house", "chapel", "church", "mill"});
	}

	private void loadBuildingsCategory(int index) {
		MapScreen.clearAreaSelection();
		GameToolBar.clearMainPane();
		buildingCategoryBoxes[index].setVisible(true);
		buildingCategoryBoxes[index].setManaged(true);
	}

	private void fillCategoryList(int index, String[] names) {
		for (String name : names) {
			ImageView image = new ImageView(AssetImageLoader.getAssetImage(name));
			image.setFitWidth(MapScreen.CELL_DIMENTIONS);
			image.setFitHeight(MapScreen.CELL_DIMENTIONS);
			image.setOnDragDetected(event -> {
				image.startFullDrag();
				GameMenuController.setDraggedBuildingName(name);
			});
			Tooltip tooltip = new Tooltip(name);
			Tooltip.install(image, tooltip);
			buildingCategoryBoxes[index].getChildren().add(image);
		}
	}

	private PauseTransition errorTextEraseDelay;
	public void showErrorText(String error) {
		if (errorTextEraseDelay != null) errorTextEraseDelay.stop();
		errorText.setText(error);
		if (error.equals(GameMenuMessage.SUCCESS.getErrorString()))
			errorText.setTextFill(Color.GREEN);
		else
			errorText.setTextFill(Color.RED);
		errorTextEraseDelay = new PauseTransition(Duration.seconds(2));
		errorTextEraseDelay.setOnFinished(event -> { errorText.setText(""); });
		errorTextEraseDelay.play();
	}

	public void runSelectBuilding(int x, int y) {
		MapScreen.clearAreaSelection();
		GameToolBar.clearMainPane();
		GameMenuMessage message = GameMenuController.selectBuilding(x, y);
		showErrorText(message.getErrorString());
		if (message != GameMenuMessage.SUCCESS) return;
		if (game.getSelectedBuilding() == null) return;
		else if (game.getSelectedBuilding().getName().equals("market")) {
			try {
				savedScene = LoginMenu.getStage().getScene();
				new MarketMenu().start(LoginMenu.getStage());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		else if (game.getSelectedBuilding() instanceof DefensiveStructure)
			showRepairBox();
		else if (game.getSelectedBuilding() instanceof Stockpile)
			showStockpileReport();
		else if (game.getSelectedBuilding() instanceof Barracks)
			showUnitCreationPanel();
	}

	private void showRepairBox() {
		repairBox.setVisible(true);
		repairBox.setManaged(true);
		if (((DefensiveStructure)game.getSelectedBuilding()).getType() == DefensiveStructureType.GATE) {
			gateOpenCheckBox.setVisible(true);
			gateOpenCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
				if (newValue.booleanValue()) runOpenGate();
				else runCloseGate();
			});
		}
		else
			gateOpenCheckBox.setVisible(false);
	}

	private void showStockpileReport() {
		stockpileReportBox.setVisible(true);
		stockpileReportBox.setManaged(true);
		stockpileReportBox.getChildren().clear();
		Stockpile stockpile = (Stockpile) game.getSelectedBuilding();
		for (ResourceType resourceType : stockpile.getResources().keySet()) {
			ImageView image = new ImageView(resourceType.getImage());
			image.setFitWidth(50);
			image.setFitHeight(50);
			VBox vBox = new VBox(10, image,
				new Label(stockpile.getResources().get(resourceType) + "/" +
				game.getCurrentPlayer().getResourceCount(resourceType)));
			vBox.setAlignment(Pos.CENTER);
			stockpileReportBox.getChildren().add(vBox);
		}
	}

	private void showUnitCreationPanel() {
		unitCreationBox.setVisible(true);
		unitCreationBox.setManaged(true);
		unitCreationBox.getChildren().clear();
		Barracks barracks = (Barracks) game.getSelectedBuilding();
		for (String name : barracks.getTroopNames()) {
			ImageView image = new ImageView(AssetImageLoader.getAssetImage(name));
			image.setFitWidth(100);
			image.setFitHeight(140);
			unitCreationBox.getChildren().add(image);
			image.setOnMouseClicked(event -> { runCreateUnit(name); });
		}
	}

	void showUnitCommandsBox() {
		unitCommandsBox.setVisible(true);
		unitCommandsBox.setManaged(true);
		HBox unitsList = (HBox) unitCommandsBox.getChildren().get(0);
		unitsList.getChildren().clear();
		HashMap<String, Integer> unitsCount = Miscellaneous.countPeopleFromArray(game.getSelectedUnits());
		for (String name : unitsCount.keySet()) {
			ImageView image = new ImageView(AssetImageLoader.getAssetImage(name));
			image.setFitWidth(60);
			image.setFitHeight(80);
			Label countLabel = new Label(Integer.toString(unitsCount.get(name)));
			Button minusButton = new Button("-");
			minusButton.setOnMouseClicked(event -> {
				GameMenuMessage message = GameMenuController.deleteSingleSelectedUnit(name);
				if (message != GameMenuMessage.SUCCESS) {
					showErrorText(message.getErrorString());
					return;
				}
				countLabel.setText(Integer.toString(Integer.parseInt(countLabel.getText()) - 1));
			});
			unitsList.getChildren().add(new VBox(5,
				image, new HBox(5, minusButton, countLabel)));
		}
		unitStanceCombo.getSelectionModel().clearSelection();
	}

	public void showClipboard(MouseEvent mouseEvent) {
		GameToolBar.clearMainPane();
		MapScreen.clearAreaSelection();
		clipboardBox.setVisible(true);
		clipboardBox.setManaged(true);
		clipboardBox.getChildren().clear();
		if (!clipboardContent.hasString()) return;
		TextField pasteXInput = new TextField();
		TextField pasteYInput = new TextField();
		pasteXInput.setPromptText("paste X");
		pasteYInput.setPromptText("paste Y");
		HBox imagesBox = new HBox(10);
		String[] names = clipboardContent.getString().split("/");
		for (String name : names) {
			ImageView image = new ImageView(AssetImageLoader.getAssetImage(name));
			image.setFitHeight(60);
			image.setFitWidth(60);
			image.setOnMouseClicked(event -> {
				if (!checkInputXY(pasteXInput, pasteYInput)) {
					showErrorText("Error: Invalid coordinates");
					return;
				}
				runDropBuilding(Integer.parseInt(pasteXInput.getText()),
					Integer.parseInt(pasteYInput.getText()), name);
			});
			imagesBox.getChildren().add(image);
		}
		clipboardBox.getChildren().add(new VBox(10,
			new HBox(10, pasteXInput, pasteYInput), imagesBox));
	}

	public void copyBuilding(MouseEvent event) {
		if (game.getSelectedBuilding() == null) {
			showErrorText("No selected building");
			return;
		}
		String previousContent = (clipboardContent.hasString() ? clipboardContent.getString() : "");
		clipboardContent.putString(previousContent + "/" + game.getSelectedBuilding().getName());
		showErrorText(GameMenuMessage.SUCCESS.getErrorString());
	}

	void runDropBuilding(int x, int y, String name) {
		GameMenuMessage message = GameMenuController.dropBuilding(x, y, name);
		if (message != GameMenuMessage.CONSTRUCTION_FAILED)
			showErrorText(message.getErrorString());
		if (message == GameMenuMessage.SUCCESS)
			MapScreen.refreshMapCell(x, y);
	}

	public void runRepair() {
		showErrorText(GameMenuController.repair().getErrorString());
	}

	private void runOpenGate() {
		GameMenuMessage message = GameMenuController.changeGateState(false);
		showErrorText(message.getErrorString());
		if (message == GameMenuMessage.SUCCESS)
			MapScreen.refreshMapCell(game.getSelectedBuilding().getX(), game.getSelectedBuilding().getY());
	}

	private void runCloseGate() {
		GameMenuMessage message = GameMenuController.changeGateState(true);
		showErrorText(message.getErrorString());
		if (message == GameMenuMessage.SUCCESS)
			MapScreen.refreshMapCell(game.getSelectedBuilding().getX(), game.getSelectedBuilding().getY());
	}

	private void runCreateUnit(String type) {
		showErrorText(GameMenuController.createUnit(type, 1).getErrorString());
		int[] keep = game.getCurrentPlayer().findKeep();
		MapScreen.refreshMapCell(keep[0], keep[1]);
	}

	public void runNextTurn() throws Exception {
		GameToolBar.clearMainPane();
		MapScreen.clearAreaSelection();
		GameMenuMessage message = GameMenuController.nextTurn();
		showErrorText(message.getErrorString());
		if (message == GameMenuMessage.END_GAME) {
			endGame();
			return;
		}
		else if (message != GameMenuMessage.SUCCESS) return;
		// set the map view to the next players keep
		// int[] keep = game.getCurrentPlayer().findKeep();
		// scrollPane.setVvalue(keep[0] / (double)game.getMap().getHeight());
		// scrollPane.setHvalue(keep[1] / (double)game.getMap().getWidth());
		updateToolBarReport();

		setControllable(false);
		SendRequests.sendGameMap();
		new Thread(SendRequests::waitForTurn).start();
	}

	private void endGame() throws Exception {
		System.out.println("Game Has Ended!!!");
		new MainMenu().start(LoginMenu.getStage());
	}

	private void runCheatGold() {
		game.getCurrentPlayer().setGold(game.getCurrentPlayer().getGold() + 1000);
	}

	public void runDisband(MouseEvent event) {
		ArrayList<Person> backupSelected = new ArrayList<>(game.getSelectedUnits());
		showErrorText(GameMenuController.disbandUnit().getErrorString());
		MapScreen.clearAreaSelection();
		GameToolBar.clearMainPane();
		for (Person person : backupSelected)
			MapScreen.refreshMapCell(person.getX(), person.getY());
	}

	public void runStopUnit(MouseEvent event) {
		showErrorText(GameMenuController.stopUnit().getErrorString());
	}

	private boolean checkInputXY(TextField inputX, TextField inputY) {
		return (FormatValidation.isNumber(inputX.getText()) && FormatValidation.isNumber(inputY.getText()));
	}

	public void runMoveUnit(MouseEvent event) {
		if (!checkInputXY(unitXInput, unitYInput)) {
			showErrorText("Error: Invalid coordinates");
			return;
		}
		showErrorText(GameMenuController.moveUnit(
				Integer.parseInt(unitXInput.getText()),
				Integer.parseInt(unitYInput.getText())).getErrorString());
	}

	public void runPatrolUnit(MouseEvent event) {
		if (!checkInputXY(unitXInput, unitYInput)) {
			showErrorText("Error: Invalid coordinates");
			return;
		}
		showErrorText(GameMenuController.patrolUnit(
				Integer.parseInt(unitXInput.getText()),
				Integer.parseInt(unitYInput.getText())).getErrorString());
	}


	public void runAttack(MouseEvent event) {
		if (!checkInputXY(unitXInput, unitYInput)) {
			showErrorText("Error: Invalid coordinates");
			return;
		}
		showErrorText(GameMenuController.attack(
				Integer.parseInt(unitXInput.getText()),
				Integer.parseInt(unitYInput.getText())).getErrorString());
	}

	private void runSetStance(String name) {
		showErrorText(GameMenuController.setStance(name).getErrorString());
	}

	public void showAttackBanner(boolean status) {
		attackLabel.setVisible(status);
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

	// private static void showPopularity() {
	// 	System.out.println("Your popularity is: " + game.getCurrentPlayer().getPopularity());
	// }

	// private static void showPopularityFactors() {
	// 	Government currentPlayer = game.getCurrentPlayer();
	// 	System.out.println("Popularity factors:");
	// 	System.out.println("Food influencing: " + currentPlayer.getFoodPopularityInfluence());
	// 	System.out.println("Tax influencing: " + PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate()));
	// 	System.out.println("Religion influencing: " + currentPlayer.getReligionPopularityInfluence());
	// 	System.out.println("Fear influencing: " + currentPlayer.getFearFactor());
	// 	int sumOfInfluencing = currentPlayer.getFoodPopularityInfluence() +
	// 			PopularityFormulas.taxRate2Popularity(currentPlayer.getTaxRate()) +
	// 			currentPlayer.getReligionPopularityInfluence() +
	// 			currentPlayer.getFearFactor();
	// 	System.out.println("Sum of your influencing : " + sumOfInfluencing);
	// }

	// private static void runDropBuilding(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.dropBuilding(
	// 			Integer.parseInt(matcher.get("x")),
	// 			Integer.parseInt(matcher.get("y")),
	// 			matcher.get("type")).getErrorString());
	// }

	// public static void showMapEditorError(MapEditorMenuMessage message) {
	// 	System.out.println(message.getErrorString());
	// }

	// private static void showFoodList() {
	// 	Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
	// 	ResourceType[] foodTypes = ResourceType.foodTypes;
	// 	for (int i = 0; i < foodTypes.length; i++) {
	// 		System.out.println(
	// 				"your " + foodTypes[i].getName() + " property: " + currentPlayer.getResourceCount(foodTypes[i]));
	// 	}
	// }

	// private static void runSetFearRate(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.setFearRate(
	// 			Integer.parseInt(matcher.get("rate"))).getErrorString());
	// }

	// private static void runSetFoodRate(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.setFoodRate(
	// 			Integer.parseInt(matcher.get("rate"))).getErrorString());
	// }

	// private static void runSetTaxRate(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.setTaxRate(
	// 			Integer.parseInt(matcher.get("rate"))).getErrorString());
	// }

	// private static void foodRateShow() {
	// 	Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
	// 	System.out.println("your food rate: " + currentPlayer.getFoodRate());
	// }

	// private static void taxRateShow() {
	// 	Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
	// 	System.out.println("your tax rate: " + currentPlayer.getTaxRate());
	// }

	// private static void fearRateShow() {
	// 	Government currentPlayer = StrongHold.getCurrentGame().getCurrentPlayer();
	// 	System.out.println("your fear rate: " + currentPlayer.getFearFactor());
	// }

	// private static void showSelectedBuilding() {
	// 	if (game.getSelectedBuilding() == null) {
	// 		System.out.println("No building is selected");
	// 		return;
	// 	}
	// 	System.out.println(game.getSelectedBuilding());
	// }

	// private static void showResourcesAmount() {
	// 	System.out.println("Resources report:");
	// 	for (ResourceType resourceType : ResourceType.values())
	// 		System.out
	// 				.println(resourceType.getName() + " => " + game.getCurrentPlayer().getResourceCount(resourceType));
	// }

	// private static void runSelectUnit(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.selectUnit(
	// 			Integer.parseInt(matcher.get("x")),
	// 			Integer.parseInt(matcher.get("y"))).getErrorString());
	// }

	// private static void showSelectedUnits() {
	// 	if (game.getSelectedUnits().isEmpty()) {
	// 		System.out.println("No unit is selected");
	// 		return;
	// 	}
	// 	for (Person person : game.getSelectedUnits())
	// 		System.out.println(person);
	// }

	// private static void runBuildSiegeEquipment(HashMap<String, String> matcher) {
	// 	System.out.println(GameMenuController.buildSiegeEquipment(
	// 		matcher.get("equipment")
	// 	).getErrorString());
	// }

	public static void showWinner(Government winner) {
		if (winner == null)
			System.out.println("The game didn't have a winner");
		else
			System.out.println("The winner is: " + winner.getUser().getUserName());
	}

	private static void runDigTunnel() {
		System.out.println(GameMenuController.digTunnel().getErrorString());
	}

	private static void runToggleDebugMode() {
		GameMenuController.setDebugMode(!GameMenuController.getDebugMode());
		System.out.println("DEBUG MODE: " + GameMenuController.getDebugMode());
	}
}
