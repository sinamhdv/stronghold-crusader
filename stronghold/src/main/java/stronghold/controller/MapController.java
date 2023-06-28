package stronghold.controller;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import stronghold.model.StrongHold;
import stronghold.model.buildings.Building;
import stronghold.model.environment.EnvironmentItem;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;
import stronghold.utils.AssetImageLoader;
import stronghold.utils.Miscellaneous;
import stronghold.utils.ViewUtils;

public class MapController {
	public static final int SHOW_MAP_WIDTH = 16;
	public static final int SHOW_MAP_HEIGHT = SHOW_MAP_WIDTH / 16 * 9;

	public static Group getTileRepresentation(int x, int y) {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
		ImageView groundImage = new ImageView(tile.getGroundType().getImage());
		double height = ViewUtils.getScreenHeight() / (double)SHOW_MAP_HEIGHT;
		double width = ViewUtils.getScreenWidth() / (double)SHOW_MAP_WIDTH;
		groundImage.setFitHeight(height);
		groundImage.setFitWidth(width);
		Group group = new Group(groundImage);
		addBuildingImage(tile, group, width, height);
		addEnvironmentItemImage(tile, group, width, height);
		// addPeopleImages(tile, group, width, height);
		addTooltip(group, groundImage, x, y);
		return group;
	}

	private static void addBuildingImage(MapTile tile, Group group, double width, double height) {
		Building building = tile.getBuilding();
		if (building == null || !building.isVisible()) return;
		ImageView image = new ImageView(AssetImageLoader.getAssetImage(building.getName()));
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

	private static void addTooltip(Group group, ImageView groundImage, int x, int y) {
		Tooltip tooltip = new Tooltip(getTileDetails(x, y));
		tooltip.setShowDelay(Duration.millis(10));
		tooltip.setHideDelay(Duration.millis(10));
		tooltip.setShowDuration(Duration.INDEFINITE);
		Tooltip.install(group, tooltip);
		group.setOnMouseEntered(event -> {
			tooltip.setText(getTileDetails(x, y));
		});
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
}
