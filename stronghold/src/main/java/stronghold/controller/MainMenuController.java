package stronghold.controller;

import stronghold.model.StrongHold;
import stronghold.utils.DatabaseManager;

public class MainMenuController {
	public static void logout() {
		DatabaseManager.clearStayLoggedIn(StrongHold.getCurrentUser());
	}
	public static void exit() {
		System.exit(0);
	}
}
