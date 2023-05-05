package stronghold.controller;

import stronghold.controller.messages.MapManagementMenuMessage;
import stronghold.model.map.Map;
import stronghold.utils.FormatValidation;

public class MapManagementMenuController {
	private static final int MIN_DIMENTIONS = 200;
	private static final int MAX_DIMENTIONS = 400;
	private static Map loadedMap;

	public static Map getLoadedMap() {
		return loadedMap;
	}

	public static MapManagementMenuMessage createNewMap(String mapName, int governmentsCount, int width, int height) {
		if (DatabaseManager.mapExists(mapName))
			return MapManagementMenuMessage.MAP_ALREADY_EXISTS;
		if (!FormatValidation.checkMapNameFormat(mapName))
			return MapManagementMenuMessage.INVALID_MAP_NAME_FORMAT;
		if (governmentsCount < 1 || governmentsCount > 8)
			return MapManagementMenuMessage.INVALID_GOVERNMENTS_COUNT;
		if (width < MIN_DIMENTIONS || width > MAX_DIMENTIONS || height < MIN_DIMENTIONS || height > MAX_DIMENTIONS)
			return MapManagementMenuMessage.INVALID_DIMENTIONS;
		Map map = new Map(mapName, governmentsCount, width, height);
		DatabaseManager.saveMap(map);
		return MapManagementMenuMessage.CREATE_SUCCESS;
	}

	public static MapManagementMenuMessage editMap(String mapName) {
		if (!DatabaseManager.mapExists(mapName))
			return MapManagementMenuMessage.MAP_NOT_FOUND;
		loadedMap = DatabaseManager.loadMapByName(mapName);
		return MapManagementMenuMessage.LOAD_SUCCESS;
	}

	public static MapManagementMenuMessage deleteMap(String mapName) {
		if (!DatabaseManager.mapExists(mapName))
			return MapManagementMenuMessage.MAP_NOT_FOUND;
		DatabaseManager.deleteMap(mapName);
		return MapManagementMenuMessage.DELETE_SUCCESS;
	}
}
