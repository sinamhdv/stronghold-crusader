package stronghold.controller;

import stronghold.model.StrongHold;
import stronghold.model.User;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.map.Map;
import stronghold.utils.Cryptography;

public class CentralController {
	public static final String[] SECURITY_QUESTIONS = new String[] {
		"What is your father's name?",
		"What was your first pet's name?",
		"What is your mother's last name?",
	};

	public static boolean checkPassword(String username, String password) {
		User user = StrongHold.getUserByName(username);
		return Cryptography.hashPassword(password).equals(user.getPassword());
	}

	public static boolean hasKeepOnMap(Map map, int ownerIndex) {
		for (int i = 0; i < map.getGrid().length; i++) {
			for (int j = 0; j < map.getGrid()[i].length; j++) {
				if (map.getGrid()[i][j].getBuilding() instanceof DefensiveStructure) {
					DefensiveStructure building = (DefensiveStructure)map.getGrid()[i][j].getBuilding();
					if (building.getType() == DefensiveStructureType.KEEP && building.getOwnerIndex() == ownerIndex)
						return true;
				}
			}
		}
		return false;
	}
}
