package stronghold.controller;

import stronghold.controller.messages.GameMenuMessage;
import stronghold.model.Game;

public class GameMenuController {
	private static Game game;
	public static void setGame(Game game) {
		GameMenuController.game = game;
	}

	public static GameMenuMessage dropWall(int x1, int y1, int x2, int y2) {

	}

	public static GameMenuMessage dropBuilding(int x, int y, String type) {

	}
}
