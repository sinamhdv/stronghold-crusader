package stronghold.model.buildings;

import stronghold.utils.ConfigManager;

public class BuildingGenerator {
	public static Building newBuildingByName(String name, int x, int y, int ownerIndex) {
		Building modelBuilding = ConfigManager.getBuildingFromConfig(name);
		if (modelBuilding == null) return null;
		return modelBuilding.generateCopy(x, y, ownerIndex);
	}
}
