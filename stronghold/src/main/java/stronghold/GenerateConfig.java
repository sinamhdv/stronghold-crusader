package stronghold;

import java.util.ArrayList;

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
			1000,
			"keep",
			0,
			3, 3, 1,
			true,
			0, 0,
			8, 0,
			10,
			7,
			DefensiveStructureType.KEEP,
			false
		));

		addBuilding(new DefensiveStructure(
			100,
			"high wall",
			0,
			1, 1, 2,
			false, 0, 0,
			0, 0,
			4, 10,
			DefensiveStructureType.WALL,
			false
		));

		addBuilding(new DefensiveStructure(
			50,
			"low wall",
			0,
			1, 1, 2,
			false,
			0, 0,
			0, 0,
			2, 12,
			DefensiveStructureType.WALL,
			false
		));
	}

	private static void addBuilding(Building building) {
		ConfigManager.saveBuildingConfig(building);
	}
}
