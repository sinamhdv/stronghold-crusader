package stronghold.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import stronghold.model.map.Map;
import stronghold.model.people.Person;

public class Miscellaneous {
	public static final Random RANDOM_GENERATOR = new Random();

	public static HashMap<String, Integer> countPeopleFromArray(ArrayList<Person> people) {
		HashMap<String, Integer> result = new HashMap<>();
		for (Person person : people) {
			int current = (result.get(person.getName()) == null ? 0 : result.get(person.getName()));
			result.put(person.getName(), current + 1);
		}
		return result;
	}

	public static ArrayList<Person> getPeopleByOwner(ArrayList<Person> people, int ownerIndex) {
		ArrayList<Person> result = new ArrayList<>();
		for (Person person : people)
			if (person.getOwnerIndex() == ownerIndex)
				result.add(person);
		return result;
	}

	public static boolean checkCoordinatesOnMap(Map map, int x, int y) {
		return (x >= 0 && y >= 0 && x < map.getGrid().length && y < map.getGrid()[0].length);
	}
}
