package stronghold.controller;

import stronghold.client.SendRequests;
import stronghold.controller.messages.GameMenuMessage;
import stronghold.controller.messages.MainMenuMessage;
import stronghold.model.Game;
import stronghold.model.PendingGame;
import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.map.Map;
import stronghold.utils.DatabaseManager;
import stronghold.utils.Miscellaneous;

public class MainMenuController {
	public static void logout() {
		DatabaseManager.clearStayLoggedIn(StrongHold.getCurrentUser());
		SendRequests.requestLogout();
	}

	public static synchronized MainMenuMessage serverCreateGame(String mapName, User admin, String gameId) {
		if (!DatabaseManager.mapExists(mapName))
			return MainMenuMessage.MAP_DOESNT_EXIST;
		Map map = DatabaseManager.loadMapByName(mapName);
		MainMenuMessage message = checkKeeps(map);
		if (message != null) return message;
		PendingGame game = new PendingGame(map, admin);
		StrongHold.addPendingGame(gameId, game);
		return MainMenuMessage.SUCCESS;
	}

	public static synchronized MainMenuMessage addPlayerToGame(String gameId, User player) {
		PendingGame game = StrongHold.getPendingGameById(gameId);
		if (game == null) return MainMenuMessage.GAME_NOT_FOUND;
		if (game.getPlayers().size() == game.getMap().getGovernmentsCount()) return MainMenuMessage.GAME_IS_FULL;
		game.addPlayer(player);
		return MainMenuMessage.SUCCESS;
	}

	public static String getNewGameId() {
		return Integer.toString(Miscellaneous.RANDOM_GENERATOR.nextInt(100000000));
	}

	private static MainMenuMessage checkKeeps(Map map) {
		for (int i = 0; i < map.getGovernmentsCount(); i++) {
			if (!CentralController.hasKeepOnMap(map, i))
				return MainMenuMessage.KEEP_NOT_FOUND;
		}
		return null;
	}

	public static void checkStartGame(String gameId) {
		PendingGame game = StrongHold.getPendingGameById(gameId);
		if (game.getPlayers().size() != game.getMap().getGovernmentsCount()) return;
		for (User user : game.getPlayers())
			user.getClientHandler().sendGameMap();
	}

	public static void clientStartGame(Map map) {
		Game game = new Game(map, new User[map.getGovernmentsCount()]);
		StrongHold.setCurrentGame(game);
	}

	// private static boolean hasRepetitiveName(String[] array) {
	// 	for (int i = 0; i < array.length; i++)
	// 		for (int j = i + 1; j < array.length; j++)
	// 			if (array[i].equals(array[j]))
	// 				return true;
	// 	return false;
	// }
}
