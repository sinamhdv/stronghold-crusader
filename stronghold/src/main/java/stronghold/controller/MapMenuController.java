package stronghold.controller;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import stronghold.controller.messages.MapMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.map.Map;
import stronghold.model.map.MapTile;

public class MapMenuController {
	public static final int SHOW_MAP_WIDTH = 32;
	public static final int SHOW_MAP_HEIGHT = SHOW_MAP_WIDTH / 16 * 9;

	private static Map currentMap;
	private static int currentX;
	private static int currentY;

	public static int getScreenHeight() {
		return (int)(Screen.getPrimary().getBounds().getHeight() + 0.5);
	}

	public static int getScreenWidth() {
		return (int)(Screen.getPrimary().getBounds().getWidth() + 0.5);
	}

	public static int getCurrentX() {
		return currentX;
	}
	public static void setCurrentX(int currentX) {
		MapMenuController.currentX = currentX;
	}
	public static int getCurrentY() {
		return currentY;
	}
	public static void setCurrentY(int currentY) {
		MapMenuController.currentY = currentY;
	}
	public static Map getCurrentMap() {
		return currentMap;
	}
	public static void setCurrentMap(Map currentMap) {
		MapMenuController.currentMap = currentMap;
	}
	public static void updateCurrentMap() {
		if (StrongHold.getCurrentGame() != null)
			currentMap = StrongHold.getCurrentGame().getMap();
	}

	public static MapMenuMessage moveMap(int deltaX, int deltaY) {
		MapTile[][] map = getCurrentMap().getGrid();
		if (currentX + deltaX < 0 || currentX + deltaX >= map.length
		|| currentY + deltaY < 0 || currentY + deltaY >= map[0].length) {
			return MapMenuMessage.INVALID_COORDINATES;
		}
		setCurrentX(currentX + deltaX);
		setCurrentY(currentY + deltaY);
		return MapMenuMessage.SUCCESS;
	}

	public static MapMenuMessage startShowMap(int x, int y) {
		MapMenuMessage errorCheck = MapMenuController.checkCoordinateErrors(x, y);
		if (errorCheck != MapMenuMessage.SUCCESS) return errorCheck;
		setCurrentX(x);
		setCurrentY(y);
		return MapMenuMessage.SUCCESS;
	}

	public static MapMenuMessage checkCoordinateErrors(int x, int y) {
		MapTile[][] map = getCurrentMap().getGrid();
		if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
			return MapMenuMessage.INVALID_COORDINATES;
		}
		return MapMenuMessage.SUCCESS;
	}

	public static Group getTileRepresentation(int x, int y) {
		MapTile tile = getCurrentMap().getGrid()[x][y];
		ImageView image = new ImageView(tile.getGroundType().getImage());
		image.setFitHeight(getScreenHeight() / (double)SHOW_MAP_HEIGHT);
		image.setFitWidth(getScreenWidth() / (double)SHOW_MAP_WIDTH);
		return new Group(image);
	}
}
