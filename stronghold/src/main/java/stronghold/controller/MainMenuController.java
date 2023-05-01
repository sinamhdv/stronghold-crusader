package stronghold.controller;

import stronghold.model.StrongHold;

public class MainMenuController {
	public static void logout() {
		DatabaseManager.clearStayLoggedIn(StrongHold.getCurrentUser());
	}
}
