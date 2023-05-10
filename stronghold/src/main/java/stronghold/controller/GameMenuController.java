package stronghold.controller;

import stronghold.model.Game;

public class GameMenuController {
	private static Game game;
	public static void setGame(Game game) {
		GameMenuController.game = game;
	}
}
