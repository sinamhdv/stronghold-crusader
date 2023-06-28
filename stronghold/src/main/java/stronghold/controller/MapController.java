package stronghold.controller;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import stronghold.controller.messages.MapMenuMessage;
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

	private static void showTileDetails(int x, int y) {
		MapMenuMessage errorCheck = MapMenuController.checkCoordinateErrors(x, y);
		if (errorCheck != MapMenuMessage.SUCCESS) {
			System.out.println(errorCheck.getErrorString());
			return;
		}
		MapTile tile = MapMenuController.getCurrentMap().getGrid()[x][y];
		System.out.println("Ground Type: " + tile.getGroundType().getName());
		if (tile.getEnvironmentItem() != null)
			System.out.println("Environment Item: " + tile.getEnvironmentItem());
		if (tile.getBuilding() != null && tile.getBuilding().isVisible())
			System.out.println("Building: " + tile.getBuilding());
		for (int i = 0; i < MapMenuController.getCurrentMap().getGovernmentsCount(); i++) {
			System.out.println("List of people owned by #" + i + " " +": ");
			ArrayList<Person> filteredPeople = Miscellaneous.getPeopleByOwner(tile.getPeople(), i);
			for (Person person : filteredPeople)
				if (person.isVisible())
					System.out.println(person);
		}
	}
}
