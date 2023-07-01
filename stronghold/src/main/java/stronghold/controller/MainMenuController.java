package stronghold.controller;

import stronghold.client.SendRequests;
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
		SendRequests.requestLogout();
	}

	public static MainMenuMessage startGame(String mapName, User admin) {
		if (!DatabaseManager.mapExists(mapName))
			return MainMenuMessage.MAP_DOESNT_EXIST;
		Map map = DatabaseManager.loadMapByName(mapName);
		MainMenuMessage message = checkKeeps(map);
		if (message != null) return message;
		User[] users = new User[map.getGovernmentsCount()];
		users[0] = admin;
		Game game = new Game(map, users);
		StrongHold.addPendingGame(admin, game);
		return MainMenuMessage.SUCCESS;
	}

	private static MainMenuMessage checkKeeps(Map map) {
		for (int i = 0; i < map.getGovernmentsCount(); i++) {
			if (!CentralController.hasKeepOnMap(map, i))
				return MainMenuMessage.KEEP_NOT_FOUND;
		}
		return null;
	}

	private static boolean hasRepetitiveName(String[] array) {
		for (int i = 0; i < array.length; i++)
			for (int j = i + 1; j < array.length; j++)
				if (array[i].equals(array[j]))
					return true;
		return false;
	}
}
