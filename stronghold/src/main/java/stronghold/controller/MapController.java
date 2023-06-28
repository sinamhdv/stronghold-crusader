package stronghold.controller;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
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
		ImageView image = new ImageView(tile.getGroundType().getImage());
		image.setFitHeight(ViewUtils.getScreenHeight() / (double)SHOW_MAP_HEIGHT);
		image.setFitWidth(ViewUtils.getScreenWidth() / (double)SHOW_MAP_WIDTH);
		return new Group(image);
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
					result += person;
		}
		return result;
	}
}
