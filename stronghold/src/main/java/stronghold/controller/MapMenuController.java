package stronghold.controller;

import stronghold.controller.messages.MapMenuMessage;

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
		// TODO
	}
}
