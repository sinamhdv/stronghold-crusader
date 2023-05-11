package stronghold.controller;

import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.environment.Rock;
import stronghold.model.environment.Tree;
import stronghold.model.environment.Wall;
import stronghold.model.map.GroundType;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;
import stronghold.model.people.PersonGenerator;
import stronghold.utils.Miscellaneous;

public class MapEditorMenuController {
	private static Map map;
	private static int selectedGovernment;
	public static Map getMap() {
		return map;
	}
	public static void setMap(Map map) {
		MapEditorMenuController.map = map;
	}
	public static int getSelectedGovernment() {
		return selectedGovernment;
	}
	public static void setSelectedGovernment(int selectedGovernment) {
		MapEditorMenuController.selectedGovernment = selectedGovernment;
	}

	public static MapEditorMenuMessage setTexture(int x, int y, String type) {
		return rectangleSetTexture(x, y, x, y, type);
	}

	public static MapEditorMenuMessage checkRectangle(int x1, int y1, int x2, int y2) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x1, y1) || !Miscellaneous.checkCoordinatesOnMap(map, x2, y2))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		if (x2 < x1 || y2 < y1)
			return MapEditorMenuMessage.INVALID_COORDINATES;
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				MapTile tile = map.getGrid()[i][j];
				if (tile.hasObstacle() || tile.hasPeople())
					return MapEditorMenuMessage.FULL_CELL;
			}
		}
		return null;
	}

	public static MapEditorMenuMessage rectangleSetTexture(int x1, int y1, int x2, int y2, String type) {
		GroundType groundType = GroundType.getGroundTypeByName(type);
		if (groundType == null)
			return MapEditorMenuMessage.INVALID_GROUND_TYPE_NAME;
		MapEditorMenuMessage rectangleErrors = checkRectangle(x1, y1, x2, y2);
		if (rectangleErrors != null) return rectangleErrors;
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				map.getGrid()[i][j].setGroundType(groundType);
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage clear(int x1, int y1, int x2, int y2) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x1, y1) || !Miscellaneous.checkCoordinatesOnMap(map, x2, y2))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		if (x2 < x1 || y2 < y1)
			return MapEditorMenuMessage.INVALID_COORDINATES;
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				map.getGrid()[i][j] = new MapTile();
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropRock(int x, int y, String directionString) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x, y))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		MapTile tile = map.getGrid()[x][y];
		char direction;
		if (!directionString.equals("random")) {
			if (directionString.length() != 1 || !"news".contains(directionString))
				return MapEditorMenuMessage.INVALID_DIRECTION;
			direction = directionString.charAt(0);
		}
		else
			direction = "news".charAt(Miscellaneous.RANDOM_GENERATOR.nextInt(4));
		if (tile.hasObstacle() || tile.hasPeople())
			return MapEditorMenuMessage.FULL_CELL;
		if (!tile.getGroundType().isBuildable())
			return MapEditorMenuMessage.BAD_GROUND;
		tile.setEnvironmentItem(new Rock(direction));
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropTree(int x, int y, String type) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x, y))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		MapTile tile = map.getGrid()[x][y];
		if (!Tree.TREE_NAMES.contains(type))
			return MapEditorMenuMessage.INVALID_TREE_NAME;
		if (tile.hasObstacle() || tile.hasPeople())
			return MapEditorMenuMessage.FULL_CELL;
		if (!tile.getGroundType().isBuildable())
			return MapEditorMenuMessage.BAD_GROUND;
		tile.setEnvironmentItem(new Tree(type));
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropUnit(int x, int y, String type, int count) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x, y))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		MapTile tile = map.getGrid()[x][y];
		if (count <= 0)
			return MapEditorMenuMessage.INVALID_COUNT;
		if (tile.hasObstacle())
			return MapEditorMenuMessage.FULL_CELL;
		if (!tile.getGroundType().isPassable())
			return MapEditorMenuMessage.BAD_GROUND;
		if (PersonGenerator.newPersonByName(type, x, y, getSelectedGovernment()) == null)
			return MapEditorMenuMessage.INVALID_UNIT_TYPE;
		for (int i = 0; i < count; i++)
			tile.addPerson(PersonGenerator.newPersonByName(type, x, y, getSelectedGovernment()));
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropBuilding(int x, int y, String type) {
		if (!Miscellaneous.checkCoordinatesOnMap(map, x, y))
			return MapEditorMenuMessage.INVALID_COORDINATES;
		MapTile tile = map.getGrid()[x][y];
		if (tile.hasObstacle() || tile.hasPeople())
			return MapEditorMenuMessage.FULL_CELL;
		if (!tile.getGroundType().isBuildable())
			return MapEditorMenuMessage.BAD_GROUND;
		// TODO: check if the building fits in this space considering its dimentions
		// TODO: find a way to generate buildings from config efficiently
		// Building building = newBuildingByName(type, x, y, getSelectedGovernment());
		// if (building == null)
		// 	return MapEditorMenuMessage.INVALID_BUILDING_TYPE;
		// tile.setBuilding(building);
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage selectGovernment(int index) {
		if (index < 0 || index >= map.getGovernmentsCount())
			return MapEditorMenuMessage.INVALID_GOVERNMENT_INDEX;
		setSelectedGovernment(index);
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropWall(int x1, int y1, int x2, int y2) {
		MapEditorMenuMessage rectangleErrors = checkRectangle(x1, y1, x2, y2);
		if (rectangleErrors != null) return rectangleErrors;
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				if (!map.getGrid()[i][j].getGroundType().isBuildable())
					return MapEditorMenuMessage.BAD_GROUND;
		for (int i = x1; i <= x2; i++)
			for (int j = y1; j <= y2; j++)
				map.getGrid()[i][j].setEnvironmentItem(new Wall(getSelectedGovernment()));
		return MapEditorMenuMessage.SUCCESS;
	}
}
