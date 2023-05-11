package stronghold;

import java.util.ArrayList;

import stronghold.model.buildings.Building;
import stronghold.model.people.Person;
import stronghold.utils.ConfigManager;

public class GenerateConfig {
	public static void run() {
		generatePeople();
		generateBuildings();
	}

	private static void generatePeople() {
		ArrayList<Person> people = new ArrayList<>();

		people.add(new Person("european archer", 6, 50, 20, 999999, 1, 10, true, false, false, false, 0, 0, 0));
		people.add(new Person("swordsman", 2, 200, 60, 999999, 1, 0, false, false, false, false, 0, 0, 0));

		ConfigManager.savePeopleConfig(people.toArray(new Person[0]));
	}

	private static void generateBuildings() {
		addBuilding();
	}

	private static void addBuilding(Building building) {
		
	}
}
