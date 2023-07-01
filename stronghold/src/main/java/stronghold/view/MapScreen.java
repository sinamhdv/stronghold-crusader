package stronghold.view;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import stronghold.controller.GameMenuController;
import stronghold.controller.messages.GameMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.environment.EnvironmentItem;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;
import stronghold.utils.AssetImageLoader;
import stronghold.utils.Miscellaneous;
import stronghold.utils.ViewUtils;

public class MapScreen {
	public static final int SHOW_MAP_WIDTH = 16;
	public static final int SHOW_MAP_HEIGHT = SHOW_MAP_WIDTH / 16 * 9;
	public static final double GRID_GAPS = 0.5;
	public static final double CELL_DIMENTIONS = ViewUtils.getScreenHeight() / (double)SHOW_MAP_HEIGHT;

	private static int dragStartX, dragStartY;
	private static Rectangle selectionRectangle;

	private static double currentZoomLevel = 1;

	public static Group getTileRepresentation(int x, int y) {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
		ImageView groundImage = new ImageView(tile.getGroundType().getImage());
		double height = CELL_DIMENTIONS;
		double width = CELL_DIMENTIONS;
		groundImage.setFitHeight(height);
		groundImage.setFitWidth(width);
		Group group = new Group(groundImage);
		addBuildingImage(tile, group, width, height);
		addEnvironmentItemImage(tile, group, width, height);
		addPeopleImages(tile, group, width, height);
		addTooltip(group, x, y);
		addMouseHandlers(group, x, y);
		return group;
	}

	private static void addBuildingImage(MapTile tile, Group group, double width, double height) {
		Building building = tile.getBuilding();
		if (building == null || !building.isVisible()) return;
		ImageView image;
		if ((building instanceof DefensiveStructure) &&
			((DefensiveStructure)building).getType() == DefensiveStructureType.GATE &&
			building.getVerticalHeight() == 1)
				image = new ImageView(AssetImageLoader.getAssetImage("open " + building.getName()));
		else
			image = new ImageView(AssetImageLoader.getAssetImage(building.getName()));
		image.setFitHeight(height);
		image.setFitWidth(width);
		group.getChildren().add(image);
	}

	private static void addEnvironmentItemImage(MapTile tile, Group group, double width, double height) {
		EnvironmentItem environmentItem = tile.getEnvironmentItem();
		if (environmentItem == null) return;
		ImageView image = new ImageView(AssetImageLoader.getAssetImage(environmentItem.getName()));
		image.setFitHeight(height);
		image.setFitWidth(width);
		group.getChildren().add(image);
	}

	private static void addPeopleImages(MapTile tile, Group group, double width, double height) {
		for (Person person : tile.getPeople()) {
			if (person.isVisible()) {
				ImageView image = new ImageView(AssetImageLoader.getAssetImage(person.getName()));
				image.setFitHeight(height / 2);
				image.setFitWidth(width / 3);
				group.getChildren().add(image);
			}
		}
	}

	private static void addTooltip(Group group, int x, int y) {
		Tooltip tooltip = new Tooltip(getTileDetails(x, y));
		tooltip.setShowDelay(Duration.millis(10));
		tooltip.setHideDelay(Duration.millis(10));
		tooltip.setShowDuration(Duration.INDEFINITE);
		Tooltip.install(group, tooltip);
		group.setOnMouseEntered(event -> {
			tooltip.setText(getTileDetails(x, y));
		});
	}

	private static void addMouseHandlers(Group group, int x, int y) {
		group.setOnMouseClicked(event -> {
			if (event.isControlDown())	// select building
				GameMenu.getInstance().runSelectBuilding(x, y);
			else if (event.isShiftDown())
				selectArea(x, y, x, y);
		});
		group.setOnDragDetected(event -> {
			if (!event.isShiftDown()) return;
			GameMenu.getInstance().getScrollPane().setPannable(false);
			group.startFullDrag();
			dragStartX = x;
			dragStartY = y;
		});
		group.setOnMouseDragReleased(event -> {
			if (event.getGestureSource() instanceof Group) {
				if (!event.isShiftDown()) return;
				GameMenu.getInstance().getScrollPane().setPannable(true);
				selectArea(Math.min(x, dragStartX), Math.min(y, dragStartY),
					Math.max(x, dragStartX), Math.max(y, dragStartY));
			}
			else if (event.getGestureSource() instanceof ImageView) {	// building creation
				GameMenu.getInstance().runDropBuilding(x, y, GameMenuController.getDraggedBuildingName());
			}
		});
	}

	private static void selectArea(int x1, int y1, int x2, int y2) {
		GridPane grid = GameMenu.getInstance().getGrid();
		if (selectionRectangle != null) grid.getChildren().remove(selectionRectangle);
		selectionRectangle = new Rectangle((y2 - y1 + 1) * (CELL_DIMENTIONS + GRID_GAPS), (x2 - x1 + 1) * (CELL_DIMENTIONS + GRID_GAPS));
		selectionRectangle.setFill(Color.PURPLE);
		selectionRectangle.setStroke(Color.PURPLE);
		selectionRectangle.setOpacity(0.2);
		selectionRectangle.setManaged(false);
		selectionRectangle.setMouseTransparent(true);
		grid.add(selectionRectangle, x1, y1);
		selectionRectangle.setX(y1 * (CELL_DIMENTIONS + GRID_GAPS));
		selectionRectangle.setY(x1 * (CELL_DIMENTIONS + GRID_GAPS));
		GameMenuController.setSelectedArea(x1, y1, x2, y2);
		GameToolBar.clearMainPane();
		GameMenu.getInstance().showUnitCommandsBox();
	}

	public static void clearAreaSelection() {
		GridPane grid = GameMenu.getInstance().getGrid();
		if (selectionRectangle != null) grid.getChildren().remove(selectionRectangle);
		selectionRectangle = null;
		StrongHold.getCurrentGame().clearSelectedUnits();
	}

	public static String getTileDetails(int x, int y) {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
		String result = "(" + x + ", " + y + ")\n";
		result += "Ground Type: " + tile.getGroundType().getName() + "\n";
		if (tile.getEnvironmentItem() != null)
			result += "Environment Item: " + tile.getEnvironmentItem() + "\n";
		if (tile.getBuilding() != null && tile.getBuilding().isVisible())
			result += "Building: " + tile.getBuilding() + "\n";
		for (int i = 0; i < StrongHold.getCurrentGame().getMap().getGovernmentsCount(); i++) {
			ArrayList<Person> filteredPeople = Miscellaneous.getPeopleByOwner(tile.getPeople(), i);
			for (Person person : filteredPeople)
				if (person.isVisible())
					result += person + "\n";
		}
		return result;
	}

	static void zoomHandler(double value) {
		if (currentZoomLevel * value < 1 || currentZoomLevel * value > 1.1) return;
		currentZoomLevel *= value;
		GridPane grid = GameMenu.getInstance().getGrid();
		grid.setScaleX(currentZoomLevel);
		grid.setScaleY(currentZoomLevel);
	}

	public static void refreshMapCell(int x, int y) {
		GridPane grid = GameMenu.getInstance().getGrid();
		for (Node node : grid.getChildren()) {
			if (!(node instanceof Group)) continue;	// XXX: all map grid children of type Group must be grid cells
			if (GridPane.getRowIndex(node) != x || GridPane.getColumnIndex(node) != y) continue;
			grid.getChildren().remove(node);
			break;
		}
		grid.add(getTileRepresentation(x, y), y, x);
	}
}
