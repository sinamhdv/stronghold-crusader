package stronghold.controller;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import stronghold.model.StrongHold;
import stronghold.model.map.MapTile;
import stronghold.model.people.Person;
import stronghold.utils.Miscellaneous;
import stronghold.utils.ViewUtils;

public class MapController {
	public static final int SHOW_MAP_WIDTH = 32;
	public static final int SHOW_MAP_HEIGHT = SHOW_MAP_WIDTH / 16 * 9;

	public static Group getTileRepresentation(int x, int y) {
		MapTile tile = StrongHold.getCurrentGame().getMap().getGrid()[x][y];
		ImageView groundImage = new ImageView(tile.getGroundType().getImage());
		groundImage.setFitHeight(ViewUtils.getScreenHeight() / (double)SHOW_MAP_HEIGHT);
		groundImage.setFitWidth(ViewUtils.getScreenWidth() / (double)SHOW_MAP_WIDTH);
		Group group = new Group(groundImage);
		Tooltip tooltip = new Tooltip(getTileDetails(x, y));
		tooltip.setShowDelay(Duration.millis(100));
		group.setOnMouseEntered(event -> {
			tooltip.setText(getTileDetails(x, y));
			tooltip.show(group, event.getScreenX() + groundImage.getFitHeight(),
				event.getScreenY() + groundImage.getFitWidth());
		});
		group.setOnMouseExited(event -> {
			tooltip.hide();
		});
		return group;
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
