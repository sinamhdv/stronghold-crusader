package stronghold.controller;

import stronghold.controller.messages.MainMenuMessage;
import stronghold.model.Game;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.map.Map;
import stronghold.utils.DatabaseManager;
import stronghold.view.MainMenu;

public class MainMenuController {
	public static void logout() {
		DatabaseManager.clearStayLoggedIn(StrongHold.getCurrentUser());
	}

	public static void exit() {
		System.exit(0);
	}

	public static MainMenuMessage startGame(String mapName) {
		if (!DatabaseManager.mapExists(mapName))
			return MainMenuMessage.MAP_DOESNT_EXIST;
		Map map = DatabaseManager.loadMapByName(mapName);
		MainMenuMessage message = checkKeeps(map);
		if (message != null) return message;
		String[] usernames = MainMenu.askPlayersNames(map.getGovernmentsCount());
		User[] users = new User[usernames.length];
		boolean currentUserFound = false;
		for (int i = 0; i < users.length; i++) {
			users[i] = StrongHold.getUserByName(usernames[i]);
			if (users[i] == null)
				return MainMenuMessage.USERNAME_NOT_FOUND;
			if (usernames[i].equals(StrongHold.getCurrentUser().getUserName()))
				currentUserFound = true;
		}
		if (!currentUserFound)
			return MainMenuMessage.CURRENT_USER_NOT_FOUND;
		Game game = new Game(map, users);
		StrongHold.setCurrentGame(game);
		return MainMenuMessage.SUCCESS;
	}

	private static MainMenuMessage checkKeeps(Map map) {
		for (int i = 0; i < map.getGovernmentsCount(); i++) {
			if (!CentralController.hasKeepOnMap(map, i))
				return MainMenuMessage.KEEP_NOT_FOUND;
		}
		return null;
	}
}
