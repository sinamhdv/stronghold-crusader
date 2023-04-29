package stronghold.controller;

import stronghold.controller.messages.MapMenuMessage;
import stronghold.model.StrongHold;
import stronghold.model.map.MapTile;

public class MapMenuController {
	private static final int SHOW_MAP_WIDTH = 10;
	private static final int SHOW_MAP_HEIGHT = 5;

	private static int currentX;
	private static int currentY;

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

	public static MapMenuMessage moveMap(String up, String down, String left, String right) {
		int deltaX = (up == null ? 0 : Integer.parseInt(up)) - (down == null ? 0 : Integer.parseInt(down));
		int deltaY = (right == null ? 0 : Integer.parseInt(right)) - (left == null ? 0 : Integer.parseInt(left));
		MapTile[][] map = StrongHold.getCurrentGame().getMap();
		if (currentX + deltaX < 0 || currentX + deltaX >= map.length
		|| currentY + deltaY < 0 || currentY + deltaY >= map[0].length) {
			return MapMenuMessage.INVALID_COORDINATES;
		}
		setCurrentX(currentX + deltaX);
		setCurrentY(currentY + deltaY);
		return MapMenuMessage.SUCCESS;
	}

	public static MapMenuMessage checkShowMap(String xString, String yString) {
		MapMenuMessage errorCheck = MapMenuController.checkCoordinateErrors(xString, yString);
		if (errorCheck != MapMenuMessage.SUCCESS) return errorCheck;
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		setCurrentX(x);
		setCurrentY(y);
		return MapMenuMessage.SUCCESS;
	}

	public static MapMenuMessage checkCoordinateErrors(String xString, String yString) {
		if (xString == null || yString == null) {
			return MapMenuMessage.SPECIFY_XY;
		}
		int x = Integer.parseInt(xString);
		int y = Integer.parseInt(yString);
		MapTile[][] map = StrongHold.getCurrentGame().getMap();
		if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
			return MapMenuMessage.INVALID_COORDINATES;
		}
		return MapMenuMessage.SUCCESS;
	}
}
