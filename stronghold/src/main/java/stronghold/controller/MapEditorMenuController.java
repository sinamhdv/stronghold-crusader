package stronghold.controller;

import stronghold.controller.messages.MapEditorMenuMessage;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.BuildingGenerator;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.ResourceConverterBuilding;
import stronghold.model.environment.Rock;
import stronghold.model.environment.Tree;
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

	public static MapEditorMenuMessage checkRectangle(int x1, int y1, int x2, int y2, boolean checkGroundType) {
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
		if (checkGroundType)
			for (int i = x1; i <= x2; i++)
				for (int j = y1; j <= y2; j++)
					if (!map.getGrid()[i][j].getGroundType().isBuildable())
						return MapEditorMenuMessage.BAD_GROUND;
		return null;
	}

	public static MapEditorMenuMessage rectangleSetTexture(int x1, int y1, int x2, int y2, String type) {
		GroundType groundType = GroundType.getGroundTypeByName(type);
		if (groundType == null)
			return MapEditorMenuMessage.INVALID_GROUND_TYPE_NAME;
		MapEditorMenuMessage rectangleErrors = checkRectangle(x1, y1, x2, y2, false);
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
		for (int i = x1; i <= x2; i++) {
			for (int j = y1; j <= y2; j++) {
				eraseBuilding(map.getGrid()[i][j].getBuilding());
				map.getGrid()[i][j] = new MapTile();
			}
		}
		return MapEditorMenuMessage.SUCCESS;
	}

	private static void eraseBuilding(Building building) {
		if (building == null) return;
		for (int i = building.getX(); i <= building.getX() + building.getHeight() - 1; i++) {
			for (int j = building.getY(); j <= building.getY() + building.getWidth() - 1; j++) {
				map.getGrid()[i][j].setBuilding(null);
			}
		}
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
		if (!tile.isPassable())
			return MapEditorMenuMessage.CANNOT_PLACE_UNIT;
		if (PersonGenerator.newPersonByName(type, x, y, getSelectedGovernment()) == null)
			return MapEditorMenuMessage.INVALID_UNIT_TYPE;
		if (type.equals("lord") && CentralController.hasKeepOnMap(map, getSelectedGovernment()))
			return MapEditorMenuMessage.SECOND_LORD;
		for (int i = 0; i < count; i++)
			tile.addPerson(PersonGenerator.newPersonByName(type, x, y, getSelectedGovernment()));
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage dropBuilding(int x, int y, String type) {
		Building building = BuildingGenerator.newBuildingByName(type, x, y, getSelectedGovernment());
		if (building == null)
			return MapEditorMenuMessage.INVALID_BUILDING_TYPE;
			
		if ((building instanceof DefensiveStructure) &&
			((DefensiveStructure)building).getType() == DefensiveStructureType.KEEP &&
			CentralController.hasKeepOnMap(map, getSelectedGovernment()))
				return MapEditorMenuMessage.SECOND_KEEP;
		
		int x2 = x + building.getHeight() - 1;
		int y2 = y + building.getWidth() - 1;
		MapEditorMenuMessage rectangleErrors = checkRectangle(x, y, x2, y2, true);
		if (rectangleErrors != null) return rectangleErrors;

		if (building instanceof ResourceConverterBuilding) {
			for (int i = x; i <= x2; i++) {
				for (int j = y; j <= y2; j++) {
					GroundType groundType = map.getGrid()[i][j].getGroundType();
					if (!((ResourceConverterBuilding)building).isGroundTypeAllowed(groundType))
						return MapEditorMenuMessage.BAD_GROUND;
				}
			}
		}

		dropUnit(x, y, "lord", 1);
		for (int i = x; i <= x2; i++)
			for (int j = y; j <= y2; j++)
				map.getGrid()[i][j].setBuilding(building);
		return MapEditorMenuMessage.SUCCESS;
	}

	public static MapEditorMenuMessage selectGovernment(int index) {
		if (index < 0 || index >= map.getGovernmentsCount())
			return MapEditorMenuMessage.INVALID_GOVERNMENT_INDEX;
		setSelectedGovernment(index);
		return MapEditorMenuMessage.SUCCESS;
	}
}
