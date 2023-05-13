package stronghold;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import stronghold.model.ResourceType;
import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
import stronghold.model.buildings.ResourceConverterBuilding;
import stronghold.model.buildings.Stockpile;
import stronghold.model.buildings.Trap;
import stronghold.model.people.Person;
import stronghold.model.people.PersonType;
import stronghold.utils.ConfigManager;

public class GenerateConfig {
	public static void run() {
		generatePeople();
		generateBuildings();
	}

	private static void generatePeople() {
		ArrayList<Person> people = new ArrayList<>();

		people.add(new Person("lord", 2, 400, 80, 999999, 1, 0, false, false, false, false, PersonType.LORD,0, 0, 0));
		people.add(new Person("european archer", 6, 50, 20, 999999, 1, 10, true, false, false, false, PersonType.NORMAL,0, 0, 0));
		people.add(new Person("swordsman", 2, 200, 60, 999999, 1, 0, false, false, false, false, PersonType.NORMAL, 0, 0, 0));

		ConfigManager.savePeopleConfig(people.toArray(new Person[0]));
	}

	private static void generateBuildings() {
		
		addBuilding(new DefensiveStructure(
			1000, "keep", 0,
			3, 3, 1,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.KEEP, false
		));

		addBuilding(new DefensiveStructure(
			400, "horizontal small gate", 0,
			2, 2, 2,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.GATE, false
		));

		addBuilding(new DefensiveStructure(
			400, "vertical small gate", 0,
			2, 2, 2,
			true, 0, 0,
			8, 0,
			10, 7,
			DefensiveStructureType.GATE, true
		));

		addBuilding(new DefensiveStructure(
			600, "horizontal large gate", 0,
			3, 3, 2,
			true, 0, 0,
			10, 0,
			12, 6,
			DefensiveStructureType.GATE, false
		));

		addBuilding(new DefensiveStructure(
			600, "vertical large gate", 0,
			3, 3, 2,
			true, 0, 0,
			10, 0,
			12, 6,
			DefensiveStructureType.GATE, true
		));

		addBuilding(new DefensiveStructure(
			300, "lookout tower", 0,
			1, 1, 2,
			true, 0, 0,
			0, 0,
			12, 3,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			300, "small turret", 0,
			1, 1, 2,
			true, 0, 0,
			0, 0,
			10, 6,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			400, "large turret", 0,
			2, 2, 2,
			true, 0, 0,
			0, 0,
			10, 6,
			DefensiveStructureType.TURRET, false
		));

		addBuilding(new DefensiveStructure(
			700, "square tower", 0,
			3, 3, 2,
			true, 0, 0,
			0, 0,
			12, 4,
			DefensiveStructureType.TOWER, false
		));

		addBuilding(new DefensiveStructure(
			1000, "round tower", 0,
			3, 3, 2,
			true, 0, 0,
			0, 0,
			12, 4,
			DefensiveStructureType.TOWER, false
		));

		addBuilding(new DefensiveStructure(
			100, "high wall", 0,
			1, 1, 2,
			false, 0, 0,
			0, 0,
			4, 10,
			DefensiveStructureType.WALL, false
		));

		addBuilding(new DefensiveStructure(
			50, "low wall", 0,
			1, 1, 2,
			false, 0, 0,
			0, 0,
			2, 12,
			DefensiveStructureType.WALL, false
		));

		addBuilding(new DefensiveStructure(
			50, "stairs", 0,
			1, 1, 1,
			false, 0, 0,
			0, 0,
			2, 12,
			DefensiveStructureType.STAIRS, false
		));

		addBuilding(new Barracks(
			200, "european barracks", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"european archer", "swordsman"
			}
		));

		addBuilding(new Barracks(
			200, "arabian barracks", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"arabian archer", "arabian swordsman"
			}
		));

		addBuilding(new Barracks(
			200, "engineers guild", 0,
			2, 2, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"engineer", "ladderman", "tunneler"
			}
		));

		addBuilding(new Trap(
			10, "killing pit", 0,
			0, 0, 0,
			false, 0, 0,
			0, 0,
			9999, false
		));

		addBuilding(new Stockpile(
			200, "inn", 1,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.WINE, 0
			)), 20
		));

		addBuilding(new Stockpile(
			200, "armory", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.BOW, 0,
				ResourceType.CROSBOW, 0,
				ResourceType.SPEAR, 0,
				ResourceType.PIKE, 0,
				ResourceType.MACE, 0,
				ResourceType.SWORDS, 0,
				ResourceType.LEATHER_ARMOR, 0,
				ResourceType.METAL_ARMOR, 0
			)), 40
		));

		addBuilding(new Stockpile(
			200, "granary", 0,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			new HashMap<>(Map.of(
				ResourceType.APPLE, 0,
				ResourceType.CHEESE, 0,
				ResourceType.BREAD, 0,
				ResourceType.MEAT, 0
			)), 50
		));

		addBuilding(new ResourceConverterBuilding(
			200, "mill", 3,
			1, 1, 999,
			true, 0, 0,
			0, 0,
			ResourceType.WHEAT, ResourceType.FLOUR,
			null,
			2, 2, 1
		));
		
	}

	private static void addBuilding(Building building) {
		ConfigManager.saveBuildingConfig(building);
	}
}
