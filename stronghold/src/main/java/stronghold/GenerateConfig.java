package stronghold;

import java.util.ArrayList;

import stronghold.model.buildings.Barracks;
import stronghold.model.buildings.Building;
import stronghold.model.buildings.DefensiveStructure;
import stronghold.model.buildings.DefensiveStructureType;
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
			100, "european barracks", 0,
			3, 3, 999,
			true, 0, 0,
			0, 0,
			0, new String[] {
				"european archer", "swordsman"
			}
		));
	}

	private static void addBuilding(Building building) {
		ConfigManager.saveBuildingConfig(building);
	}
}
