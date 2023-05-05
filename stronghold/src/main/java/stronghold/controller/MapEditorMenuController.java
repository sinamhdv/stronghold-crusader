package stronghold.controller;

import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.map.GroundType;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;
import stronghold.utils.Miscellaneous;

public class MapEditorMenuController {
	private static Map map;
	public static Map getMap() {
		return map;
	}
	public static void setMap(Map map) {
		MapEditorMenuController.map = map;
	}

	public static MapEditorMenuMessage setTexture(int x, int y, String type) {
		return rectangleSetTexture(x, y, x, y, type);
	}

	public static MapEditorMenuMessage rectangleSetTexture(int x1, int y1, int x2, int y2, String type) {
		GroundType groundType = Miscellaneous.getGroundTypeByName(type);
		if (groundType == null)
			return MapEditorMenuMessage.INVALID_GROUND_TYPE;
		else if (!Miscellaneous.checkCoordinatesOnMap(map, x1, y1) || !Miscellaneous.checkCoordinatesOnMap(map, x2, y2))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				MapTile tile = map.getGrid()[i][j];
				if (!tile.getPeople().isEmpty() || tile.getBuilding() != null || tile.getEnvironmentItem() != null)
					return MapEditorMenuMessage.OBJECT_FOUND;
			}
		}
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				map.getGrid()[i][j].setGroundType(groundType);
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage clear(int x1, int y1, int x2, int y2) {
		
	}
}
