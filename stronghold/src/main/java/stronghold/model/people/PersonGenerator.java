package stronghold.model.people;

import stronghold.utils.ConfigManager;

public class PersonGenerator {
	public static Person newPersonByName(String name, int x, int y, int ownerIndex) {
		Person[] people = ConfigManager.getPeopleConfigs();
		for (Person person : people) {
			if (person.getName().equals(name)) {
				return new Person(person, x, y, ownerIndex);
			}
		}
		return null;
	}
}
